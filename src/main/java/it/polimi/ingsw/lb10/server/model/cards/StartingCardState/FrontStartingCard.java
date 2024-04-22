package it.polimi.ingsw.lb10.server.model.cards.StartingCardState;

import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.StartingCard_v2;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;

import java.util.ArrayList;

public class FrontStartingCard implements StateStartingCard{
    private StartingCard_v2 startingCard;
    public FrontStartingCard(StartingCard_v2 startingCard){
        this.startingCard=startingCard;
    }
    @Override
    public ArrayList<Corner> getCorners() {
        return startingCard.getCorners();
    }
    @Override
    public ArrayList<Corner> getFlippedCardCorners() {
        return new ArrayList<>();
    }
    @Override
    public ArrayList<Resource> getMiddleResources() {
        return startingCard.getMiddleResources();
    }


}