package it.polimi.ingsw.lb10.server.model;


import it.polimi.ingsw.lb10.server.model.cards.Corner;

import java.util.ArrayList;

/**
 * It's the node of the Matrix! It may contain 2 cards, every node is a corner(free/occupied) of the card!
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
        corners.remove(corners.size()-1);
    }
    public boolean checkAvailability(){
        if(corners.size()==1)
            return false;
        if(!corners.get(0).isAvailable() || !corners.get(1).isAvailable() )
            return true;
        System.out.println(">>> E R R O R <<< This method isn't call if the Node is empty!!!!!");
        return true;
    }
}
