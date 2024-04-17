package it.polimi.ingsw.lb10.server.model.cards.CardState;

import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.CardState.CardState;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;

import java.util.ArrayList;
import java.util.HashMap;

public class NotFlippedCardState implements CardState {
    private final ArrayList<Corner> corners;
    private HashMap<Resource, Integer> activationCost=new HashMap<>();
    private final int points;
    private final ArrayList<Resource> cardResources;

    public NotFlippedCardState(ArrayList<Corner> corners,int points,HashMap<Resource, Integer> activationCost, ArrayList<Resource> cardResources){
        this.corners=corners;
        this.points=points;
        this.activationCost=activationCost;
        this.cardResources=cardResources;
    }

    // --------> GETTER <--------

    @Override
    public ArrayList<Corner> getCorners() {
        return corners;
    }

    @Override
    public int getPoints() {
        return points;
    }

    @Override
    public HashMap<Resource, Integer> getActivationCost() {
        return activationCost;
    }

    @Override
    public ArrayList<Resource> getCardResources() {
        return cardResources;
    }
}
