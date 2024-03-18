package it.polimi.ingsw.lb10.server.model;
/*
    same reasoning as StartingCard, we need to define the "color" of the card
    to define the flipped card, should the method be here or in the controller?
 */
public class ResourceCard extends Card{

    private Resource resource;

    //with this method we know the "color" of the card, naturally it can't be with the number from 5 to 7
    public Resource getResource(){return resource;}



}
