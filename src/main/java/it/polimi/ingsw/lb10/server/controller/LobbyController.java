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
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This SINGLETON class handles the login requests and dispatches players inside match controllers,
 * keeps track of starting or waiting match and signed in players separated from Server.
 * Every class method has to be SYNCHRONIZED.
 */

public class LobbyController implements LobbyRequestVisitor {

    private static LobbyController instance;
    private static ArrayList<MatchController> matches;
    private static ArrayList<Player> signedPlayers;
    private static ArrayList<RemoteView> remoteViews;
    private static final ExecutorService controllersPool = Executors.newCachedThreadPool();


    private LobbyController() {
        signedPlayers = new ArrayList<>();
        matches = new ArrayList<>();
        remoteViews = new ArrayList<>();
        Server.log("\n\n >> Server online ");
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

    // ----------REQUEST HANDLING -------------//

    /**this method handles the login request sent by the client, checking if the requested username is valid
     * @param lr login request sent by the client
     */
    @Override
    public synchronized void visit(@NotNull LoginRequest lr) {
        Server.log(">> Received Login Request from: " + lr.getUserHash() + " - Requested username: " + lr.getUsername());
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
    public synchronized void visit(@NotNull LobbyToMatchRequest ltmr) {
        Server.log(">> Received new Join Match Request from: " + ltmr.getUserHash());
        Server.log(">> Match to be joined: " + ltmr.getMatchId());

        if (matches.stream().filter(matchController -> !matchController.isStarted()).map(MatchController::getMatchId).noneMatch(id -> id == ltmr.getMatchId())){
            //Predicate : matchId contained in the request is an actual waiting match
            getRemoteView(ltmr.getUserHash()).send(new JoinMatchResponse(false, 0)); //match already started or not existing
            Server.log(">> No such match existing, sent new JoinMatchResponse to " + ltmr.getUserHash() + " status : false");
        }else{
            //envelopes the username of the player to be passed to the controller
            matches.stream().filter(matchController -> (!matchController.isStarted()) && matchController.getMatchId() == ltmr.getMatchId()).findFirst().ifPresent(matchController -> {
                try {
                    matchController.addRemoteView(getRemoteView(ltmr.getUserHash())); //adds the remote view to Match controller
                    JoinMatchRequest jmr = new JoinMatchRequest(ltmr.getMatchId(), getPlayer(ltmr.getUserHash()));
                    submitToController(matchController,jmr, ltmr.getUserHash()); //submits request
                    Server.log(">> Request submitted to MatchController");
                } catch (Exception e) {
                    e.printStackTrace();
                    Server.log(">> Match " + ltmr.getMatchId() + "interrupted");
                }
            }); //submit the request to the controller of the requested match

        }
    }

    public synchronized void visit(@NotNull MatchRequest mr /*A GENERAL*/) {
        Server.log(">> Received generic Match Request from " + mr.getUserHash());
        matches.stream().filter(matchController -> matchController.getId() == mr.getMatchId()).findFirst().ifPresent(matchController -> {
            try {
                matchController.submitRequest(mr);
            } catch(Exception e){
                e.printStackTrace();
                getRemoteView(mr.getUserHash()).send(new TerminatedMatchResponse());
            }

        });
    }

    @Override
    public synchronized void visit(@NotNull NewMatchRequest newMatchRequest) {
        Server.log(">> New Match Request from: " + newMatchRequest.getUserHash() + " " + newMatchRequest.getNumberOfPlayers() + " players");
        MatchController controller = new MatchController(newMatchRequest.getNumberOfPlayers()); //creates new controller
        matches.add(controller);
            controller.addRemoteView(getRemoteView(newMatchRequest.getUserHash())); //adds the view to the new controller
            controllersPool.submit(controller); //runs new controller thread
            Server.log(">> New Match Controller created, Match id : " + controller.getId());
            try {
                submitToController(controller, new JoinMatchRequest(controller.getMatchId(), getPlayer(newMatchRequest.getUserHash())), newMatchRequest.getUserHash());
            } catch (Exception e) {
                e.printStackTrace();
                getRemoteView(newMatchRequest.getUserHash()).send(new TerminatedMatchResponse()); //match interrupted
        }
    }

    @Override
    public synchronized void visit(@NotNull QuitRequest quitRequest) {
        System.out.println(quitRequest.getUserHash() + " quit req");
        Server.log(">> Received new Quit Request from: " + quitRequest.getUserHash());
       disconnectClient(quitRequest.getUserHash());
    }

    public synchronized void submitToController(@NotNull MatchController controller, @NotNull MatchRequest request, int userHash) throws InterruptedException{
        request.setUserHash(userHash);
        controller.submitRequest(request);
    }

    private static synchronized RemoteView getRemoteView(int hashCode){
        return remoteViews.stream().filter(remoteView -> remoteView.getSocket().hashCode() == hashCode).findFirst().get();
    }

    private static synchronized Player getPlayer (int hashCode){
        return signedPlayers.stream().filter(player -> player.getUserHash() == hashCode).findFirst().get();
    }

    private static synchronized MatchController getController(int userHash) {
       return matches
                .stream()
                .filter(matchController -> matchController.getPlayers().stream().anyMatch(matchPlayer -> matchPlayer.getUserHash() == userHash))
                .findFirst().orElse(null);
    }
    public void  terminateMatch(int matchId){
        matches.stream().filter(matchController-> matchController.getMatchId() == matchId).findFirst().get().getPlayers().forEach(player -> disconnectClient(player.getUserHash()));
        matches.remove(matches.stream().filter(matchController -> matchController.getMatchId() == matchId).findFirst().get());
    }
    public static synchronized void disconnectClient(int userHash){
        try {
            System.out.println("removing from lobby player " + userHash);
            getController(userHash).removePlayer(getPlayer(userHash));
        }catch (NoSuchElementException e){
            //ok
        }
        finally {
            signedPlayers.remove(getPlayer(userHash));
            try {
                getRemoteView(userHash).getSocket().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
