package it.polimi.ingsw.lb10.server.model.cards;

import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.Card;
import java.util.HashMap;

public class GoldenCard extends Card {
    private Resource resource;
    private HashMap<Resource,Integer> activationCost= new HashMap<>();
    
    /**
     * @param resource
     * Setter & Getter of GoldenCard's resource
     */
    public void setResource(Resource resource) {
        this.resource = resource;
    }
    public Resource getResource(){return resource;}


    /**
     * @param activationCost
     * it's to complete, I don't know how json works with the HashMap
     */
    public void setActivationCost(HashMap<Resource, Integer> activationCost) {
        this.activationCost = activationCost;
    }
}
