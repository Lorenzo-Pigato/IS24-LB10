package it.polimi.ingsw.lb10.network.requests.match;

import it.polimi.ingsw.lb10.server.visitors.requestDispatch.MatchRequestVisitor;

import java.io.Serial;

public class DrawGoldenFromTableRequest extends MatchRequest {
    @Serial
    private static final long serialVersionUID = 19L;
    private final int index;

    public DrawGoldenFromTableRequest(int matchId, int index) {
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
