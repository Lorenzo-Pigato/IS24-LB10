package it.polimi.ingsw.lb10.network.response.match;

import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.CLIResponseHandler;

public class NotYourTurnResponse extends Response {

    private String username;

    public NotYourTurnResponse(String username) {
        this.username = username;
    }

    @Override
    public void accept(CLIResponseHandler handler) {
        handler.visit(this);
    }

    public String getUsername(){return username;}
}
