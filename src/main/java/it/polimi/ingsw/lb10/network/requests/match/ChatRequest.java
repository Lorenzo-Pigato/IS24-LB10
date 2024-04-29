package it.polimi.ingsw.lb10.network.requests.match;

import it.polimi.ingsw.lb10.server.visitors.requestDispatch.MatchRequestVisitor;

import java.io.Serial;

public class ChatRequest extends MatchRequest{
    @Serial
    private static final long serialVersionUID = 17L;

    private final String message;

    public ChatRequest(int matchId, String message) {
        super(matchId);
        this.message = message;
    }

    public String getMessage() {return this.message;}

    /**
     * @param visitor
     */
    @Override
    public void accept(MatchRequestVisitor visitor) {
        visitor.visit(this);
    }
}
