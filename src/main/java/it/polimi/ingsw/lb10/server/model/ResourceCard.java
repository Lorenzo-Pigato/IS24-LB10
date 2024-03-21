package it.polimi.ingsw.lb10.server.model;

import java.util.ArrayList;

/*
    same reasoning as StartingCard, we need to define the "color" of the card
    to define the flipped card, should the method be here or in the controller?--> Controller, but to finish the work we need the json.
 */
public class ResourceCard extends Card{

    public ResourceCard(int id, boolean flipped, int points, ArrayList<Corner> corners){
        this.setId(id);
        this.setPoints(points);
        this.setFlipped(flipped);
        this.setCorners(corners);
    }

//    public void setResource(Resource resource) {k
//        this.resource = resource;
//    }
//
//
//
//    //with this method we know the "color" of the card, naturally it can't be with the number from 5 to 7
//    public Resource getResource(){return resource;}



}
