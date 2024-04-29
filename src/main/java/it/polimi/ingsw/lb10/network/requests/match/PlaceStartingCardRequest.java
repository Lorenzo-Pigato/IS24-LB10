package it.polimi.ingsw.lb10.network.requests.match;

import it.polimi.ingsw.lb10.server.visitors.requestDispatch.LobbyRequestVisitor;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.MatchRequestVisitor;

public class PlaceStartingCardRequest extends MatchRequest {

    public PlaceStartingCardRequest(int matchId) {
        super(matchId);
    }

    @Override
    public void accept(MatchRequestVisitor visitor) {
        visitor.visit(this);
    }
}
