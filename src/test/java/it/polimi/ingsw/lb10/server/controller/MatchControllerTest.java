package it.polimi.ingsw.lb10.server.controller;

import it.polimi.ingsw.lb10.server.model.Matrix;
import it.polimi.ingsw.lb10.server.model.Node;
import it.polimi.ingsw.lb10.server.model.Player;
import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.*;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;
import it.polimi.ingsw.lb10.server.model.cards.decks.GoldenDeck;
import it.polimi.ingsw.lb10.server.model.cards.decks.ResourceDeck;
import it.polimi.ingsw.lb10.server.model.cards.decks.StartingDeck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

class MatchControllerTest {
    private static ResourceCard firstCard;
    private static ResourceCard secondCard;
    private static ResourceCard thirdCard;
    private static GoldenCard golden1;
    private static StartingDeck starting;


    private static int points = 0;

    private static Player player;

    private static Matrix matrix;

    private static MatchController controller;



    static ArrayList<Corner> corners = new ArrayList<>(Arrays.asList(
            new Corner(0,true, Position.TOPLEFT, Resource.ANIMAL, Color.GREEN),
            new Corner(0,true,Position.TOPRIGHT,Resource.PLANT,Color.GREEN),
            new Corner(0,false,Position.BOTTOMLEFT,Resource.EMPTY,Color.GREEN),
            new Corner(0,true,Position.BOTTOMRIGHT,Resource.PLANT,Color.GREEN)
    ));

    static ArrayList<Corner> corners2 = new ArrayList<>(Arrays.asList(
            new Corner(1,false, Position.TOPLEFT, Resource.ANIMAL, Color.RED),
            new Corner(1,true,Position.TOPRIGHT,Resource.PLANT,Color.RED),
            new Corner(1,true,Position.BOTTOMLEFT,Resource.EMPTY,Color.RED),
            new Corner(1,true,Position.BOTTOMRIGHT,Resource.PLANT,Color.RED)
    ));

    static ArrayList<Corner> corners3 = new ArrayList<>(Arrays.asList(
            new Corner(2,false, Position.TOPLEFT, Resource.ANIMAL, Color.BLUE),
            new Corner(2,true,Position.TOPRIGHT,Resource.PLANT,Color.BLUE),
            new Corner(2,true,Position.BOTTOMLEFT,Resource.EMPTY,Color.BLUE),
            new Corner(2,true,Position.BOTTOMRIGHT,Resource.PLANT,Color.BLUE)
    ));

    static Resource goldenBuffResource =Resource.PLANT;
    static HashMap<Resource, Integer> activationCost;

    static HashMap<Resource,Integer> onMapResources;

    static ArrayList<PlaceableCard> hand;

    static ArrayList<Node> nodesVisited = new ArrayList<>();

    static ResourceDeck resourceDeck = new ResourceDeck();

    static GoldenDeck goldenDeck = new GoldenDeck();
    static StartingDeck startingDeck= new StartingDeck();
    static Position[] positions = new Position[]{Position.TOPLEFT, Position.TOPRIGHT, Position.BOTTOMRIGHT, Position.BOTTOMLEFT};
    @BeforeEach
    void setUp() {
        firstCard = new ResourceCard(0,Color.GREEN,corners,points,Resource.NULL,new HashMap<>());
        secondCard = new ResourceCard(1,Color.RED,corners2,points,Resource.NULL,new HashMap<>());
        thirdCard = new ResourceCard(2,Color.BLUE,corners3,points,Resource.NULL,new HashMap<>());
        golden1 = new GoldenCard(5,Color.RED, corners3, 4, goldenBuffResource, Resource.PLANT, new HashMap<>());
        player = new Player(0,"Simo");
        matrix = new Matrix();
        player.setMatrix(matrix);
        controller = new MatchController(4);
        controller.addPlayer(player);
        activationCost = new HashMap<>();
        onMapResources = new HashMap<>();
        onMapResources.put(Resource.MUSHROOM,5);
        for( int i=0; i<onMapResources.get(Resource.MUSHROOM); i++){
            player.addOnMapResources(Resource.MUSHROOM);
        }
        hand= new ArrayList<>();
        hand.add(firstCard);
        resourceDeck.fillDeck();
        goldenDeck.fillDeck();
        startingDeck.fillDeck();



    }

//Test for resource cards
    @Test
    void setCardTest(){
        matrix.setCard(firstCard, 41,41);
        matrix.setCard(secondCard,43,41);
        assertFalse(controller.insertCard(player, thirdCard, 42,42));

    }


    /**
     * Testing all the single cases of verificationSetting,
     */

    @Test
    void testingPlacement1(){
        matrix.setCard(firstCard, 41,41);
        matrix.setCard(secondCard,43,42);

        assertFalse(controller.insertCard(player, thirdCard, 42,42));

    }
    @Test
    void testingPlacement2(){

        matrix.setCard(firstCard, 41,41);
        matrix.setCard(secondCard,43,43);

        assertFalse(controller.insertCard(player, thirdCard, 41,42));

    }

    /**
     * I've also tested the case where one of the corners is not available changing some values in the declaration
     */
    @Test
    void testingPlacement3(){
        matrix.setCard(firstCard, 41,41);
        matrix.setCard(secondCard,41,43);

        assertFalse(controller.insertCard(player, thirdCard, 42,42));

    }

    /**
     * Testing the placement of a GoldenCard
     */
    @Test
    void goldenPlacement(){
        matrix.setCard(firstCard, 42,43);
        matrix.setCard(secondCard,44,43);

        assertTrue(controller.insertCard(player, golden1,41,42));
        assertEquals(4,player.getPoints());


    }

    //Adding test with Deck created from Json

    @Test
    void setFromDeckTest(){
        matrix.setCard(resourceDeck.getCards().get(20), 41,41);
        matrix.setCard(resourceDeck.getCards().get(3), 43,41);
        ResourceCard resourceCard = resourceDeck.getCards().get(15);
        resourceCard.setFlippedState();
        assertTrue(controller.insertCard(player, resourceCard, 42,42));

    }

    @Test
    void setPointsTest1(){
        matrix.setCard(resourceDeck.getCards().get(20), 41,41);
        GoldenCard goldenCard = goldenDeck.getCards().get(2);
        assertTrue(controller.insertCard(player, goldenCard, 42,42));
        assertEquals(5,player.getPoints());

    }

    @Test
    void setPointTest2(){
        //Flipped goldenCard has activationCost NULL and 0 points
        matrix.setCard(resourceDeck.getCards().get(20), 41,41);
        GoldenCard goldenCard = goldenDeck.getCards().get(2);
        goldenCard.setFlippedState();
        assertTrue(controller.insertCard(player, goldenCard, 42,42));
        assertEquals(0,player.getPoints());

    }

    /**
     * Using every time the same card to assure the presence of the upleft and downright corner, this shouldn't be a problem for the id-corner
     * because only the down corner is checked
     * in the worst case, when the players are two, each of them can place 40 cards.
     */

    @Test
    void limitPlacement() {
        int row = 42;
        int column = 42;

        matrix.setCard(resourceDeck.getCards().get(20), 41, 41);
        for (int i = 0; i < 40; i++) {

            assertTrue(controller.insertCard(player, resourceDeck.getCards().get(20), row, column));
            row++;
            column++;

        }

    }
    @Test
    void startingMatch(){
        matrix.setCard(startingDeck.getCards().getFirst());



    }






}