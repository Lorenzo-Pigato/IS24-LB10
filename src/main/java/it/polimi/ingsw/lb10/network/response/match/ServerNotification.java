package it.polimi.ingsw.lb10.network.response.match;

import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.ResponseVisitor;
import javafx.scene.paint.Color;

public class ServerNotification extends Response {

    private final String message;
    private final boolean status;

    public ServerNotification(String message, boolean status) {
        this.message = message;
        this.status = status;
    }

    @Override
    public void accept(ResponseVisitor handler) {
        handler.visit(this);
    }

    public String getMessage() {
        return message;
    }
    public boolean getStatus(){return status;}

}
