package it.polimi.ingsw.lb10.server.model.cards;

import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;
import it.polimi.ingsw.lb10.server.model.cards.decks.ResourceDeck;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ResourceCardTest {
    static ResourceDeck resourceDeck= new ResourceDeck();
    private static ResourceCard resourceCardV1;
    private static final int points=1;

    static ArrayList<Corner> corners = new ArrayList<>(Arrays.asList(
            new Corner(0,true,Position.TOPLEFT,Resource.ANIMAL,Color.GREEN),
            new Corner(0,true,Position.TOPLEFT,Resource.PLANT,Color.GREEN),
            new Corner(0,true,Position.TOPLEFT,Resource.FEATHER,Color.GREEN),
            new Corner(0,true,Position.TOPLEFT,Resource.EMPTY,Color.GREEN)
    ));

    static HashMap<Resource, Integer> activationCost;

    @BeforeAll
    static void setUp() {
        resourceCardV1=new ResourceCard(0,Color.GREEN,corners,points,Resource.NULL,new HashMap<>());

        resourceDeck.fillDeck();
    }

    /**
     * Methods for the points
     */
    @Test
    void getStateCardPointsNotFlipped() {
        resourceCardV1.setNotFlippedState();
        assertEquals(points,resourceCardV1.getStateCardPoints());
    }
    @Test
    void getStateCardPointsFlipped() {
        resourceCardV1.setFlippedState();
        assertEquals(0,resourceCardV1.getStateCardPoints());
    }

    /**
     * It doesn't depend on the fact that is flipped or not
     */
    @Test
    void getStateCardGoldenBuffResource() {
        assertEquals(Resource.NULL,resourceCardV1.getStateCardGoldenBuffResource());
    }

    /**
     * Corners Tests
     */
    @Test
    void getStateCardCornersFlipped() {
        resourceCardV1.setFlippedState();
        resourceCardV1.getStateCardCorners().forEach(corner -> {
            assertEquals(Resource.EMPTY, corner.getResource(), "When flipped, all corner resources should be EMPTY.");
        });
    }

    /**
     *The card is already not flipped
     */
    @Test
    public void testCornersNotFlipped() {
        assertEquals(Resource.ANIMAL, resourceCardV1.getCorners().get(0).getResource());
        assertEquals(Resource.PLANT, resourceCardV1.getCorners().get(1).getResource());
        assertEquals(Resource.FEATHER, resourceCardV1.getCorners().get(2).getResource());
        assertEquals(Resource.EMPTY, resourceCardV1.getCorners().get(3).getResource());
    }

    /**
     * Resources tests
     */
    @Test
    void getStateCardMiddleResourceNotFlipped() {
        resourceCardV1.setNotFlippedState();
        assertEquals( Resource.NULL, resourceCardV1.getStateCardMiddleResource());
    }

    /**
     * The Resource card doesn't have activation cost, the map that return it's empty
     */
    @Test
    void getStateCardActivationCost() {
        assertTrue(resourceCardV1.getStateCardActivationCost().isEmpty());
    }

    @RepeatedTest(40)
    void checkCorners(){
        PlaceableCard card= resourceDeck.drawCard();
            for(Corner corner : card.getStateCardCorners()){
                if(!corner.isAvailable())
                    assertEquals(Resource.EMPTY, corner.getResource());
            }
        }

        @Test
        void checkCardColor(){
        resourceDeck.fillDeck();
            for(PlaceableCard card : resourceDeck.getCards())
                for(Corner corner : card.getStateCardCorners())
                        assertEquals(card.getColorCard(), corner.getCardColor());
        }


}
