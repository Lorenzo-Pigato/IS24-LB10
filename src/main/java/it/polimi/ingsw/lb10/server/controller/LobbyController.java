package it.polimi.ingsw.lb10.server.controller;

import it.polimi.ingsw.lb10.network.requests.match.MatchRequest;
import it.polimi.ingsw.lb10.network.requests.preMatch.JoinMatchRequest;
import it.polimi.ingsw.lb10.network.requests.preMatch.LoginRequest;
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
    private ArrayList<MatchController> waitingMatches;
    private ArrayList<MatchController> startedMatches;
    private ArrayList<Player> signedPlayers;
    private static ArrayList<RemoteView> remoteViews;
    private static ExecutorService controllersPool = Executors.newCachedThreadPool();


    private LobbyController() {
        signedPlayers = new ArrayList<>();
        waitingMatches = new ArrayList<>();
        startedMatches = new ArrayList<>();
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

    @Override
    public void visit(JoinMatchRequest jmr) {

        Server.log(">> Received Join Match Request from: " + jmr.getHashCode() + " - Match to be joined: " + jmr.getMatchId());

        if (waitingMatches.stream().map(MatchController::getMatchId).noneMatch(id -> id == jmr.getMatchId())) { //Predicate : matchId contained in the request is an actual waiting match
            getRemoteView(jmr.getHashCode()).send(new JoinMatchResponse(false)); //match already started or not existing
        } else {
            jmr.setUsername(signedPlayers.stream().filter(player -> player.getHashCode() == jmr.getHashCode()).findFirst().get().getUsername()); //envelopes the username of the player to be passed to the controller
            waitingMatches.stream().filter(matchController -> matchController.getMatchId() == jmr.getMatchId()).findFirst().ifPresent(matchController -> {
                try {
                    matchController.addRemoteView(getRemoteView(jmr.getHashCode())); //adds the remote view to Match controller
                    matchController.submitRequest(jmr); //submits request
                } catch (InterruptedException e) {
                    getRemoteView(jmr.getHashCode()).send(new TerminatedMatchResponse());
                    Server.log(">> Match " + jmr.getMatchId() + "interrupted");
                }
            }); //submit the request to the controller of the requested match

            Server.log(">> Match " + jmr.getMatchId() + "found request has been submitted to match controller ");
        }
    }


    public void visit(MatchRequest mr /*A GENERAL*/) {
        startedMatches.stream().filter(matchController -> matchController.hashCode() == mr.getMatchId()).findFirst().ifPresent(matchController -> {
            try {
                matchController.submitRequest(mr);
            } catch (InterruptedException e) {
                getRemoteView(mr.getHashCode()).send(new TerminatedMatchResponse());
            }
        });
    }


    public RemoteView getRemoteView(int hashCode){
        return remoteViews.stream().filter(remoteView -> remoteView.getSocket().hashCode() == hashCode).findFirst().get();
    }


}
