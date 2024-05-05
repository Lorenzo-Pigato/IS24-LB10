package it.polimi.ingsw.lb10.network.requests.match;

import it.polimi.ingsw.lb10.server.visitors.requestDispatch.MatchRequestVisitor;

import java.io.Serial;

public class ShowPlayerRequest extends MatchRequest {
    @Serial
    private static final long serialVersionUID = 29L;

    private final String username;

    public ShowPlayerRequest(int matchId, String username) {
        super(matchId);
        this.username = username;
    }

    /**
     * @param visitor
     */
    @Override
    public void accept(MatchRequestVisitor visitor) {
        visitor.visit(this);
    }
}
