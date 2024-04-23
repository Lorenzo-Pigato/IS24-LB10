package it.polimi.ingsw.lb10.network.requests.match;

import it.polimi.ingsw.lb10.network.requests.Request;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.LobbyRequestVisitor;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.MatchRequestVisitor;

public class PrivateQuestSelectedRequest extends MatchRequest{

    public PrivateQuestSelectedRequest(int matchId) {
        super(matchId);
    }

    /**
     * @param handler
     */
    @Override
    public void accept(LobbyRequestVisitor handler) {

    }

    /**
     * @param visitor
     */
    @Override
    public void accept(MatchRequestVisitor visitor) {

    }
}
