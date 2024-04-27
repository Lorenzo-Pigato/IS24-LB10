package it.polimi.ingsw.lb10.server.model.cards.StartingCardState;

import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.StartingCard;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;

import java.util.ArrayList;

public class BackStartingCard implements StateStartingCard{
    private final StartingCard startingCard;
    public BackStartingCard(StartingCard startingCard){
        this.startingCard=startingCard;
    }

    @Override
    public ArrayList<Corner> getCorners() {
        return startingCard.getFlippedCardCorners();
    }

    @Override
    public ArrayList<Resource> getMiddleResources() {
        return new ArrayList<>();
    }
}