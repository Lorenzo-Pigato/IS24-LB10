package it.polimi.ingsw.lb10.network.response.match;

import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.model.cards.PlaceableCard;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.ResponseVisitor;
import java.util.List;

public class DeckUpdateResponse extends Response {
    private List<PlaceableCard> pickables;

    public DeckUpdateResponse(List<PlaceableCard> pickables) {
        this.pickables = pickables;
    }


    @Override
    public void accept(ResponseVisitor handler) {
        handler.visit(this);
    }

    public List<PlaceableCard> getPickables() {
        return pickables;
    }

    public void setPickables(List<PlaceableCard> pickables) {
        this.pickables = pickables;
    }
}
