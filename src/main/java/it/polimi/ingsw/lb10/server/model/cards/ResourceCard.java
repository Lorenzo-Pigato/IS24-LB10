package it.polimi.ingsw.lb10.server.model.cards;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


public class ResourceCard extends PlaceableCard implements Serializable {
@JsonCreator
    public ResourceCard(@JsonProperty ("id") int id, @JsonProperty("color") Color colorCard, @JsonProperty("corners") ArrayList<Corner> corners, @JsonProperty("points") int points, @JsonProperty("resource") Resource resource, @JsonProperty("activationCost") HashMap<Resource, Integer> activationCost) {
        super(id, colorCard, corners, points, resource, Resource.NULL, activationCost);
        setNotFlippedState();
    }

    @Override
    public int getStateCardPoints() {
        return getCardState().getPoints();
    }

    @Override
    public Resource getStateCardGoldenBuffResource() {
        return Resource.NULL;
    }

    @Override
    public ArrayList<Corner> getStateCardCorners() {
        return getCardState().getCorners();
    }

    @Override
    public Resource getStateCardMiddleResource() {
        return getCardState().getMiddleResource();
    }

    @Override
    public HashMap<Resource, Integer> getStateCardActivationCost() {
        return new HashMap<>();
    }
}
