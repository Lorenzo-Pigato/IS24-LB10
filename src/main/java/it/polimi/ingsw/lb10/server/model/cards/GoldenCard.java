package it.polimi.ingsw.lb10.server.model.cards;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class GoldenCard extends PlaceableCard implements Serializable {
@JsonCreator
    public GoldenCard(@JsonProperty("id") int id, @JsonProperty("color") Color colorCard, @JsonProperty("corners") ArrayList<Corner> corners, @JsonProperty("points") int points, @JsonProperty("resource") Resource resource, @JsonProperty("goldenBuffResource") Resource goldenBuffResource, @JsonProperty("activationCost") HashMap<Resource, Integer> activationCost) {
        super(id, colorCard, corners, points, resource, goldenBuffResource, activationCost);
        setNotFlippedState();
    }


    @Override
    public int getStateCardPoints() {
        return getCardState().getPoints();
    }

    @Override
    public Resource getStateCardGoldenBuffResource() {
        return getCardState().goldenBuffResource();
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
        return getCardState().activationCost();
    }

}
