package it.polimi.ingsw.lb10.network.response.match;

import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.ResponseVisitor;

public class PlayerLeftResponse extends Response {

    private String username;

    public PlayerLeftResponse(String username) {
        this.username = username;
    }


    @Override
    public void accept(ResponseVisitor handler) {
    handler.visit(this);
    }

    public String getUsername() {return username;}
}
