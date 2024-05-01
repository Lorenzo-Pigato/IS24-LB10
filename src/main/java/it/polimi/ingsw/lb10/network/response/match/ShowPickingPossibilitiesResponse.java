package it.polimi.ingsw.lb10.network.response.match;

import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.model.cards.GoldenCard;
import it.polimi.ingsw.lb10.server.model.cards.PlaceableCard;
import it.polimi.ingsw.lb10.server.model.cards.ResourceCard;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.CLIResponseHandler;
import java.util.ArrayList;

public class ShowPickingPossibilitiesResponse extends Response {
    private final GoldenCard goldenCard;
    private final ResourceCard resourceCard;
    private final ArrayList<GoldenCard> goldenUncovered;
    private final ArrayList<ResourceCard> resourceUncovered;

    public ShowPickingPossibilitiesResponse(GoldenCard goldenCard, ResourceCard resourceCard, ArrayList<GoldenCard> goldenUncovered, ArrayList<ResourceCard> resourceUncovered) {
        this.goldenCard = goldenCard;
        this.resourceCard = resourceCard;
        this.goldenUncovered = goldenUncovered;
        this.resourceUncovered = resourceUncovered;
    }

    @Override
    public void accept(CLIResponseHandler handler) {
        handler.visit(this);
    }

    public PlaceableCard getGoldenCard() {return this.goldenCard;}
    public PlaceableCard getResourceCard() {return this.resourceCard;}
    public ArrayList<GoldenCard> getGoldenUncovered() {return this.goldenUncovered;}
    public ArrayList<ResourceCard> getResourceUncovered() {return this.resourceUncovered;}
}
