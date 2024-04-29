package it.polimi.ingsw.lb10.server.model;


import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;

import java.util.ArrayList;

/**
 * It's the node of the Matrix! It may contain two cards, every node is a corner(free/occupied) of the card!
 */
public class Node {
    private ArrayList<Corner> corners;

    public Node(){
        corners=new ArrayList<>();
    }

    public void addCorner(Corner corner){
        corners.add(corner);
    }

    public ArrayList<Corner> getCorners() {
        return corners;
    }
    public void deleteLastCorner(){
        corners.removeLast();
    }

    /**
     * In the first part of the algorithm we insert the card in any case
     * @return false if there's only 1 card or both the cards have isAvailable-->true
     * and @return true if one of them it's not available
     */
    public boolean checkIsNotAvailable(){
        if(corners.size()==1)
            return false;
        if(!corners.get(0).isAvailable() || !corners.get(1).isAvailable() )
            return true;
        return false;
    }

}
