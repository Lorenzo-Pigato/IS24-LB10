package it.polimi.ingsw.lb10.server.model.cards.StartingCardState;

import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;

import java.util.ArrayList;

public interface StateStartingCard {
    ArrayList<Corner> getCorners();
    ArrayList<Corner> getFlippedCardCorners();
    ArrayList<Resource> getMiddleResources();

}
