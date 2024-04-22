package it.polimi.ingsw.lb10.server.model.cards;

import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;

import java.util.ArrayList;
import java.util.HashMap;

public class ResourceCard_v1 extends PlaceableCard{

    public ResourceCard_v1(int id, Color colorCard, ArrayList<Corner> corners, int points, Resource resource, Resource goldenBuffResource, HashMap<Resource, Integer> activationCost) {
        super(id, colorCard, corners, points, resource, goldenBuffResource, activationCost);
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
