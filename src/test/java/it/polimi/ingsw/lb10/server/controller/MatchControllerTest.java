package it.polimi.ingsw.lb10.server.controller;

import it.polimi.ingsw.lb10.server.model.Matrix;
import it.polimi.ingsw.lb10.server.model.Node;
import it.polimi.ingsw.lb10.server.model.Player;
import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.Color;
import it.polimi.ingsw.lb10.server.model.cards.PlaceableCard;
import it.polimi.ingsw.lb10.server.model.cards.ResourceCard_v1;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;
import org.junit.jupiter.api.BeforeAll;
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

    private static int points = 1;

    private static Player player;

    private static Matrix matrix;

    private static MatchController controller;



    static ArrayList<Corner> corners = new ArrayList<>(Arrays.asList(
            new Corner(0,true,false, Position.TOPLEFT, Resource.ANIMAL, Color.GREEN),
            new Corner(0,true,false,Position.TOPRIGHT,Resource.PLANT,Color.GREEN),
            new Corner(0,true,false,Position.BOTTOMLEFT,Resource.EMPTY,Color.GREEN),
            new Corner(0,true,false,Position.BOTTOMRIGHT,Resource.PLANT,Color.GREEN)
    ));

    static ArrayList<Corner> corners2 = new ArrayList<>(Arrays.asList(
            new Corner(1,true,false, Position.TOPLEFT, Resource.ANIMAL, Color.RED),
            new Corner(1,true,false,Position.TOPRIGHT,Resource.PLANT,Color.RED),
            new Corner(1,true,false,Position.BOTTOMLEFT,Resource.EMPTY,Color.RED),
            new Corner(1,true,false,Position.BOTTOMRIGHT,Resource.PLANT,Color.RED)
    ));

    static ArrayList<Corner> corners3 = new ArrayList<>(Arrays.asList(
            new Corner(2,true,false, Position.TOPLEFT, Resource.ANIMAL, Color.BLUE),
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
    @BeforeAll
    static void setUp() {
        firstCard = new ResourceCard_v1(0,Color.GREEN,corners,points,middleResource,Resource.NULL,new HashMap<>());
        secondCard = new ResourceCard_v1(1,Color.RED,corners2,points,middleResource,Resource.NULL,new HashMap<>());
        thirdCard = new ResourceCard_v1(1,Color.BLUE,corners3,points,middleResource,Resource.NULL,new HashMap<>());
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

    @Test
    void testInsertion(){
        //Simulating the insertion of one card by player
        boolean result = controller.insertCard(player, firstCard, 41,41);
        //assertEquals(player.getMatrix(),matrix.getNode(41,41).getCorners().get(0));
    }
    @Test
    void setCardTest(){

        matrix.setCard(firstCard);
        assertEquals(firstCard.getStateCardCorners().get(0), matrix.getNode(41, 41).getCorners().get(0), "Top left corner should match");
        matrix.deleteCard(41,41);
        assertTrue(matrix.getNode(41,41).getCorners().isEmpty());

        //Using debugger u can see that all corners are added in position 41 41
        //Also GetNode and GetCorners are working properly

    }

    @Test
    void verificationSettingTest(){
        matrix.setCard(firstCard, 41,41);

        boolean result = controller.verificationSetting(player, 41,41);
        assertTrue(result);

    }

    /**
     * Testing all the single cases of verificationSetting
     */

    @Test
    void NodesExceptions(){
        int row = 42;
        int column = 42;
        matrix.setCard(firstCard, 41,41);
        matrix.setCard(secondCard,43,42);
        matrix.setCard(thirdCard,42,42);

        assertFalse(controller.verificationSetting(player, 42,42));

    }



}