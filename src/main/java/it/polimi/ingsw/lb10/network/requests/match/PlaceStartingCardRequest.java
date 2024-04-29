package it.polimi.ingsw.lb10.network.requests.match;

import it.polimi.ingsw.lb10.server.model.cards.StartingCard;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.LobbyRequestVisitor;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.MatchRequestVisitor;

public class PlaceStartingCardRequest extends MatchRequest {
    private StartingCard startingCard;
    public PlaceStartingCardRequest(int matchId, StartingCard startingCard) {
        super(matchId);
        this.startingCard = startingCard;
    }

    @Override
    public void accept(MatchRequestVisitor visitor) {
        visitor.visit(this);
    }

    public StartingCard getStartingCard() {
        return startingCard;
    }
}
