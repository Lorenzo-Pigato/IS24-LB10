package it.polimi.ingsw.lb10.network.requests.match;

import it.polimi.ingsw.lb10.network.requests.Request;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.LobbyRequestVisitable;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.LobbyRequestVisitor;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.MatchRequestVisitable;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.MatchRequestVisitor;

public abstract class MatchRequest extends Request implements LobbyRequestVisitable, MatchRequestVisitable {

    private final int matchId;

    public MatchRequest(int hashCode, int matchId) {
        super(hashCode);
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
