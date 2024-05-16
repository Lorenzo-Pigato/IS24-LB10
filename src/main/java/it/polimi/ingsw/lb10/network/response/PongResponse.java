package it.polimi.ingsw.lb10.network.response;

import it.polimi.ingsw.lb10.server.visitors.responseDespatch.ResponseVisitor;

public class PongResponse extends Response{

    @Override
    public void accept(ResponseVisitor handler) {
        handler.visit(this);
    }
}
