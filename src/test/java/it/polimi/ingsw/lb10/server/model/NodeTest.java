package it.polimi.ingsw.lb10.server.model;

import it.polimi.ingsw.lb10.server.model.cards.Color;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {
    static ArrayList<Corner> corners = new ArrayList<>(Arrays.asList(
            new Corner(0,true, Position.TOPLEFT, Resource.MUSHROOM, Color.GREEN),
            new Corner(0,true,Position.TOPRIGHT,Resource.PLANT,Color.GREEN),
            new Corner(0,false,Position.BOTTOMLEFT,Resource.EMPTY,Color.GREEN),
            new Corner(0,true,Position.BOTTOMRIGHT,Resource.PLANT,Color.GREEN)
    ));

    static Node node = new Node();

    @Test
    void checkIsNotAvailableTest(){
        node.getCorners().add(corners.getFirst());
        assertFalse(node.checkIsNotAvailable());
        node.getCorners().remove(corners.getFirst());
        node.getCorners().addAll(corners);
        assertFalse(node.checkIsNotAvailable());

    }



}