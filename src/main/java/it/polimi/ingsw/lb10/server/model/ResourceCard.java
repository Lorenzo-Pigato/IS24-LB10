package it.polimi.ingsw.lb10.server.model;
/*
    same reasoning as StartingCard, we need to define the "color" of the card
    to define the flipped card, should the method be here or in the controller?--> Controller, but to finish the work we need the json.
 */
public class ResourceCard extends Card{

    public ResourceCard(int id, int flagged, int points){
        this.id=id;
        this.points=points;
    }

//    public void setResource(Resource resource) {
//        this.resource = resource;
//    }
//
//    private Resource resource;
//
//    //with this method we know the "color" of the card, naturally it can't be with the number from 5 to 7
//    public Resource getResource(){return resource;}



}
