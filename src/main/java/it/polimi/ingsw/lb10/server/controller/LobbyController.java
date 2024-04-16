package it.polimi.ingsw.lb10.server.controller;

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
        Server.log(">> Received Login Request from: " + lr.getHashCode() + " - Requested username: " + lr.getUsername());
        boolean validated = validateUsername(lr.getUsername());
        if (validated) addSignedPlayer(lr.getHashCode(), lr.getUsername());
        getRemoteView(lr.getHashCode()).send(new BooleanResponse(validated));
        Server.log(">> Sent boolean response to hashcode: " + lr.getHashCode() + "- status: " + validated);
    }

    /**this method handles the request of a client which wants to join a specific match. First thing to do is check if the match is present in the waiting list
     * then the client remoteview is submitted to the match controller and a new JoinMatchRequest is propagated to the specific match controller which will handle it.
     * @param ltmr lobby to match request sent by the client
     */
    @Override
    public void visit(LobbyToMatchRequest ltmr) {

        Server.log(">> Received Join Match Request from: " + ltmr.getHashCode() + " - Match to be joined: " + ltmr.getMatchId());

        if (matches.stream().filter(matchController -> !matchController.isStarted()).map(MatchController::getMatchId).noneMatch(id -> id == ltmr.getMatchId())) { //Predicate : matchId contained in the request is an actual waiting match
            getRemoteView(ltmr.getHashCode()).send(new JoinMatchResponse(false)); //match already started or not existing
        } else {
            //envelopes the username of the player to be passed to the controller
            matches.stream().filter(matchController -> (!matchController.isStarted()) && matchController.getMatchId() == ltmr.getMatchId()).findFirst().ifPresent(matchController -> {
                try {
                    matchController.addRemoteView(getRemoteView(ltmr.getHashCode())); //adds the remote view to Match controller
                    matchController.submitRequest(new JoinMatchRequest(ltmr.getHashCode(), ltmr.getMatchId(), signedPlayers.stream().filter(player -> player.getHashCode() == ltmr.getHashCode()).findFirst().get())); //submits request
                    Server.log(">> Submitted request from " + ltmr.getHashCode() + " to match controller");
                } catch (InterruptedException e) {
                    getRemoteView(ltmr.getHashCode()).send(new TerminatedMatchResponse());
                    Server.log(">> Match " + ltmr.getMatchId() + "interrupted");
                }
            }); //submit the request to the controller of the requested match

            Server.log(">> Match " + ltmr.getMatchId() + "found request has been submitted to match controller ");
        }
    }


    public void visit(MatchRequest mr /*A GENERAL*/) {
        matches.stream().filter(matchController -> matchController.getId() == mr.getMatchId()).findFirst().ifPresent(matchController -> {
            try {
                matchController.submitRequest(mr);
            } catch (InterruptedException e) {
                getRemoteView(mr.getHashCode()).send(new TerminatedMatchResponse());
            }
        });
    }

    @Override
    public void visit(NewMatchRequest newMatchRequest) {
        MatchController controller = new MatchController(newMatchRequest.getNumberOfPlayers()); //creates new controller
        controllersPool.submit(controller); //runs new controller thread
        controller.addRemoteView(getRemoteView(newMatchRequest.getHashCode())); //adds the view to the new controller
        try {
            controller.submitRequest(new JoinMatchRequest(newMatchRequest.getHashCode(), controller.getMatchId(), signedPlayers.stream().filter(player -> player.getHashCode() == newMatchRequest.getHashCode()).findFirst().get()));  //submits the join request
        }catch (InterruptedException e) {
            getRemoteView(newMatchRequest.getHashCode()).send(new TerminatedMatchResponse()); //match interrupted
        }
    }

    public RemoteView getRemoteView(int hashCode){
        return remoteViews.stream().filter(remoteView -> remoteView.getSocket().hashCode() == hashCode).findFirst().get();
    }


}
