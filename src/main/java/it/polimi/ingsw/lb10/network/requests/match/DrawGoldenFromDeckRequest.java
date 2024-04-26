package it.polimi.ingsw.lb10.network.requests.match;

import it.polimi.ingsw.lb10.server.visitors.requestDispatch.LobbyRequestVisitor;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.MatchRequestVisitor;

public class DrawGoldenFromDeckRequest extends MatchRequest {


    public DrawGoldenFromDeckRequest(int matchId) {
        super(matchId);
    }

    /**
     * @param handler
     */
    @Override
    public void accept(LobbyRequestVisitor handler) {
        handler.visit(this);
    }

    /**
     * @param visitor
     */
    @Override
    public void accept(MatchRequestVisitor visitor) {

    }
}
