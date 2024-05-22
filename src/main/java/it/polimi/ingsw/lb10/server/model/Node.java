package it.polimi.ingsw.lb10.server.model;


import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * It's the node of the Matrix! It may contain two cards, every node is a corner(free/occupied) of the card!
 */
public class Node implements Serializable {
    @Serial
    private static final long serialVersionUID = 36L;

    private ArrayList<Corner> corners;

    public Node() {
        corners = new ArrayList<>();
    }

    public void addCorner(Corner corner) {
        corners.add(corner);
    }

    public ArrayList<Corner> getCorners() {
        return corners;
    }

    public void deleteLastCorner() {
        corners.removeLast();
    }

    /**
     * In the first part of the algorithm, we insert the card in any case
     * @return false if there's only one card or at least the corner of the card under the one we put now it's available,
     * and @return true if it's not available
     */
    public boolean checkIsNotAvailable() {
        if (corners.size() == 1)
            return false;
        return !corners.getFirst().isAvailable();
    }
}
