package it.polimi.ingsw.lb10.server.model.cards.PlaceableCardState;

import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;

import java.util.ArrayList;
import java.util.HashMap;

public interface StateOfTheCard {
    ArrayList<Corner> getCorners();

    int getPoints();

    Resource getMiddleResource();

    Resource goldenBuffResource();

    HashMap<Resource, Integer> activationCost();
}
