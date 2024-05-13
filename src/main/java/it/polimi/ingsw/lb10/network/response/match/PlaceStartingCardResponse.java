package it.polimi.ingsw.lb10.network.response.match;

import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.model.Matrix;
import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.StartingCard;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.CLIResponseHandler;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.ResponseVisitor;

import java.io.Serial;
import java.util.HashMap;

public class PlaceStartingCardResponse extends Response {
    @Serial
    private static final long serialVersionUID = 7L;
    private final StartingCard startingCard;
    private final HashMap<Resource, Integer> resources;

    public PlaceStartingCardResponse(StartingCard startingCard, HashMap<Resource, Integer> resources) {
        this.startingCard = startingCard;
        this.resources = resources;
    }

    @Override
    public void accept(ResponseVisitor handler) {
        handler.visit(this);
    }

    public StartingCard getStartingCard() {
        return startingCard;
    }

    public HashMap<Resource, Integer> getResources() {
        return resources;
    }
}
