package it.polimi.ingsw.lb10.server.model.cards.oldVersion;

import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.CardState.CardState;
import it.polimi.ingsw.lb10.server.model.cards.CardState.FlippedCardState;
import it.polimi.ingsw.lb10.server.model.cards.CardState.NotFlippedCardState;
import it.polimi.ingsw.lb10.server.model.cards.Color;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;
import it.polimi.ingsw.lb10.server.model.cards.oldVersion.Card;

import java.util.ArrayList;
import java.util.HashMap;

public class GoldenCard extends Card {
    private CardState cardState;

    public GoldenCard(int id, int points, ArrayList<Corner> corners, Color color, HashMap<Resource,Integer> activationCost, ArrayList<Resource> resources, Resource goldenBuffResource){
        this.setId(id);
        this.setPoints(points);
        this.setCorners(corners);
        this.setColor(color);
        this.setActivationCost(activationCost);
        this.setResources(resources);
        this.setGoldenBuffResource(goldenBuffResource);

        setNotFlippedState();
    }

    @Override
    public void swapState() {
        if (getCardState() instanceof FlippedCardState)
            setNotFlippedState();
        else
            setFlippedState();
    }

    // --------> SETTER <--------

    @Override
    public void setFlippedState() {
        cardState = new FlippedCardState(this);
    }
    @Override
    public void setNotFlippedState() {
        cardState = new NotFlippedCardState(this);
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

    @Override
    public ArrayList<Resource> getStateCardResources(){return getCardState().getCardResources();}
    @Override
    public Resource getStateCardGoldenBuffResource(){return getCardState().getGoldenBuffResource();}
}
