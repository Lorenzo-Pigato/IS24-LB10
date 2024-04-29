package it.polimi.ingsw.lb10.network.response.match;

import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.StartingCard;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.CLIResponseHandler;

import java.util.HashMap;

public class PlaceStartingCardResponse extends Response {
    private StartingCard startingCard;
    private HashMap<Resource, Integer> resources;

    public PlaceStartingCardResponse(StartingCard startingCard, HashMap<Resource, Integer> resources) {
        this.startingCard = startingCard;
        this.resources = resources;
    }

    @Override
    public void accept(CLIResponseHandler handler) {
        handler.visit(this);
    }

    public StartingCard getStartingCard() {
        return startingCard;
    }

    public HashMap<Resource, Integer> getResources() {
        return resources;
    }
}
