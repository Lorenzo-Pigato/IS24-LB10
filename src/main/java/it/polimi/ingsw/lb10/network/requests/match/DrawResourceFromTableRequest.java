package it.polimi.ingsw.lb10.network.requests.match;

import it.polimi.ingsw.lb10.network.requests.match.MatchRequest;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.MatchRequestVisitor;

public class DrawResourceFromTableRequest extends MatchRequest {
    private static final long serialVersionUID = 21L;
    private int index;

    public DrawResourceFromTableRequest(int matchId, int index) {
        super(matchId);
        this.index = index;
    }

    @Override
    public void accept(MatchRequestVisitor visitor) {
        visitor.visit(this);
    }

    public int getIndex(){return index;}
}
