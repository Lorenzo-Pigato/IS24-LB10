package it.polimi.ingsw.lb10.network.response.match;

import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.CLIResponseHandler;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.ResponseVisitable;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.ResponseVisitor;

public class StartedMatchResponse extends Response implements ResponseVisitable {
    private static final long serialVersionUID = 9L;
    private int matchId;

    public StartedMatchResponse(int matchId) {
        this.matchId = matchId;
    }

    /**
     * @param handler
     */
    @Override
    public void accept(ResponseVisitor handler) {
        handler.visit(this);
    }

    public int getMatchId() {
        return matchId;
    }
}
