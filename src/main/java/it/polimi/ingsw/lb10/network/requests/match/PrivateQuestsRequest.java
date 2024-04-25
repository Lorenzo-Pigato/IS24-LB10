package it.polimi.ingsw.lb10.network.requests.match;

import it.polimi.ingsw.lb10.server.visitors.requestDispatch.LobbyRequestVisitor;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.MatchRequestVisitor;

public class PrivateQuestsRequest extends MatchRequest {

    public PrivateQuestsRequest(int matchId) {
        super(matchId);
    }

    /**
     * @param handler LobbyController
     */
    @Override
    public void accept(LobbyRequestVisitor handler) {
        handler.visit(this);

    }

    /**
     * @param visitor MatchController
     */
    @Override
    public void accept(MatchRequestVisitor visitor) {
        visitor.visit(this);
    }
}
