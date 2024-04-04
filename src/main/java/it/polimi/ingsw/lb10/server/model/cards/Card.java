package it.polimi.ingsw.lb10.server.model.cards;

import com.fasterxml.jackson.annotation.JsonAlias;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;

import java.util.ArrayList;

public abstract class Card {

    private  int id;
    private boolean flipped;
    private int points;
    private ArrayList<Corner> corners = new ArrayList<Corner>();

    // --------> METHODS <--------
    public boolean flip(){
        return flipped=!flipped;
    }

    // --------> GETTER <--------
    @JsonAlias("flipped")
    public boolean isFlipped() {
        return flipped;
    }
    public int getPoints() {
        return points;
    }
    public int getId(){return id;}
    public ArrayList<Corner> getCorners(){
        return corners;
    }
    public ArrayList<Corner> getStateCardCorners(){
        return corners;
    }
    public int getStateCardPoints() {
        return points;
    }
    // --------> SETTER <--------
    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setCorners(ArrayList<Corner> corners) {
        this.corners = corners;
    }

    public void setFlippedState(){}
    public void setNotFlippedState(){}
}
