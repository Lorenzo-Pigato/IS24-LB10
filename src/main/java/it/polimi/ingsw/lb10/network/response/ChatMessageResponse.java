package it.polimi.ingsw.lb10.network.response;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.CLIResponseHandler;

public class ChatMessageResponse extends Response {
    private static final long serialVersionUID = 14L;
    private final String message;
    private final String sender;

    public ChatMessageResponse(String message, String sender) {this.message = message;this.sender = sender;}

    /**
     * @param handler
     */
    @Override
    public void accept(CLIResponseHandler handler) {
        handler.visit(this);
    }

    public String getMessage() {return message;}
    public String getSender() {return sender;}
}
