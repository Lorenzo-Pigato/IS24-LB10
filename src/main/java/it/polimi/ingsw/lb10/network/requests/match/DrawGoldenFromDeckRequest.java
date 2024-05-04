package it.polimi.ingsw.lb10.network.requests.match;

import it.polimi.ingsw.lb10.server.visitors.requestDispatch.MatchRequestVisitor;

import java.io.Serial;

public class DrawGoldenFromDeckRequest extends MatchRequest {
    @Serial
    private static final long serialVersionUID = 18L;

    public DrawGoldenFromDeckRequest(int matchId) {
        super(matchId);
    }

    /**
     * @param visitor
     */
    @Override
    public void accept(MatchRequestVisitor visitor) {
        visitor.visit(this);
    }
}
