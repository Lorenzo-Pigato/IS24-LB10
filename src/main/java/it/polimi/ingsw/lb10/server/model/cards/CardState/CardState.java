package it.polimi.ingsw.lb10.server.model.cards.CardState;

import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;

import java.util.ArrayList;
import java.util.HashMap;

public interface CardState {
    Resource getGoldenBuffResource();
    ArrayList<Resource> getCardResources();
    HashMap<Resource, Integer> getActivationCost();
    int getPoints();
    ArrayList<Corner> getCorners();
}
