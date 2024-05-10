package it.polimi.ingsw.lb10.server.model.cards.StartingCardState;

import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;

import java.util.ArrayList;

/**
 * we implemented a pattern strategy to change from flipped to not flipped state and vice versa,
 * we did this because, according to the state of the card,
 * we need the same methods but with different response/algorithm
 */
public interface StateStartingCard {
    ArrayList<Corner> getCorners();

    ArrayList<Resource> getMiddleResources();

}