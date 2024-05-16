package it.polimi.ingsw.lb10.server.controller;

import it.polimi.ingsw.lb10.network.heartbeat.ServerHeartBeatHandler;
import it.polimi.ingsw.lb10.network.requests.PongRequest;
import it.polimi.ingsw.lb10.network.requests.QuitRequest;
import it.polimi.ingsw.lb10.network.requests.match.JoinMatchRequest;
import it.polimi.ingsw.lb10.network.requests.match.MatchRequest;
import it.polimi.ingsw.lb10.network.requests.preMatch.LobbyToMatchRequest;
import it.polimi.ingsw.lb10.network.requests.preMatch.LoginRequest;
import it.polimi.ingsw.lb10.network.requests.preMatch.NewMatchRequest;
import it.polimi.ingsw.lb10.network.requests.preMatch.PingRequest;
import it.polimi.ingsw.lb10.network.response.PongResponse;
import it.polimi.ingsw.lb10.network.response.Response;
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
import java.util.concurrent.ScheduledExecutorService;

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
    private static final ArrayList<ServerHeartBeatHandler> heartBeats = new ArrayList<>();

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

    public static void addHeartBeat(ServerHeartBeatHandler heartBeatHandler){
        heartBeats.add(heartBeatHandler);
    }

    public static void removeHeartBeat(ServerHeartBeatHandler heartBeatHandler){
        heartBeatHandler.close();
        heartBeats.remove(heartBeatHandler);
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

    /**
     * this method handles the login request sent by the client, checking if the requested username is valid
     *
     * @param lr login request sent by the client
     */
    @Override
    public synchronized void visit(@NotNull LoginRequest lr) {
        Server.log(">>login request [username : " + lr.getUsername() + ", " + lr.getUserHash() + "]");
        boolean validated = validateUsername(lr.getUsername());
        if (validated) addSignedPlayer(lr.getUserHash(), lr.getUsername());
        send(lr.getUserHash(), new BooleanResponse(lr.getUsername(), validated));
        Server.log(">>boolean response [username : " + lr.getUsername() + ", " + lr.getUserHash() + "] " + validated);
    }

    /**
     * this method handles the request of a client which wants to join a specific match. First thing to do is check if the match is present in the waiting list
     * then the client remote view is submitted to the match controller and a new JoinMatchRequest is propagated to the specific match controller which will handle it.
     *
     * @param ltmr lobby to match request sent by the client
     */
    @Override
    public synchronized void visit(@NotNull LobbyToMatchRequest ltmr) {
        Server.log(">>join match [username : " + getPlayer(ltmr.getUserHash()).getUsername() + ", id : " + ltmr.getMatchId() + "]");
        if (matches.stream().filter(matchController -> !matchController.isStarted()).map(MatchController::getMatchId).noneMatch(id -> id == ltmr.getMatchId())) {
            //Predicate : matchId contained in the request is an actual waiting match
            send(ltmr.getUserHash(), new JoinMatchResponse(false, 0)); //match already started or not existing
            Server.log(">>no match found, join match response [status : false]");
        } else {
            //envelopes the username of the player to be passed to the controller
            matches.stream().filter(matchController -> (!matchController.isStarted()) && matchController.getMatchId() == ltmr.getMatchId()).findFirst().ifPresent(matchController -> {
                try {
                    getPlayer(ltmr.getUserHash()).setInMatch(true);
                    matchController.addRemoteView(getRemoteView(ltmr.getUserHash())); //adds the remote view to Match controller
                    JoinMatchRequest jmr = new JoinMatchRequest(ltmr.getMatchId(), getPlayer(ltmr.getUserHash()));
                    submitToController(matchController, jmr, ltmr.getUserHash()); //submits request
                } catch (InterruptedException e) {
                    Server.log(">>match " + ltmr.getMatchId() + "interrupted");
                    disconnectClient(ltmr.getUserHash());
                }
            });//submit the request to the controller of the requested match

        }
    }

    public synchronized void visit(@NotNull MatchRequest mr /*A GENERAL*/) {
        matches.stream().filter(matchController -> matchController.getId() == mr.getMatchId()).findFirst().ifPresent(matchController -> {
            try {
                matchController.submitRequest(mr);
            } catch (Exception e) {
                send(mr.getUserHash(), new TerminatedMatchResponse());
            }
        });
    }

    @Override
    public synchronized void visit(@NotNull NewMatchRequest newMatchRequest) {
        Server.log(">>new match request [username : " + getPlayer(newMatchRequest.getUserHash()).getUsername() + "]");
        MatchController controller = new MatchController(newMatchRequest.getNumberOfPlayers()); //creates new controller
        matches.add(controller);
        controller.addRemoteView(getRemoteView(newMatchRequest.getUserHash())); //adds the view to the new controller
        controllersPool.submit(controller); //runs new controller thread
        Server.log("created new match [id : " + controller.getMatchId());
        try {
            getPlayer(newMatchRequest.getUserHash()).setInMatch(true);
            submitToController(controller, new JoinMatchRequest(controller.getMatchId(), getPlayer(newMatchRequest.getUserHash())), newMatchRequest.getUserHash());
        } catch (InterruptedException e) {
            send(newMatchRequest.getUserHash(), new TerminatedMatchResponse()); //match interrupted
        }
    }

    @Override
    public synchronized void visit(@NotNull QuitRequest quitRequest) {
        disconnectClient(quitRequest.getUserHash());
    }


    @Override
    public synchronized void visit(PingRequest pingRequest) {
        send(pingRequest.getUserHash(), new PongResponse());
    }

    @Override
    public void visit(PongRequest pongRequest) {
        getHeartBeatHandler(pongRequest.getUserHash()).decrementCounter();
    }

    private static ServerHeartBeatHandler getHeartBeatHandler(int userHash) {
        return heartBeats.stream().filter(heartBeatHandler -> heartBeatHandler.getHashcode() == userHash).findFirst().orElse(null);
    }

    public synchronized void submitToController(@NotNull MatchController controller, @NotNull MatchRequest request, int userHash) throws InterruptedException {
        request.setUserHash(userHash);
        controller.submitRequest(request);
    }

    private static synchronized RemoteView getRemoteView(int hashCode) {
        return remoteViews.stream().filter(remoteView -> remoteView.getSocket().hashCode() == hashCode).findFirst().orElse(null);
    }

    private static synchronized Player getPlayer(int hashCode) {
        return signedPlayers.stream().filter(player -> player.getUserHash() == hashCode).findFirst().orElse(null);
    }

    private static synchronized MatchController getController(int userHash) {
        return matches
                .stream()
                .filter(matchController -> matchController.getPlayers().stream().anyMatch(matchPlayer -> matchPlayer.getUserHash() == userHash))
                .findFirst().orElse(null);
    }

    public static void terminateMatch(int matchId) {
        matches.remove(matches.stream().filter(matchController -> matchController.getMatchId() == matchId).findFirst().orElse(null));
    }

    public static synchronized void disconnectClient(int userHash) {
        Player player = getPlayer(userHash);
        if(signedPlayers.contains(player)){
            if (player.isInMatch()) {
                MatchController controller = getController(userHash);
                controller.removePlayer(player);
            }
        signedPlayers.remove(getPlayer(userHash));
        }
        try {
            removeHeartBeat(getHeartBeatHandler(userHash));
            getRemoteView(userHash).getSocket().close();
            removeRemoteView(getRemoteView(userHash));
        } catch (IOException e) {
            //
        }
        Server.log(">>client disconnected : " + userHash);
    }

    public synchronized static void send(int hashcode, Response response){
        getRemoteView(hashcode).send(response);
    }
}
