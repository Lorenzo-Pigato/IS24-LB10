package it.polimi.ingsw.lb10.network.requests.preMatch;

import it.polimi.ingsw.lb10.server.visitors.requestDispatch.LobbyRequestVisitor;

public class LobbyToMatchRequest extends LobbyRequest {

    private final int matchId;

    public LobbyToMatchRequest(int hashCode, int matchId) {
        super(hashCode);
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
