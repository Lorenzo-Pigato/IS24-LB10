package it.polimi.ingsw.lb10.server.model.cards;

import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;

import java.util.ArrayList;

/**
 *     same reasoning as StartingCard, we need to define the "color" of the card
 *     to define the flipped card, should the method be here or in the controller?--> Controller, but to finish the work we need the json.
 */

public class ResourceCard extends Card {
//la resource della card se ne sta occupando simo
    private CardState cardState;
    public ResourceCard(){}

    public ResourceCard(int id, boolean flipped, int points, ArrayList<Corner> corners, Resource resource){
        this.setId(id);
        this.setPoints(points);
        this.setFlipped(flipped);
        this.setCorners(corners);

            flippedCheck();
    }
    public void flippedCheck(){
        if(isFlipped())
            setFlippedState();
        else
            setNotFlippedState();
    }

    @Override
    public ArrayList<Corner> getStateCardCorners() {
        return getCardState().getCorners();
    }

    @Override
    public int getStateCardPoints(){
        return getCardState().getPoints();
    }

    @Override
    public void setFlippedState() {
        cardState = new FlippedCardState(getId());
    }

    public void setNotFlippedState() {
        cardState = new NotFlippedCardState(getCorners(),getPoints());
    }

    public CardState getCardState() {
        return cardState;
    }
}
