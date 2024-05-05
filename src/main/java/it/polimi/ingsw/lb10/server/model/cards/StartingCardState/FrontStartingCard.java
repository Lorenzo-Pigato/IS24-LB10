package it.polimi.ingsw.lb10.server.model.cards.StartingCardState;

import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.StartingCard;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;

import java.io.Serializable;
import java.util.ArrayList;

public class FrontStartingCard implements StateStartingCard, Serializable {
    private final StartingCard startingCard;

    public FrontStartingCard(StartingCard startingCard) {
        this.startingCard = startingCard;
    }

    @Override
    public ArrayList<Corner> getCorners() {
        return startingCard.getCorners();
    }

    @Override
    public ArrayList<Resource> getMiddleResources() {
        return startingCard.getMiddleResources();
    }


}