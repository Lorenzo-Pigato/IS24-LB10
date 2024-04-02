package it.polimi.ingsw.lb10.server.controller;

import it.polimi.ingsw.lb10.network.requests.preMatch.LoginRequest;
import it.polimi.ingsw.lb10.network.response.BooleanResponse;
import it.polimi.ingsw.lb10.server.Server;
import it.polimi.ingsw.lb10.server.model.MatchModel;
import it.polimi.ingsw.lb10.server.model.Player;
import it.polimi.ingsw.lb10.server.view.RemoteView;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.RequestVisitor;
import org.jetbrains.annotations.NotNull;

import java.net.Socket;
import java.util.ArrayList;

/**
 * This SINGLETON class handles the dispatch of players inside the different lobbies,
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

    @Override
    public void visit(@NotNull LoginRequest lr) {
        if(validateUsername(lr.getUsername())){
            addSignedPlayer(lr.getUsername());
            getRemoteView(lr.getHashCode()).send(new BooleanResponse(true));
        }
        getRemoteView(lr.getHashCode()).send(new BooleanResponse(false));
    }

    public RemoteView getRemoteView(int hashCode){
        return remoteViews.stream().filter(remoteView -> remoteView.getSocket().hashCode() == hashCode).findFirst().get();
    }


}
