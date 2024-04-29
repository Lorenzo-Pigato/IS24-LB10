package it.polimi.ingsw.lb10.network.requests.match;

import it.polimi.ingsw.lb10.server.visitors.requestDispatch.LobbyRequestVisitor;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.MatchRequestVisitor;

public class PrivateQuestsRequest extends MatchRequest {
    private static final long serialVersionUID = 27L;
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
