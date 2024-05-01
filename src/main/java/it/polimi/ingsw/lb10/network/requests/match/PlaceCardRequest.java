package it.polimi.ingsw.lb10.network.requests.match;

import it.polimi.ingsw.lb10.network.requests.Request;
import it.polimi.ingsw.lb10.server.model.cards.PlaceableCard;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.LobbyRequestVisitor;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.MatchRequestVisitor;

import javax.smartcardio.Card;

public class PlaceCardRequest extends MatchRequest {
    private static final long serialVersionUID = 24L;

    private final PlaceableCard card;
    private final int matrixCardId;
    private final Position position;

    /**
     *
     * @param card card on hand that the player wants to place
     * @param position corner of the card on hand that the player wants to place
     * @param matrixCardId matrix card on which the player wants to place the specified card corner
     */
    public PlaceCardRequest(int matchId, PlaceableCard card, Position position, int matrixCardId) {
        super(matchId);
        this.card = card ; this.matrixCardId = matrixCardId; this.position = position;
    }

    @Override
    public void accept(MatchRequestVisitor handler) {
        handler.visit(this);
    }

    public PlaceableCard getCard(){return card;}
    public int getMatrixCardId(){return matrixCardId;}
    public Position getPosition(){return position;}
}
