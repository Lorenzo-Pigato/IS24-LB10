package it.polimi.ingsw.lb10.server.model;

import it.polimi.ingsw.lb10.server.model.cards.PlaceableCard;
import it.polimi.ingsw.lb10.server.model.cards.ResourceCard;
import it.polimi.ingsw.lb10.server.model.cards.decks.ResourceDeck;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private static Player player;
    private static Map<Resource, Integer> onMapResource;
    private static ResourceDeck resourceDeck;

    @BeforeAll
    static void setUp() {
        player = new Player(123, "Tony");
        Matrix matrix = new Matrix();
        player.setMatrix(matrix);
        onMapResource = new HashMap<>();

        resourceDeck = new ResourceDeck();
        resourceDeck.fillDeck();
    }

    @AfterEach
    void cleanUp(){
        onMapResource.clear();
        for( Resource resource: player.getOnMapResources().keySet() )
            player.getOnMapResources().put(resource,0);
    }

    @Test
    public void addOnMapResources(){
        onMapResource=new HashMap<>();

        player.addOnMapResources(Resource.MUSHROOM);
        onMapResource.put(Resource.MUSHROOM, 1);
        assertEquals(onMapResource, player.getOnMapResources());

        player.addOnMapResources(Resource.MUSHROOM);
        onMapResource.put(Resource.MUSHROOM, 2);
        assertEquals(onMapResource, player.getOnMapResources());

    }

    @Test
    public void deleteOnMapResources(){
        onMapResource=new HashMap<>();

        player.addOnMapResources(Resource.MUSHROOM);
        player.addOnMapResources(Resource.MUSHROOM);

        player.deleteOnMapResources(Resource.MUSHROOM);

        onMapResource.put(Resource.MUSHROOM, 1);
        assertEquals(onMapResource, player.getOnMapResources());

        /////                                               /////

        player.deleteOnMapResources(Resource.MUSHROOM);

        onMapResource.put(Resource.MUSHROOM, 0);
        assertEquals(onMapResource, player.getOnMapResources());
    }

    @Test
    public void addQuestPoints(){
        player.setQuestPoints(10);
        player.setPoints(27);

        player.addPoints(player.getQuestPoints());
        assertEquals(29,player.getPoints());
    }

    @Test
    public void addCardOnHand(){

        player.addCardOnHand(resourceDeck.getCards().getFirst());
        player.addCardOnHand(resourceDeck.getCards().get(1));

        assertEquals(2, player.getHand().size());
    }

    @Test
    public void removeCardOnHand(){
        player.removeCardOnHand(resourceDeck.getCards().getFirst());
    }

    @Test
    public void getResourceQuantity(){
        assertEquals(0, player.getResourceQuantity(Resource.PLANT));
        assertEquals(0, player.getResourceQuantity(Resource.ANIMAL));
    }

}