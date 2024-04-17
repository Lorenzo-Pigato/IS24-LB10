package it.polimi.ingsw.lb10.server.controller;

import it.polimi.ingsw.lb10.network.requests.QuitRequest;
import it.polimi.ingsw.lb10.network.requests.match.MatchRequest;
import it.polimi.ingsw.lb10.network.requests.match.JoinMatchRequest;
import it.polimi.ingsw.lb10.network.requests.preMatch.LobbyToMatchRequest;
import it.polimi.ingsw.lb10.network.requests.preMatch.LoginRequest;
import it.polimi.ingsw.lb10.network.requests.preMatch.NewMatchRequest;
import it.polimi.ingsw.lb10.network.response.lobby.BooleanResponse;
import it.polimi.ingsw.lb10.network.response.match.JoinMatchResponse;
import it.polimi.ingsw.lb10.network.response.match.TerminatedMatchResponse;
import it.polimi.ingsw.lb10.server.Server;
import it.polimi.ingsw.lb10.server.model.Player;
import it.polimi.ingsw.lb10.server.view.RemoteView;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.LobbyRequestVisitor;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This SINGLETON class handles the login requests and dispatches players inside match controllers,
 * keeps track of starting or waiting match and signed in players separated from Server.
 * Every class method has to be SYNCHRONIZED.
 */

public class LobbyController implements LobbyRequestVisitor {

    private static LobbyController instance;
    private ArrayList<MatchController> matches;
    private ArrayList<Player> signedPlayers;
    private static ArrayList<RemoteView> remoteViews;
    private static ExecutorService controllersPool = Executors.newCachedThreadPool();


    private LobbyController() {
        signedPlayers = new ArrayList<>();
        matches = new ArrayList<>();
        remoteViews = new ArrayList<>();
    }

    public synchronized static LobbyController instance() {
        if (instance == null) instance = new LobbyController();
        return instance;
    }

    public synchronized static void addRemoteView(RemoteView remoteView) {
        remoteViews.add(remoteView);
    }

    public synchronized static void removeRemoteView(RemoteView remoteView) {
        remoteViews.remove(remoteView);
    }

    public synchronized boolean validateUsername(@NotNull String username) {
        return signedPlayers
                .stream()
                .map(Player::getUsername)
                .noneMatch((username::equalsIgnoreCase));
    }

    public synchronized void addSignedPlayer(int hashCode, String username) {
        signedPlayers.add(new Player(hashCode, username));
    }

    // -------- REQUEST HANDLING -------------//

    @Override
    public void visit(@NotNull LoginRequest lr) {
        Server.log("\n>> Received Login Request from: " + lr.getUserHash() + " - Requested username: " + lr.getUsername());
        boolean validated = validateUsername(lr.getUsername());
        if (validated) addSignedPlayer(lr.getUserHash(), lr.getUsername());
        getRemoteView(lr.getUserHash()).send(new BooleanResponse(validated));
        Server.log(">> Sent boolean response to hashcode: " + lr.getUserHash() + "- status: " + validated);
    }

    /**this method handles the request of a client which wants to join a specific match. First thing to do is check if the match is present in the waiting list
     * then the client remoteview is submitted to the match controller and a new JoinMatchRequest is propagated to the specific match controller which will handle it.
     * @param ltmr lobby to match request sent by the client
     */
    @Override
    public void visit(LobbyToMatchRequest ltmr) {
        Server.log("\n>> Received Join Match Request from: " + ltmr.getUserHash() + " - Match to be joined: " + ltmr.getMatchId());

        if (matches.stream().filter(matchController -> !matchController.isStarted()).map(MatchController::getMatchId).noneMatch(id -> id == ltmr.getMatchId())) { //Predicate : matchId contained in the request is an actual waiting match
            getRemoteView(ltmr.getUserHash()).send(new JoinMatchResponse(false)); //match already started or not existing
            Server.log(">> No such match existing, sent new JoinMatchResponse to " + ltmr.getUserHash() + " status : false");
        } else {
            //envelopes the username of the player to be passed to the controller
            matches.stream().filter(matchController -> (!matchController.isStarted()) && matchController.getMatchId() == ltmr.getMatchId()).findFirst().ifPresent(matchController -> {
                try {
                    matchController.addRemoteView(getRemoteView(ltmr.getUserHash())); //adds the remote view to Match controller
                    JoinMatchRequest jmr = new JoinMatchRequest(ltmr.getMatchId(), getPlayer(ltmr.getUserHash()));
                    submitToController(matchController,jmr, ltmr.getUserHash()); //submits request
                } catch (InterruptedException e) {
                    getRemoteView(ltmr.getUserHash()).send(new TerminatedMatchResponse());
                    Server.log(">> Match " + ltmr.getMatchId() + "interrupted");
                }
            }); //submit the request to the controller of the requested match

        }
    }


    public void visit(MatchRequest mr /*A GENERAL*/) {
        Server.log("\n>> Received generic Match Request from " + mr.getUserHash());
        matches.stream().filter(matchController -> matchController.getId() == mr.getMatchId()).findFirst().ifPresent(matchController -> {
            try {
                matchController.submitRequest(mr);
            } catch (InterruptedException e) {
                getRemoteView(mr.getUserHash()).send(new TerminatedMatchResponse());
            }
        });
    }

    @Override
    public void visit(NewMatchRequest newMatchRequest) {
        Server.log("\n>> Received new New Match Request from: " + newMatchRequest.getUserHash() + " - Number of players: " + newMatchRequest.getNumberOfPlayers());
        MatchController controller = new MatchController(newMatchRequest.getNumberOfPlayers()); //creates new controller
        matches.add(controller);
        controller.addRemoteView(getRemoteView(newMatchRequest.getUserHash())); //adds the view to the new controller
        controllersPool.submit(controller); //runs new controller thread
        Server.log(">> New Match Controller created and view added to it, Match id : " + controller.getId());
        try {
            submitToController(controller, new JoinMatchRequest(controller.getMatchId(), getPlayer(newMatchRequest.getUserHash())), newMatchRequest.getUserHash() );
        }catch (InterruptedException e) {
            getRemoteView(newMatchRequest.getUserHash()).send(new TerminatedMatchResponse()); //match interrupted
        }
    }

    @Override
    public void visit(QuitRequest quitRequest) {
        Server.log(">> Received new Quit Request from: " + quitRequest.getUserHash());
        try {
            getController(quitRequest.getUserHash()).removePlayer(signedPlayers.stream().filter(p -> p.getHashCode() == quitRequest.getUserHash()).findFirst().get());
        }catch (NullPointerException e){
            //
        }
        finally {
            signedPlayers.remove(signedPlayers.stream().filter(p -> p.getHashCode() == quitRequest.getUserHash()).findFirst().get());
            try {
                getRemoteView(quitRequest.getUserHash()).getSocket().close();
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }
    }

    public void submitToController(MatchController controller, MatchRequest request, int userHash) throws InterruptedException{
        request.setUserHash(userHash);
        controller.submitRequest(request);
    }

    public RemoteView getRemoteView(int hashCode){
        return remoteViews.stream().filter(remoteView -> remoteView.getSocket().hashCode() == hashCode).findFirst().get();
    }

    public Player getPlayer (int hashCode){
        return signedPlayers.stream().filter(player -> player.getHashCode() == hashCode).findFirst().get();
    }

    private MatchController getController(int userHash) {
       return matches
                .stream()
                .filter(matchController -> matchController.getPlayers().stream().anyMatch(matchPlayer -> matchPlayer.getHashCode() == userHash))
                .findFirst().orElse(null);
    }


}
