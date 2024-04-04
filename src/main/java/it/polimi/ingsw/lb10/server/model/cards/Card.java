package it.polimi.ingsw.lb10.server.model.cards;

import com.fasterxml.jackson.annotation.JsonAlias;
import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.corners.Color;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Card {
    private  int id;
    private boolean flipped;
    private int points;
    private Color color;
    private final ArrayList<Resource> resources= new ArrayList<>();
    private ArrayList<Corner> corners = new ArrayList<>();
    private HashMap<Resource,Integer> activationCost= new HashMap<>();

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

    public Color getColor() {
        return color;
    }

    public HashMap<Resource, Integer> getActivationCost() {
        return activationCost;
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

    public void setColor(Color color) {
        this.color = color;
    }

    public void setActivationCost(HashMap<Resource, Integer> activationCost) {
        this.activationCost = activationCost;
    }

    // --------> METHODS <--------

    public boolean flip(){
        return flipped=!flipped;
    }
    public void flippedCheck(){
        if(isFlipped())
            setFlippedState();
        else
            setNotFlippedState();
    }

    // --------> METHODS FOR THE STATE  <--------

    public ArrayList<Corner> getStateCardCorners(){
        return corners;
    }

    public int getStateCardPoints() {
        return points;
    }

    public HashMap<Resource, Integer> getStateActivationCost(){return activationCost;}

    public void setFlippedState(){}

    public void setNotFlippedState(){}


}
