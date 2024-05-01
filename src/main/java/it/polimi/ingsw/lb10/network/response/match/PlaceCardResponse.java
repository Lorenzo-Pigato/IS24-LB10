package it.polimi.ingsw.lb10.network.response.match;

import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.model.cards.PlaceableCard;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.CLIResponseHandler;

public class PlaceCardResponse extends Response {

    private final PlaceableCard card;
    private final boolean status;
    private final int row;
    private final int col;

    public PlaceCardResponse(PlaceableCard card, boolean status, int row, int col) {this.card = card;this.status = status;this.row = row;this.col = col;}

    @Override
    public void accept(CLIResponseHandler handler) {
        handler.visit(this);
    }

    public PlaceableCard getCard(){return card;}
    public boolean getStatus(){return status;}
    public int getRow(){return row;}
    public int getCol(){return col;}
}
