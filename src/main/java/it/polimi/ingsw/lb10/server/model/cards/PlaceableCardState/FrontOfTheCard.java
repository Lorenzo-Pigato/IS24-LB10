package it.polimi.ingsw.lb10.server.model.cards.PlaceableCardState;

import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.PlaceableCard;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class FrontOfTheCard implements StateOfTheCard, Serializable {
    final PlaceableCard placeableCard;

    public FrontOfTheCard(PlaceableCard placeableCard) {
        this.placeableCard = placeableCard;
    }

    @Override
    public ArrayList<Corner> getCorners() {
        return placeableCard.getCorners();
    }

    @Override
    public int getPoints() {
        return placeableCard.getPoints();
    }

    @Override
    public Resource getMiddleResource() {
        return Resource.NULL;
    }

    @Override
    public Resource goldenBuffResource() {
        return placeableCard.getGoldenBuffResource();
    }

    @Override
    public HashMap<Resource, Integer> activationCost() {
        return placeableCard.getActivationCost();
    }
}
