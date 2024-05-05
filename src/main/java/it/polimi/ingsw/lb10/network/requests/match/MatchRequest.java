package it.polimi.ingsw.lb10.network.requests.match;

import it.polimi.ingsw.lb10.network.requests.Request;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.LobbyRequestVisitable;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.LobbyRequestVisitor;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.MatchRequestVisitable;

import java.io.Serial;

public abstract class MatchRequest extends Request implements LobbyRequestVisitable, MatchRequestVisitable {
    @Serial
    private static final long serialVersionUID = 23L;
    private final int matchId;

    public MatchRequest(int matchId) {
        this.matchId = matchId;
    }

    public int getMatchId() {
        return matchId;
    }

    @Override
    public void accept(LobbyRequestVisitor handler) {
        handler.visit(this);
    }


}
