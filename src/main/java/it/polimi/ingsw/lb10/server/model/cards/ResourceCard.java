package it.polimi.ingsw.lb10.server.model.cards;

import com.fasterxml.jackson.annotation.JsonCreator;
import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.Card;
import it.polimi.ingsw.lb10.server.model.cards.Corner;

import java.util.ArrayList;

/**
 *     same reasoning as StartingCard, we need to define the "color" of the card
 *     to define the flipped card, should the method be here or in the controller?--> Controller, but to finish the work we need the json.
 */

public class ResourceCard extends Card {
    private Resource resource;
    public ResourceCard(){}

public ResourceCard(int id, boolean flipped, int points,Resource resource){
    this.setId(id);
    this.setPoints(points);
    this.setFlipped(flipped);
    this.resource= resource;
}


    public ResourceCard(int id, boolean flipped, int points, ArrayList<Corner> corners, Resource resource){
        this.setId(id);
        this.setPoints(points);
        this.setFlipped(flipped);
        this.setCorners(corners);
        this.resource= resource;
    }

//    with this method we know the "color" of the card, naturally it can't be with the number from 0 to 7
    public Resource getResource(){return resource;}

    public void setResource(Resource resource) {
        this.resource = resource;
    }

}
