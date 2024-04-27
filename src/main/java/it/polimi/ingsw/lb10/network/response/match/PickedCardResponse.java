package it.polimi.ingsw.lb10.network.response.match;

import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.model.Player;
import it.polimi.ingsw.lb10.server.model.cards.PlaceableCard;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.CLIResponseHandler;

import javax.smartcardio.Card;

public class PickedCardResponse extends Response {

    private PlaceableCard card;

    public PickedCardResponse(PlaceableCard card) {this.card = card;}

    @Override
    public void accept(CLIResponseHandler handler) {
        handler.visit(this);
    }
    public PlaceableCard getCard(){
        return card;
    }
}
