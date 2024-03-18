package it.polimi.ingsw.lb10.server.model;

import java.util.ArrayList;

/*
    When we define this card we want to know the middle's resource and the free corners

    We have to define a method that from the json get the card
 */
public class StartingCard extends Card{
    private final ArrayList<Resource> resources=new ArrayList<>();

    public StartingCard(){ }

    @Override
    public int getPoints(){
        return 0;
    }

    public ArrayList<Resource> getResources(){return resources;}

}
