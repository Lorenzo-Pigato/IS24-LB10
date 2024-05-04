package it.polimi.ingsw.lb10.network.requests.match;

import it.polimi.ingsw.lb10.server.visitors.requestDispatch.MatchRequestVisitor;

import java.io.Serial;

public class PickRequest extends MatchRequest {

    @Serial
    private static final long serialVersionUID = 25L;

    public PickRequest(int matchId) {
        super(matchId);
    }

    @Override
    public void accept(MatchRequestVisitor visitor) {
        visitor.visit(this);
    }
}
