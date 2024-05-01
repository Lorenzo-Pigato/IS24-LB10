package it.polimi.ingsw.lb10.network.response.match;

import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.model.Player;
import it.polimi.ingsw.lb10.server.model.cards.PlaceableCard;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.CLIResponseHandler;

import javax.smartcardio.Card;

public class PickedCardResponse extends Response {
    private static final long serialVersionUID = 6L;
    private PlaceableCard card;
    private final boolean status;
    private final String message;

    public PickedCardResponse(PlaceableCard card, boolean status, String message) {this.card = card;this.status = status;
        this.message = message;
    }

    @Override
    public void accept(CLIResponseHandler handler) {
        handler.visit(this);
    }
    public PlaceableCard getCard(){
        return card;
    }
    public boolean getStatus(){return status;}
    public String getMessage(){return message;}
}
