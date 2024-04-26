package it.polimi.ingsw.lb10.network.response.match;

import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.model.Player;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.CLIResponseHandler;

import java.util.ArrayList;

public class GameSetupResponse extends Response {

    private Object[] players;
    public GameSetupResponse(Object[] players) {this.players = players;}

    @Override
    public void accept(CLIResponseHandler handler) {
        handler.visit(this);
    }

    public Object[] getPlayers() {return players;}
}
