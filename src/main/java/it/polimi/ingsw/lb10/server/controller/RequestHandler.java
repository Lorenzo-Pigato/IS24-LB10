package it.polimi.ingsw.lb10.server.controller;

import it.polimi.ingsw.lb10.network.requests.preMatch.LoginRequest;

/**
 * This class has been implemented to decouple ClientConnection, MatchController and LobbyController, every request flowing into ClientConnection is
 * put in RequestHandler BlockingQueue willing to be dispatched either to the Lobby or the Match controller based on Request dynamic type (PreMatch / Match)
 */
public class RequestHandler {

    private final LobbyController lobbyController = LobbyController.instance();
    private static RequestHandler instance;

    public RequestHandler() {}

    public static RequestHandler instance(){
        if(instance == null) instance = new RequestHandler();
        return instance;
    }

    public void handle(LoginRequest loginRequest){

    }

}
