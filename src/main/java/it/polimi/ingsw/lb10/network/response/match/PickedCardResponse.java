package it.polimi.ingsw.lb10.network.response.match;

import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.model.Matrix;
import it.polimi.ingsw.lb10.server.model.cards.PlaceableCard;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.ResponseVisitor;

import java.io.Serial;

public class PickedCardResponse extends Response {
    @Serial
    private static final long serialVersionUID = 6L;
    private final PlaceableCard card;
    private final boolean status;
    private final String message;
    private final Matrix matrix;
    private final boolean runOutOfCards;

    public PickedCardResponse(PlaceableCard card, boolean status, String message, Matrix matrix, boolean runOutOfCards){
        this.card = card;
        this.status = status;
        this.message = message;
        this.matrix = matrix;
        this.runOutOfCards = false;
    }

    @Override
    public void accept(ResponseVisitor handler) {
        handler.visit(this);
    }

    public PlaceableCard getCard() {
        return card;
    }

    public boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public boolean hasRunOutOfCards() {return runOutOfCards;}

}
