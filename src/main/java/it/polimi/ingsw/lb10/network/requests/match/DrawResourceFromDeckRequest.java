package it.polimi.ingsw.lb10.network.requests.match;

import it.polimi.ingsw.lb10.network.requests.match.MatchRequest;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.MatchRequestVisitor;

public class DrawResourceFromDeckRequest extends MatchRequest {
    private static final long serialVersionUID = 20L;

    public DrawResourceFromDeckRequest(int matchId) {
        super(matchId);
    }

    @Override
    public void accept(MatchRequestVisitor visitor) {
        visitor.visit(this);
    }

}
