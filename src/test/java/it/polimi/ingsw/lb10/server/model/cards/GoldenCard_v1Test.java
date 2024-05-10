package it.polimi.ingsw.lb10.server.model.cards;

import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;
import it.polimi.ingsw.lb10.server.model.cards.decks.GoldenDeck;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class GoldenCardTest {
    static GoldenDeck goldenDeck= new GoldenDeck();
    private static GoldenCard goldenCardV1;
    private static int points=2;
    static Resource middleResource=Resource.PLANT;
    static HashMap<Resource, Integer> activationCost= new HashMap<>();

    static ArrayList<Corner> corners = new ArrayList<>(Arrays.asList(
            new Corner(0,true, Position.TOPLEFT, Resource.ANIMAL,Color.GREEN),
            new Corner(0,true,Position.TOPLEFT,Resource.PLANT,Color.GREEN),
            new Corner(0,true,Position.TOPLEFT,Resource.FEATHER,Color.GREEN),
            new Corner(0,true,Position.TOPLEFT,Resource.EMPTY,Color.GREEN)
    ));

    @BeforeAll
    static void setUp() {
        activationCost.put(Resource.PLANT, 3 );
        goldenCardV1=new GoldenCard(0,Color.GREEN,corners,points,middleResource,Resource.PATTERN,activationCost);
        goldenDeck.fillDeck();
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

    @Test
    void getStateCardGoldenBuffResource() {
        assertEquals(Resource.NULL,goldenCardV1.getStateCardGoldenBuffResource());
    }

    @RepeatedTest(40)
    void checkCorners(){
        PlaceableCard card= goldenDeck.drawCard();
        for(Corner corner : card.getStateCardCorners()){
            if(!corner.isAvailable())
                assertEquals(Resource.EMPTY, corner.getResource());
        }
        assertNotEquals(0,card.getPoints());
    }

}