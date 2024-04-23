package it.polimi.ingsw.lb10.network.requests.preMatch;

import it.polimi.ingsw.lb10.server.visitors.requestDispatch.LobbyRequestVisitor;

import java.io.Serializable;

public class LobbyToMatchRequest extends LobbyRequest implements Serializable {

    private final int matchId;

    public LobbyToMatchRequest(int matchId) {
        this.matchId = matchId;
    }

    @Override
    public void accept(LobbyRequestVisitor handler) {
        handler.visit(this);
    }

    public int getMatchId() {
        return matchId;
    }
}
