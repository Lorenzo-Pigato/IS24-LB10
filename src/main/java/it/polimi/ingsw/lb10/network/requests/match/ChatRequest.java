package it.polimi.ingsw.lb10.network.requests.match;

import it.polimi.ingsw.lb10.server.visitors.requestDispatch.MatchRequestVisitor;

public class ChatRequest extends MatchRequest{

    private String message;

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
