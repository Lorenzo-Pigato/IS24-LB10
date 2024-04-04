package it.polimi.ingsw.lb10.server.model.cards;

import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;

import java.util.ArrayList;
import java.util.HashMap;

public class NotFlippedCardState implements CardState{
    private final ArrayList<Corner> corners;
    private HashMap<Resource, Integer> activationCost=new HashMap<>();
    private final int points;

    public NotFlippedCardState(ArrayList<Corner> corners,int points,HashMap<Resource, Integer> activationCost){
        this.corners=corners;
        this.points=points;
        // I don't know ho to work with the case that's empty, I think that'll be an error
        this.activationCost=activationCost;
    }

    // --------> SETTER <--------



    // --------> GETTER <--------

    @Override
    public HashMap<Resource, Integer> getActivationCost() {
        return activationCost;
    }

    @Override
    public int getPoints() {
        return points;
    }

    @Override
    public ArrayList<Corner> getCorners() {
        return corners;
    }
}
