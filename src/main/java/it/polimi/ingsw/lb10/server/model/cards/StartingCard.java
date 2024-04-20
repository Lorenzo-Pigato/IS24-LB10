package it.polimi.ingsw.lb10.server.model.cards;

import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.CardState.CardState;
import it.polimi.ingsw.lb10.server.model.cards.CardState.FlippedStartingCardState;
import it.polimi.ingsw.lb10.server.model.cards.CardState.NotFlippedStartingCardState;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;


import java.util.ArrayList;
import java.util.HashMap;

/** When we define this card we want to know the middle's resource and the free corners
     *  We have to define a method that from the json get the car
     */
public class StartingCard extends Card {

    private CardState cardState;
    public StartingCard(int id, int points, ArrayList<Corner> corners,ArrayList<Corner> flippedCardCorners, Color color, ArrayList<Resource> resources){
        this.setId(id);
        this.setPoints(points);
        this.setCorners(corners);
        this.setColor(color);
        this.setResources(resources);
        this.setFlippedCardCorners(flippedCardCorners);
        setPoints(0);

        setNotFlippedState();
    }

    // --------> SETTER <--------

    @Override
    public void setFlippedState() {
        cardState = new FlippedStartingCardState(this);
    }

    public void setNotFlippedState() {
        cardState = new NotFlippedStartingCardState(this);
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
