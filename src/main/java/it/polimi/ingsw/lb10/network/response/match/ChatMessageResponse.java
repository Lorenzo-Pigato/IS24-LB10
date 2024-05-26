package it.polimi.ingsw.lb10.network.response.match;

import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.ResponseVisitor;

import java.io.Serial;

public class ChatMessageResponse extends Response {
    @Serial
    private static final long serialVersionUID = 3L;
    private final String senderUsername;
    private final String message;
    private final boolean isPrivate;

    public ChatMessageResponse(String senderUsername, String message, boolean isPrivate) {
        this.senderUsername = senderUsername;
        this.message = message;
        this.isPrivate = isPrivate;
    }

    @Override
    public void accept(ResponseVisitor handler) {
        handler.visit(this);
    }

    public String getSender() {
        return senderUsername;
    }

    public String getMessage() {
        return message;
    }
    public boolean isPrivate() {return isPrivate;}
}
