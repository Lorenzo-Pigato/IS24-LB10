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
    private static ArrayList<MatchController> matches;
    private static ArrayList<Player> signedPlayers;
    private static ArrayList<RemoteView> remoteViews;
    private static final ExecutorService controllersPool = Executors.newCachedThreadPool();
    private static final ArrayList<ServerHeartBeatHandler> heartBeats = new ArrayList<>();

    private LobbyController() {
        signedPlayers = new ArrayList<>();
        matches = new ArrayList<>();
        remoteViews = new ArrayList<>();
    }

    /**
     * @return singleton instance
     */
    public synchronized static LobbyController instance() {
        if (instance == null) instance = new LobbyController();
        return instance;
    }

    /**
     * @param remoteView the remote view to be added to LobbyController list, this remote view will be passed
     * to MatchController and MatchModel to send responses to the client
     */
    public synchronized static void addRemoteView(RemoteView remoteView) {
        remoteViews.add(remoteView);
    }

    /**
     * @param remoteView the remote view that will be removed once a client has been disconnected
     */
    public synchronized static void removeRemoteView(RemoteView remoteView) {
        remoteViews.remove(remoteView);
    }

    /**
     * @param heartBeatHandler the HeartBeatHandler of a new connected client
     */
    public static void addHeartBeat(ServerHeartBeatHandler heartBeatHandler){
        heartBeats.add(heartBeatHandler);
    }

    /**
     * @param heartBeatHandler the HeartBeatHandler of a disconnected client
     */
    public static void removeHeartBeat(ServerHeartBeatHandler heartBeatHandler){
        heartBeatHandler.close();
        heartBeats.remove(heartBeatHandler);
    }

    /**
     * @param username the username to be validated
     * @return true if there's no other player in the signedPlayers list with the same username
     */
    public synchronized boolean validateUsername(@NotNull String username) {
        return signedPlayers
                .stream()
                .map(Player::getUsername)
                .noneMatch((username::equalsIgnoreCase));
    }

    /**
     * @param hashCode the new player's hash code
     * @param username the new player's username
     * this method adds e new player to the LobbyController's list after login.
     */
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
        Server.log(">> [" +  lr.getUserHash() + "] login request: [" + lr.getUsername() + "]");
        boolean validated = validateUsername(lr.getUsername());
        if (validated) addSignedPlayer(lr.getUserHash(), lr.getUsername());
        send(lr.getUserHash(), new BooleanResponse(lr.getUsername(), validated));
        Server.log(">> [" + lr.getUserHash() + "] username validation: " + validated);
    }

    /**
     * this method handles the request of a client which wants to join a specific match. First thing to do is check if the match is present in the waiting list
     * then the client remote view is submitted to the match controller and a new JoinMatchRequest is propagated to the specific match controller which will handle it.
     *
     * @param ltmr lobby to match request sent by the client
     */
    @Override
    public synchronized void visit(@NotNull LobbyToMatchRequest ltmr) {
        Server.log(">> [" + getPlayer(ltmr.getUserHash()).getUsername()+ "] join match [" + ltmr.getMatchId() + "]");
        if (matches.stream().filter(matchController -> !matchController.isStarted()).map(MatchController::getMatchId).noneMatch(id -> id == ltmr.getMatchId())) {
            //Predicate : matchId contained in the request is an actual waiting match
            send(ltmr.getUserHash(), new JoinMatchResponse(false, 0)); //match already started or not existing
            Server.log(">> [" + ltmr.getUserHash() + "]no match found, join match response status : [false]");
        } else {
            //envelopes the username of the player to be passed to the controller
            matches.stream().filter(matchController -> (!matchController.isStarted()) && matchController.getMatchId() == ltmr.getMatchId()).findFirst().ifPresent(matchController -> {
                try {
                    getPlayer(ltmr.getUserHash()).setInMatch(true);
                    matchController.addRemoteView(getRemoteView(ltmr.getUserHash())); //adds the remote view to Match controller
                    JoinMatchRequest jmr = new JoinMatchRequest(ltmr.getMatchId(), getPlayer(ltmr.getUserHash()));
                    submitToController(matchController, jmr, ltmr.getUserHash()); //submits request
                } catch (InterruptedException e) {
                    Server.log(">> match [" + ltmr.getMatchId() + "] interrupted");
                    disconnectClient(ltmr.getUserHash());
                }
            });//submit the request to the controller of the requested match

        }
    }

    /**
     * @param mr the match request received by the client controller
     * This is a key method to handle MatchRequest objects sent by the client, LobbyController just finds out which MatchController
     * the request refers to and submits it to his BlockingQueue.
     */
    public synchronized void visit(@NotNull MatchRequest mr /*A GENERAL*/) {
        matches.stream().filter(matchController -> matchController.getId() == mr.getMatchId()).findFirst().ifPresent(matchController -> {
            try {
                matchController.submitRequest(mr);
            } catch (Exception e) {
            }
        });
    }

    /**
     * @param newMatchRequest request sent by the client when a new match must be created, contains number of players required to start the match.
     */
    @Override
    public synchronized void visit(@NotNull NewMatchRequest newMatchRequest) {
        Server.log(">> [" + getPlayer(newMatchRequest.getUserHash()).getUsername() + "] new match request");
        MatchController controller = new MatchController(newMatchRequest.getNumberOfPlayers()); //creates new controller
        matches.add(controller);
        controller.addRemoteView(getRemoteView(newMatchRequest.getUserHash())); //adds the view to the new controller
        controllersPool.submit(controller); //runs new controller thread
        Server.log(">> created new match: id [" + controller.getMatchId() + "]");
        try {
            getPlayer(newMatchRequest.getUserHash()).setInMatch(true);
            submitToController(controller, new JoinMatchRequest(controller.getMatchId(), getPlayer(newMatchRequest.getUserHash())), newMatchRequest.getUserHash());
        } catch (InterruptedException e) {
        }
    }

    /**
     * @param quitRequest sent by the client whenever he wants to leave the match
     * Lobby controller will disconnect the client once this request is received.
     */
    @Override
    public synchronized void visit(@NotNull QuitRequest quitRequest) {
        disconnectClient(quitRequest.getUserHash());
    }


    /**
     * @param pingRequest the ping request sent by the client.
     * LobbyController will send a pong response.
     */
    @Override
    public synchronized void visit(PingRequest pingRequest) {
        send(pingRequest.getUserHash(), new PongResponse());
    }

    /**
     * @param pongRequest the pong request sent by the client.
     * every pong request sets server heart beat counter to zero as the client is still connected.
     */
    @Override
    public void visit(PongRequest pongRequest) {
        getHeartBeatHandler(pongRequest.getUserHash()).decrementCounter();
    }

    /**
     * @param userHash the hash code assigned to the client whose heartbeat we are searching.
     * @return the client's heart beat.
     */
    private static ServerHeartBeatHandler getHeartBeatHandler(int userHash) {
        return heartBeats.stream().filter(heartBeatHandler -> heartBeatHandler.getHashcode() == userHash).findFirst().orElse(null);
    }

    /**
     * @param controller the match controller assigned to the client's match.
     * @param request the request to be submitted to the match controller.
     * @param userHash the hash code of the player who submitted the request.
     * @throws InterruptedException in case match controller has been interrupted.
     */
    public synchronized void submitToController(@NotNull MatchController controller, @NotNull MatchRequest request, int userHash) throws InterruptedException {
        request.setUserHash(userHash);
        controller.submitRequest(request);
    }

    /**
     * @param hashCode the hashcode of the player whose remote view we are searching.
     * @return the player's remote view.
     */
    private static synchronized RemoteView getRemoteView(int hashCode) {
        return remoteViews.stream().filter(remoteView -> remoteView.getSocket().hashCode() == hashCode).findFirst().orElse(null);
    }

    /**
     * @param hashCode player's hashcode.
     * @return the player matching the hash code.
     */
    private static synchronized Player getPlayer(int hashCode) {
        return signedPlayers.stream().filter(player -> player.getUserHash() == hashCode).findFirst().orElse(null);
    }

    /**
     * @param userHash player hashcode.
     * @return the match controller assigned to the match which the player belongs to.
     */
    private static synchronized MatchController getController(int userHash) {
        return matches
                .stream()
                .filter(matchController -> matchController.getPlayers().stream().anyMatch(matchPlayer -> matchPlayer.getUserHash() == userHash))
                .findFirst().orElse(null);
    }

    /**
     * this method removes the match with the given match id from matches list.
     * @param matchId the id of the match to be terminated.
     */
    public static void removeMatch(int matchId) {
        matches.remove(matches.stream().filter(matchController -> matchController.getMatchId() == matchId).findFirst().orElse(null));
    }

    /**
     * this method disconnects the client from game LobbyController.
     * Client is removed from his match if he is actually playing,
     * is removed from signedPlayers if he belongs to the list, this double check is done because of multiple threads
     * can disconnect a single client in case of connection error.
     * Remote view is closed and removed from remotViews list and heartBeat gets shut down.
     * @param userHash hash code of the player.
     */
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
        Server.log(">> [" + userHash + "] client disconnected" );
    }

    /**
     * @param hashcode receiver client's hash code.
     * @param response the response to be sent.
     */
    public synchronized static void send(int hashcode, Response response){
        getRemoteView(hashcode).send(response);
    }
}
