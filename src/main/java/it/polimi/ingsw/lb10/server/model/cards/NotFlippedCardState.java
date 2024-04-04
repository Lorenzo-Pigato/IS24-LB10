package it.polimi.ingsw.lb10.server.model.cards;

import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;

import java.util.ArrayList;

public class NotFlippedCardState implements CardState{
    private ArrayList<Corner> corners;
    private int points;

    public NotFlippedCardState(ArrayList<Corner> corners,int points){
        this.corners=corners;
        this.points=points;
    }

    @Override
    public ArrayList<Corner> getCorners() {
        return corners;
    }

    public void setCorners(ArrayList<Corner> corners) {
        this.corners = corners;
    }

    @Override
    public int getPoints() {
        return points;
    }
}
