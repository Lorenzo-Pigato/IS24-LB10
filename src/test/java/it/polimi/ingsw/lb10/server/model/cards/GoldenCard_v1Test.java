package it.polimi.ingsw.lb10.server.model.cards;

import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class goldenCardV1Test {

    private static GoldenCard_v1 goldenCardV1;
    private static int points=2;
    static Resource middleResource=Resource.PLANT;
    static HashMap<Resource, Integer> activationCost= new HashMap<>();

    static ArrayList<Corner> corners = new ArrayList<>(Arrays.asList(
            new Corner(0,true,false, Position.TOPLEFT, Resource.ANIMAL,Color.GREEN),
            new Corner(0,true,false,Position.TOPLEFT,Resource.PLANT,Color.GREEN),
            new Corner(0,true,false,Position.TOPLEFT,Resource.FEATHER,Color.GREEN),
            new Corner(0,true,false,Position.TOPLEFT,Resource.EMPTY,Color.GREEN)
    ));

    @BeforeAll
    static void setUp() {
        activationCost.put(Resource.PLANT, 3 );
        goldenCardV1=new GoldenCard_v1(0,Color.GREEN,corners,points,middleResource,Resource.PATTERN,activationCost);
    }

    /**
     * Methods for the points
     */
    @Test
    void getStateCardPointsNotFlipped(){
        goldenCardV1.setNotFlippedState();
        assertEquals(points,goldenCardV1.getStateCardPoints());
    }
    @Test
    void getStateCardPointsFlipped() {
        goldenCardV1.setFlippedState();
        assertEquals(0,goldenCardV1.getStateCardPoints());
    }

    //it's to complete with the other methods...


    @Test
    void getStateCardGoldenBuffResource() {
        assertEquals(Resource.NULL,goldenCardV1.getStateCardGoldenBuffResource());
    }

}