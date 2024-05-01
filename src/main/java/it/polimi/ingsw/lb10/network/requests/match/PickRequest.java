package it.polimi.ingsw.lb10.network.requests.match;

import it.polimi.ingsw.lb10.server.visitors.requestDispatch.MatchRequestVisitor;

public class PickRequest extends MatchRequest {

    public PickRequest(int matchId) {
        super(matchId);
    }

    @Override
    public void accept(MatchRequestVisitor visitor) {
        visitor.visit(this);
    }
}
