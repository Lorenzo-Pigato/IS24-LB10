package it.polimi.ingsw.lb10.server.model.cards;

import it.polimi.ingsw.lb10.server.model.Resource;


import java.util.ArrayList;

/** When we define this card we want to know the middle's resource and the free corners
 *  We have to define a method that from the json get the car
 */
public class StartingCard extends Card {
    private ArrayList<Resource> resources=new ArrayList<>();
    /**
     * @return the ArrayList of the resources of the starting card
     */
    
    public ArrayList<Resource> getResources(){return resources;}
    public void setResource(ArrayList<Resource> resources) {
        this.resources = resources;
    }

    @Override
    public int getPoints(){
        return 0;
    }

}
