package it.polimi.ingsw.lb10.server.model.cards;

import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;

import java.util.ArrayList;
import java.util.HashMap;

public class GoldenCard extends Card {
    private CardState cardState;

    public GoldenCard(){}
    public GoldenCard(int id, boolean flipped, int points, ArrayList<Corner> corners, Color color,HashMap<Resource,Integer> activationCost){
        this.setId(id);
        this.setPoints(points);
        this.setFlipped(flipped);
        this.setCorners(corners);
        this.setColor(color);
        this.setActivationCost(activationCost);

            flippedCheck();
    }

    // --------> SETTER <--------

    @Override
    public void setFlippedState() {
        cardState = new FlippedCardState(getId(),getResources());
    }

    @Override
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
    @Override
    public HashMap<Resource, Integer> getStateCardActivationCost() {
        return getCardState().getActivationCost();
    }
}
