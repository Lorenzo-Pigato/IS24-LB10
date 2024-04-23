package it.polimi.ingsw.lb10.server.controller;

import it.polimi.ingsw.lb10.network.requests.preMatch.LoginRequest;
import it.polimi.ingsw.lb10.network.response.BooleanResponse;
import it.polimi.ingsw.lb10.server.Server;
import it.polimi.ingsw.lb10.server.model.MatchModel;
import it.polimi.ingsw.lb10.server.model.Player;
import it.polimi.ingsw.lb10.server.view.RemoteView;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.RequestVisitor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * This SINGLETON class handles the login requests and dispatches players inside match controllers,
 * keeps track of starting or waiting match and signed in players separated from Server.
 * Every class method has to be SYNCHRONIZED.
 */

public class LobbyController implements RequestVisitor{

    private static LobbyController instance;
    private ArrayList<MatchModel> waitingMatches;
    private ArrayList<MatchModel> startedMatches;
    private ArrayList<Player> signedPlayers;
    private static ArrayList<RemoteView> remoteViews;


    private LobbyController(){
        signedPlayers = new ArrayList<>();
        waitingMatches = new ArrayList<>();
        startedMatches = new ArrayList<>();
        remoteViews = new ArrayList<>();
    }

    public synchronized static LobbyController instance(){
        if(instance == null) instance = new LobbyController();
        return instance;
    }

    public synchronized static void addRemoteView(RemoteView remoteView){
        remoteViews.add(remoteView);
    }

    public synchronized static void removeRemoteView(RemoteView remoteView){
        remoteViews.remove(remoteView);
    }
    public synchronized boolean validateUsername(String username){
        return signedPlayers
                .stream()
                .map(Player::getUsername)
                .noneMatch((username::equalsIgnoreCase));
    }

    public synchronized void addSignedPlayer(String username){
        signedPlayers.add(new Player(username));
    }

    // -------- REQUEST HANDLING -------------//
    @Override
    public void visit(@NotNull LoginRequest lr) {
        Server.log(">> Received Login Request from: " + lr.getHashCode() + " - Requested username: " + lr.getUsername());
        boolean validated = validateUsername(lr.getUsername());
        if(validated) addSignedPlayer(lr.getUsername());
        getRemoteView(lr.getHashCode()).send(new BooleanResponse(validated));
        Server.log(">> Sent boolean response to hashcode: " + lr.getHashCode() + "- status: " + validated);
    }

    public RemoteView getRemoteView(int hashCode){
        return remoteViews.stream().filter(remoteView -> remoteView.getSocket().hashCode() == hashCode).findFirst().get();
    }


}
