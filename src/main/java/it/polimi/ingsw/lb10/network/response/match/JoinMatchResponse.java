package it.polimi.ingsw.lb10.network.response.match;

import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.ResponseVisitor;

import java.io.Serial;

public class JoinMatchResponse extends Response {
    @Serial
    private static final long serialVersionUID = 5L;
    private final boolean joined;
    private final int matchId;

    public JoinMatchResponse(boolean joined, int matchId) {
        this.joined = joined;
        this.matchId = matchId;
    }

    @Override
    public void accept(ResponseVisitor handler) {
        handler.visit(this);
    }

    public boolean getJoined() {
        return joined;
    }

    public int getMatchId() {
        return matchId;
    }
}
