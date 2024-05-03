package it.polimi.ingsw.lb10.network.response.match;

import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.PlaceableCard;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.CLIResponseHandler;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.ResponseVisitor;

import java.util.HashMap;

public class PlaceCardResponse extends Response {

    private final PlaceableCard card;
    private final boolean status;
    private final int row;
    private final int col;
    private HashMap<Resource, Integer> playerResources;

    public PlaceCardResponse(PlaceableCard card, boolean status, int row, int col, HashMap<Resource, Integer> playerResources) {this.card = card;this.status = status;this.row = row;this.col = col;
        this.playerResources = playerResources;
    }

    @Override
    public void accept(ResponseVisitor handler) {
        handler.visit(this);
    }

    public PlaceableCard getCard(){return card;}
    public boolean getStatus(){return status;}
    public int getRow(){return row;}
    public int getCol(){return col;}
    public HashMap<Resource, Integer> getPlayerResources(){return playerResources;}
}
