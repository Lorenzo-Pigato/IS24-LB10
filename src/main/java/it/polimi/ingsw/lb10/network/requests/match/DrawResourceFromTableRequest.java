package it.polimi.ingsw.lb10.network.requests.match;

import it.polimi.ingsw.lb10.server.visitors.requestDispatch.MatchRequestVisitor;

import java.io.Serial;

public class DrawResourceFromTableRequest extends MatchRequest {
    @Serial
    private static final long serialVersionUID = 21L;
    private final int index;

    public DrawResourceFromTableRequest(int matchId, int index) {
        super(matchId);
        this.index = index;
    }

    @Override
    public void accept(MatchRequestVisitor visitor) {
        visitor.visit(this);
    }

    public int getIndex() {
        return index;
    }
}
