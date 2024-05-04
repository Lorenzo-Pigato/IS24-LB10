package it.polimi.ingsw.lb10.network.response.match;

import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.ResponseVisitor;

public class ServerNotification extends Response {

    private final String message;

    public ServerNotification(String message) {
        this.message = message;
    }

    @Override
    public void accept(ResponseVisitor handler) {
        handler.visit(this);
    }

    public String getMessage() {
        return message;
    }

}
