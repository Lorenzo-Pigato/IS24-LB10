package it.polimi.ingsw.lb10.server.controller;

import it.polimi.ingsw.lb10.server.Server;
import it.polimi.ingsw.lb10.server.model.MatchModel;
import it.polimi.ingsw.lb10.server.model.Player;

import java.util.ArrayList;

/**
 * This SINGLETON class handles the dispatch of players inside the different lobbies,
 * keeps track of starting or waiting match and signed in players separated from Server.
 * Every class method has to be SYNCHRONIZED.
 */

public class LobbyController {

    private static LobbyController instance;
    private ArrayList<MatchModel> waitingMatches;
    private ArrayList<MatchModel> startedMatches;
    private ArrayList<Player> signedPlayers;

    private LobbyController(){
        signedPlayers = new ArrayList<>();
        waitingMatches = new ArrayList<>();
        startedMatches = new ArrayList<>();
    }

    public synchronized static LobbyController instance(){
        if(instance == null) instance = new LobbyController();
        return instance;
    }

    public synchronized boolean validateUsername(String username){
        return signedPlayers.stream().map(player -> player.getUsername()).noneMatch((us->username.equalsIgnoreCase(us)));
    }

    public synchronized void addSignedPlayer(String username){
        signedPlayers.add(new Player(username));
    }

}
