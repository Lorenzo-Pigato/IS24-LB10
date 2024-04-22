package it.polimi.ingsw.lb10.server.model.cards;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.PlaceableCardState.BackOfTheCard;
import it.polimi.ingsw.lb10.server.model.cards.PlaceableCardState.FrontOfTheCard;
import it.polimi.ingsw.lb10.server.model.cards.PlaceableCardState.StateOfTheCard;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class PlaceableCard extends BaseCard {
    @JsonProperty("points")
    private int points;
    @JsonProperty("resource")
    private Resource resource;
    @JsonProperty("goldenBuffResource")
    private Resource goldenBuffResource;
    @JsonProperty("activationCost")
    private HashMap<Resource,Integer> activationCost;
    @JsonIgnore
    private StateOfTheCard stateOfTheCard;

@JsonCreator
    public PlaceableCard(int id,  Color colorCard,  ArrayList<Corner> corners,  int points, Resource resource, Resource goldenBuffResource, HashMap<Resource,Integer> activationCost) {
        super(id, colorCard, corners);
        this.points = points;
        this.resource = resource;
        this.goldenBuffResource = goldenBuffResource;
        this.activationCost = activationCost;

    }

    public void swapState(){
        if (getCardState() instanceof FrontOfTheCard)
            setNotFlippedState();
        else
            setFlippedState();
    }

    // --------> SETTER <--------

    public void setFlippedState() {
        stateOfTheCard = new BackOfTheCard(this);
    }

    public void setNotFlippedState() {
        stateOfTheCard = new FrontOfTheCard(this);
    }

    // --------> METHODS FOR THE STATE  <--------

    public abstract int getStateCardPoints();
    public abstract Resource getStateCardGoldenBuffResource();
    public abstract ArrayList<Corner> getStateCardCorners();
    public abstract Resource getStateCardMiddleResource();
    public abstract HashMap<Resource, Integer> getStateCardActivationCost();

    // --------> GETTER <--------
    public int getPoints() {
        return points;
    }
    public Resource getResource() {
        return resource;
    }
    public Resource getGoldenBuffResource() {
        return goldenBuffResource;
    }
    public HashMap<Resource, Integer> getActivationCost() {
        return activationCost;
    }
    public StateOfTheCard getCardState() {
        return stateOfTheCard;
    }
    // --------> SETTER <--------
    public void setPoints(int points) {
        this.points = points;
    }
    public void setResource(Resource resource) {
        this.resource = resource;
    }
    public void setGoldenBuffResource(Resource goldenBuffResource) {
        this.goldenBuffResource = goldenBuffResource;
    }
    public void setActivationCost(HashMap<Resource, Integer> activationCost) {
        this.activationCost = activationCost;
    }
}
