package it.polimi.ingsw.lb10.network.response.match;

import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.ResponseVisitor;

public class ChatMessageResponse extends Response {
    private static final long serialVersionUID = 3L;
    private String senderUsername;
    private String message;
    private boolean isPrivate;

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
