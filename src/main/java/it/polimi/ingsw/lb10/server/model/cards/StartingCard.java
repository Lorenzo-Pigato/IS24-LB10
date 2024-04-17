package it.polimi.ingsw.lb10.server.model.cards;

import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.CardState.CardState;
import it.polimi.ingsw.lb10.server.model.cards.CardState.FlippedCardState;
import it.polimi.ingsw.lb10.server.model.cards.CardState.NotFlippedCardState;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;


import java.util.ArrayList;
import java.util.HashMap;

/** When we define this card we want to know the middle's resource and the free corners
     *  We have to define a method that from the json get the car
     */
public class StartingCard extends Card {
    private CardState cardState;
    public StartingCard(int id, boolean flipped, int points, ArrayList<Corner> corners, Resource resource, Color color, HashMap<Resource,Integer> activationCost, ArrayList<Resource> resources){
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
    public ArrayList<Resource> getStateCardResources(){
        return getCardState().getCardResources();
    }
}
