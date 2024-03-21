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

    public static LobbyController instance(){
        if(instance == null) instance = new LobbyController();
        return instance;
    }


}
