package it.polimi.ingsw.lb10.server.model.cards;


import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;

import java.util.ArrayList;
import java.util.HashMap;

public interface CardState {
    ArrayList<Corner> getCorners();
    int getPoints();
    HashMap<Resource,Integer> getActivationCost();

}





// nuova classe che implemento private  pleasebleCard ...;

//metodi comuni
///getPoints, getActivationResource,getCorner