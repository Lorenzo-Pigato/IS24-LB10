package it.polimi.ingsw.lb10.server.model.cards.oldVersion;

import com.fasterxml.jackson.annotation.JsonAlias;
import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.Color;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Card {
    private  int id;
    private int points;
    private Color color;
    private Resource goldenBuffResource;
    private ArrayList<Resource> resources= new ArrayList<>();
    private ArrayList<Corner> flippedCardCorners= new ArrayList<>();
    private ArrayList<Corner> corners = new ArrayList<>();
    private HashMap<Resource,Integer> activationCost= new HashMap<>();

    // --------> GETTER <--------

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
    public Resource getGoldenBuffResource() {
        return goldenBuffResource;
    }
    public ArrayList<Resource> getResources() {
        return resources;
    }
    public ArrayList<Corner> getFlippedCardCorners() {
        return flippedCardCorners;
    }
    public HashMap<Resource, Integer> getActivationCost() {
        return activationCost;
    }

    // --------> SETTER <--------

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
    public void setGoldenBuffResource(Resource goldenBuffResource) {
        this.goldenBuffResource = goldenBuffResource;
    }
    public void setFlippedCardCorners(ArrayList<Corner> flippedCardCorners) {
        this.flippedCardCorners = flippedCardCorners;
    }
    public void setResources(ArrayList<Resource> resources){
        this.resources=resources;
   }
    public void setActivationCost(HashMap<Resource, Integer> activationCost) {
        this.activationCost = activationCost;
    }

// --------> METHODS FOR THE STATE  <--------

    public abstract int getStateCardPoints();
    public abstract Resource getStateCardGoldenBuffResource();
    public abstract ArrayList<Corner> getStateCardCorners();
    public abstract ArrayList<Resource> getStateCardResources();
    public abstract HashMap<Resource, Integer> getStateCardActivationCost();
    public abstract void swapState();
    public abstract void setFlippedState();
    public abstract void setNotFlippedState();
}
