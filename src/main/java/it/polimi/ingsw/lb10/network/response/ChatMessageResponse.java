package it.polimi.ingsw.lb10.network.response;

import it.polimi.ingsw.lb10.server.visitors.responseDespatch.CLIResponseHandler;

public class ChatMessageResponse extends Response {

    private final String message;

    public ChatMessageResponse(String message) {
        this.message = message;
    }

    /**
     * @param handler
     */
    @Override
    public void accept(CLIResponseHandler handler) {
        handler.visit(this);
    }
}
