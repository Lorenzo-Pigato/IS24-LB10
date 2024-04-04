package it.polimi.ingsw.lb10.server.model.cards;

import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *     same reasoning as StartingCard, we need to define the "color" of the card
 *     to define the flipped card, should the method be here or in the controller?--> Controller, but to finish the work we need the json.
 */

public class ResourceCard extends Card {
    private CardState cardState;
    public ResourceCard(){}

    public ResourceCard(int id, boolean flipped, int points, ArrayList<Corner> corners, Resource resource, Color color,HashMap<Resource,Integer> activationCost, ArrayList<Resource> resources){
        this.setId(id);
        this.setPoints(points);
        this.setFlipped(flipped);
        this.setCorners(corners);
        this.setColor(color);

            flippedCheck();
    }

    // --------> SETTER <--------

    @Override
    public void setFlippedState() {
        cardState = new FlippedCardState(getId(),getResources());
    }

    public void setNotFlippedState() {
        cardState = new NotFlippedCardState(getCorners(),getPoints(),getActivationCost(),getResources());
    }

    // --------> GETTER <--------

    public CardState getCardState() {
        return cardState;
    }

    @Override
    public ArrayList<Corner> getStateCardCorners() {
        return getCardState().getCorners();
    }

    @Override
    public int getStateCardPoints(){
        return getCardState().getPoints();
    }

}
