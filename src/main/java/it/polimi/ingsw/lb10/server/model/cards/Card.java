package it.polimi.ingsw.lb10.server.model.cards;

import com.fasterxml.jackson.annotation.JsonAlias;
import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Card {
    private  int id;
    private boolean flipped;
    private int points;
    private Color color;
    private ArrayList<Resource> resources= new ArrayList<>();
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
        return null;
    }

    public ArrayList<Resource> getResources() {
        return resources;
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

   public void setResources(ArrayList<Resource> resources){
        this.resources=resources;
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
        return null;
    }

    public int getStateCardPoints() {
        return 0;
    }

    public HashMap<Resource, Integer> getStateCardActivationCost(){return null;}

    public ArrayList<Resource> getStateCardResources(){return null;}


    public void setFlippedState(){}

    public void setNotFlippedState(){}



}
