package it.polimi.ingsw.lb10.server.controller;

import it.polimi.ingsw.lb10.server.model.Matrix;
import it.polimi.ingsw.lb10.server.model.Node;
import it.polimi.ingsw.lb10.server.model.Player;
import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.Color;
import it.polimi.ingsw.lb10.server.model.cards.GoldenCard_v1;
import it.polimi.ingsw.lb10.server.model.cards.PlaceableCard;
import it.polimi.ingsw.lb10.server.model.cards.ResourceCard_v1;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MatchControllerTest {
    private static ResourceCard_v1 firstCard;
    private static ResourceCard_v1 secondCard;
    private static ResourceCard_v1 thirdCard;
    private static GoldenCard_v1 golden1;


    private static int points = 0;

    private static Player player;

    private static Matrix matrix;

    private static MatchController controller;



    static ArrayList<Corner> corners = new ArrayList<>(Arrays.asList(
            new Corner(0,true,false, Position.TOPLEFT, Resource.ANIMAL, Color.GREEN),
            new Corner(0,true,false,Position.TOPRIGHT,Resource.PLANT,Color.GREEN),
            new Corner(0,false,false,Position.BOTTOMLEFT,Resource.EMPTY,Color.GREEN),
            new Corner(0,true,false,Position.BOTTOMRIGHT,Resource.PLANT,Color.GREEN)
    ));

    static ArrayList<Corner> corners2 = new ArrayList<>(Arrays.asList(
            new Corner(1,false,false, Position.TOPLEFT, Resource.ANIMAL, Color.RED),
            new Corner(1,true,false,Position.TOPRIGHT,Resource.PLANT,Color.RED),
            new Corner(1,true,false,Position.BOTTOMLEFT,Resource.EMPTY,Color.RED),
            new Corner(1,true,false,Position.BOTTOMRIGHT,Resource.PLANT,Color.RED)
    ));

    static ArrayList<Corner> corners3 = new ArrayList<>(Arrays.asList(
            new Corner(2,false,false, Position.TOPLEFT, Resource.ANIMAL, Color.BLUE),
            new Corner(2,true,false,Position.TOPRIGHT,Resource.PLANT,Color.BLUE),
            new Corner(2,true,false,Position.BOTTOMLEFT,Resource.EMPTY,Color.BLUE),
            new Corner(2,true,false,Position.BOTTOMRIGHT,Resource.PLANT,Color.BLUE)
    ));

    static Resource middleResource=Resource.PLANT;
    static HashMap<Resource, Integer> activationCost;

    static HashMap<Resource,Integer> onMapResources;

    static ArrayList<PlaceableCard> hand;

    static ArrayList<Node> nodesVisited = new ArrayList<>();

    static Position[] positions = new Position[]{Position.TOPLEFT, Position.TOPRIGHT, Position.BOTTOMRIGHT, Position.BOTTOMLEFT};
    @BeforeEach
    void setUp() {
        firstCard = new ResourceCard_v1(0,Color.GREEN,corners,points,middleResource,Resource.NULL,new HashMap<>());
        secondCard = new ResourceCard_v1(1,Color.RED,corners2,points,middleResource,Resource.NULL,new HashMap<>());
        thirdCard = new ResourceCard_v1(2,Color.BLUE,corners3,points,middleResource,Resource.NULL,new HashMap<>());
        golden1 = new GoldenCard_v1(5,Color.RED, corners3, 4, middleResource, Resource.PLANT, new HashMap<>());
        player = new Player("Simo");
        matrix = new Matrix();
        player.setMatrix(matrix);
        controller = new MatchController();
        controller.addPlayer(player);
        activationCost = new HashMap<>();
        onMapResources = new HashMap<>();
        hand= new ArrayList<>();
        hand.add(firstCard);

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

        assertFalse(controller.insertCard(player, thirdCard, 42,42));

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






}