package it.polimi.ingsw.lb10.network.requests.match;

import it.polimi.ingsw.lb10.server.visitors.requestDispatch.MatchRequestVisitor;

import java.io.Serial;

public class PrivateQuestsRequest extends MatchRequest {
    @Serial
    private static final long serialVersionUID = 28L;

    public PrivateQuestsRequest(int matchId) {
        super(matchId);
    }

    /**
     * @param visitor MatchController
     */
    @Override
    public void accept(MatchRequestVisitor visitor) {
        visitor.visit(this);
    }
}
