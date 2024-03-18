package it.polimi.ingsw.lb10.server.model;

import java.util.ArrayList;

public abstract class Card {
    private ArrayList<Corner> corners = new ArrayList<Corner>();
    private boolean flipped;
    private int points;


    public ArrayList<Corner> getCorner(){
        return corners;
    }
    public boolean isFlipped() {
        return flipped;
    }
    public int getPoints() {
        return points;
    }
}
