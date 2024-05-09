package it.polimi.ingsw.lb10.server.model;

import it.polimi.ingsw.lb10.server.model.cards.Color;
import it.polimi.ingsw.lb10.server.model.cards.ResourceCard;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;


class MatrixTest {
    private Matrix matrix;
    static ResourceCard firstCard;
    static int points = 0;


    static ArrayList<Corner> corners = new ArrayList<>(Arrays.asList(
            new Corner(0,true, Position.TOPLEFT, Resource.MUSHROOM, Color.GREEN),
            new Corner(0,true,Position.TOPRIGHT,Resource.PLANT,Color.GREEN),
            new Corner(0,false,Position.BOTTOMLEFT,Resource.EMPTY,Color.GREEN),
            new Corner(0,true,Position.BOTTOMRIGHT,Resource.PLANT,Color.GREEN)
    ));


 @BeforeEach
 void setUp(){
     matrix = new Matrix();
     matrix.startingMatrix();
     firstCard = new ResourceCard(0,Color.GREEN,corners,points,Resource.NULL,new HashMap<>());

 }

 @Test
  void setCardTest(){
     matrix.setCard(firstCard,41,41);
     assertEquals(1,matrix.getNode(41,41).getCorners().size());

 }

 @Test
 void deleteCardTest(){
     matrix.setCard(firstCard,41,41);
     assertEquals(1,matrix.getNode(41,41).getCorners().size());
     matrix.deleteCard(41,41);
     assertEquals(0,matrix.getNode(41,41).getCorners().size());
    }

}