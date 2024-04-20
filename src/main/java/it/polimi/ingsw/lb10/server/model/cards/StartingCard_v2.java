package it.polimi.ingsw.lb10.server.model.cards;

import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.PlaceableCardState.FrontOfTheCard;
import it.polimi.ingsw.lb10.server.model.cards.StartingCardState.BackStartingCard;
import it.polimi.ingsw.lb10.server.model.cards.StartingCardState.FrontStartingCard;
import it.polimi.ingsw.lb10.server.model.cards.StartingCardState.StateStartingCard;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;

import java.util.ArrayList;

public class StartingCard_v2 extends BaseCard {

    private ArrayList<Corner> flippedCardCorners;
    private ArrayList<Resource> middleResources;
    private StateStartingCard stateStartingCard;
    public StartingCard_v2(int id, Color colorCard, ArrayList<Corner> corners,ArrayList<Corner> flippedCardCorners,ArrayList<Resource> middleResources,StateStartingCard stateStartingCard) {
        super(id, colorCard, corners);
        this.flippedCardCorners=flippedCardCorners;
        this.middleResources=middleResources;
        this.stateStartingCard=stateStartingCard;
    }

    public void swapState(){
        if (getStateStartingCard() instanceof FrontOfTheCard)
            setNotFlippedState();
        else
            setFlippedState();
    }

    // --------> SETTER <--------

    public void setFlippedState() {
        stateStartingCard = new FrontStartingCard(this);
    }

    public void setNotFlippedState() {
        stateStartingCard = new BackStartingCard(this);
    }
    // --------> GETTER <--------

    public ArrayList<Corner> getFlippedCardCorners() {
        return flippedCardCorners;
    }
    public ArrayList<Resource> getMiddleResources() {
        return middleResources;
    }
    public StateStartingCard getStateStartingCard() {
        return stateStartingCard;
    }
    // --------> SETTER <--------

    public void setFlippedCardCorners(ArrayList<Corner> flippedCardCorners) {
        this.flippedCardCorners = flippedCardCorners;
    }
    public void setMiddleResources(ArrayList<Resource> middleResources) {
        this.middleResources = middleResources;
    }
}
