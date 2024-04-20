package it.polimi.ingsw.lb10.client.controller;

import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.Color;
import it.polimi.ingsw.lb10.server.model.cards.GoldenCard;
import it.polimi.ingsw.lb10.server.model.cards.ResourceCard;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;
import it.polimi.ingsw.lb10.server.model.cards.corners.CornerAvailable;
import it.polimi.ingsw.lb10.server.model.cards.corners.CornerNotAvailable;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Assertions;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ResourceCardTest {
    static ResourceCard resourceCard;

    static GoldenCard goldenCard;
    static ArrayList<Corner> corners;
    static ArrayList<Resource> middleResource;
    static HashMap<Resource, Integer> activationCost;


    /**
     * Testing if the values of corners are correct despite the state of the card
     */
    @BeforeAll
    public static void initialize() {
        middleResource = new ArrayList<>(List.of(Resource.ANIMAL));
        //activationCost = new HashMap<>();
        corners = new ArrayList<>(Arrays.asList(new CornerAvailable(Position.TOPLEFT, Resource.ANIMAL), new CornerAvailable(Position.TOPRIGHT, Resource.PLANT), new CornerAvailable(Position.BOTTOMLEFT, Resource.FEATHER), new CornerNotAvailable(Position.BOTTOMRIGHT, Resource.NULL)));
        resourceCard = new ResourceCard(0, 1, corners, Color.BLUE, middleResource);

    }

    @BeforeAll
    public static void initializeGold(){
        middleResource = new ArrayList<>(List.of(Resource.ANIMAL));
        activationCost = new HashMap<>();
        activationCost.put(Resource.ANIMAL,1);
        corners = new ArrayList<>(Arrays.asList(new CornerAvailable(Position.TOPLEFT, Resource.ANIMAL), new CornerAvailable(Position.TOPRIGHT, Resource.PLANT), new CornerAvailable(Position.BOTTOMLEFT, Resource.FEATHER), new CornerNotAvailable(Position.BOTTOMRIGHT, Resource.NULL)));
        goldenCard = new GoldenCard(0, 1, corners, Color.BLUE, activationCost,middleResource);

    }


    @Test
    public void testCornersFlipped() {
        resourceCard.setFlippedState();
        resourceCard.getStateCardCorners().forEach(corner -> {
            assertEquals(Resource.NULL, corner.getResource(), "When flipped, all corner resources should be NULL.");
        });
    }

    @Test
    public void testCornersNotFlipped() {
        //The card is already not flipped
        assertEquals(Resource.ANIMAL, resourceCard.getCorners().get(0).getResource());
        assertEquals(Resource.PLANT, resourceCard.getCorners().get(1).getResource());
        assertEquals(Resource.FEATHER, resourceCard.getCorners().get(2).getResource());
        assertEquals(Resource.NULL, resourceCard.getCorners().get(3).getResource());
    }
    @Test
    public void testValuesFlipped(){
        assertTrue(resourceCard.getStateCardResources().isEmpty());
        assertTrue(resourceCard.getActivationCost().isEmpty());


    }
    @Test
    public void testValueNotFlipped(){
        resourceCard.setFlippedState();
        assertEquals(middleResource, resourceCard.getStateCardResources(), "Middle Resource should be present when the card is flipped");
        assertTrue(resourceCard.getActivationCost().isEmpty());
    }
    //Testing activation cost for golden card here//
    @Test
    public void goldenTestValue(){
        assertEquals(activationCost, goldenCard.getStateCardActivationCost(), "The activation cost should be present when it's not flipped");
        goldenCard.setFlippedState();
        assertTrue(goldenCard.getStateCardActivationCost().isEmpty());


    }

}



