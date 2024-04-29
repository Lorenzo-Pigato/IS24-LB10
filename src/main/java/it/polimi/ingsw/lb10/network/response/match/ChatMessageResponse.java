package it.polimi.ingsw.lb10.network.response.match;

import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.CLIResponseHandler;

public class ChatMessageResponse extends Response {
    private static final long serialVersionUID = 3L;
    private String senderUsername;
    private String message;

    public ChatMessageResponse(String senderUsername, String message) {this.senderUsername = senderUsername;this.message = message;}

    @Override
    public void accept(CLIResponseHandler handler) {
        handler.visit(this);
    }
}
