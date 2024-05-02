package it.polimi.ingsw.lb10.network.requests.match;

import it.polimi.ingsw.lb10.server.visitors.requestDispatch.LobbyRequestVisitor;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.MatchRequestVisitor;

public class DrawGoldenFromDeckRequest extends MatchRequest {
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
