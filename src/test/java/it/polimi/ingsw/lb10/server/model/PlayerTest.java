package it.polimi.ingsw.lb10.server.model;

import it.polimi.ingsw.lb10.server.model.cards.PlaceableCard;
import it.polimi.ingsw.lb10.server.model.cards.ResourceCard;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;
import it.polimi.ingsw.lb10.server.model.cards.decks.ResourceDeck;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private static Player player;
    private static Map<Resource, Integer> onMapResource;
    private static ResourceDeck resourceDeck;

    private static MatchModel matchModel;

    @BeforeAll
    static void setUp() {
        player = new Player(123, "Tony");
        Matrix matrix = new Matrix();
        player.setMatrix(matrix);
        onMapResource = new HashMap<>();
        matchModel=new MatchModel(0,new ArrayList<>());

        resourceDeck = new ResourceDeck();
        resourceDeck.fillDeck();
    }

    @AfterEach
    void cleanUp(){
        onMapResource.clear();
        for( Resource resource: player.getOnMapResources().keySet() )
            player.getOnMapResources().put(resource,0);
    }

//    void setUpDeck(){
//        resourceDeck = new ResourceDeck();
//        resourceDeck.fillDeck();
//    }
//
//    /**
//     * This test doesn't pay attention at the validation of the card inside the matrix,
//     * the only focus it's about the set\delete of the resources.
//     */
//    @RepeatedTest(100)
//    void updateResources(){
//        setUpDeck();
//        int randomRow = (int) (Math.random()*80);
//        int randomColumn = (int) (Math.random()*80);
//        PlaceableCard card = resourceDeck.getCards().get((int)(Math.random()*40));
//
//            player.getMatrix().setCard(card,randomRow,randomColumn);
//            matchModel.setCardResourceOnPlayer(player,card);
//    }



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

     Position[] getPossiblePosition() {
        return new Position[]{Position.TOPLEFT, Position.TOPRIGHT, Position.BOTTOMRIGHT, Position.BOTTOMLEFT};
    }
}