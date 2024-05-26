package it.polimi.ingsw.lb10.server.model.quest.Pattern.LJ;

import it.polimi.ingsw.lb10.server.model.Matrix;
import it.polimi.ingsw.lb10.server.model.cards.Color;
import it.polimi.ingsw.lb10.server.model.cards.PlaceableCard;
import it.polimi.ingsw.lb10.server.model.cards.decks.QuestDeck;
import it.polimi.ingsw.lb10.server.model.cards.decks.ResourceDeck;
import it.polimi.ingsw.lb10.server.model.quest.Quest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TopRightTest {

    final Matrix matrix=new Matrix();
    private static Quest topRight;
    private static final ResourceDeck resourceDeck=new ResourceDeck();


    /**
     * test of card 99
     */
    @BeforeAll
    static void setUp(){
        resourceDeck.fillDeck();
        topRight = new TopRight(99, 3, Color.BLUE, Color.RED);
    }

    @AfterEach
    void cleanUp(){
        matrix.deleteCard(41,41);
        matrix.deleteCard(43,41);
        matrix.deleteCard(40,42);
    }


    @Test
    public void isPatternRightOne(){
        matrix.setCard(resourceDeck.getCards().get(37));
        matrix.setCard(resourceDeck.getCards().get(35),43,41);
        matrix.setCard(resourceDeck.getCards().get(7),40,42);
        assertTrue(topRight.isPattern(matrix,41,41));
    }

    @Test
    public void isPatternRightThree(){
        matrix.setCard(resourceDeck.getCards().get(31));
        matrix.setCard(resourceDeck.getCards().get(39),43,41);
        matrix.setCard(resourceDeck.getCards().get(5),40,42);
        assertTrue(topRight.isPattern(matrix,40,42));
    }

    @Test
    public void isPatternNotRightTop(){
        matrix.setCard(resourceDeck.getCards().get(15));
        matrix.setCard(resourceDeck.getCards().get(16),43,41);
        matrix.setCard(resourceDeck.getCards().get(3),40,42);

        assertFalse(topRight.isPattern(matrix,41,41));
        assertFalse(topRight.isPattern(matrix,43,41));
        assertFalse(topRight.isPattern(matrix,40,42));
    }

    @Test
    public void isPatternNotRightMiddle(){
        matrix.setCard(resourceDeck.getCards().get(15));
        matrix.setCard(resourceDeck.getCards().get(23),43,41);
        matrix.setCard(resourceDeck.getCards().get(3),40,42);

        assertFalse(topRight.isPattern(matrix,41,41));
        assertFalse(topRight.isPattern(matrix,43,41));
        assertFalse(topRight.isPattern(matrix,40,42));
    }

    @Test
    public void isPatternNotRightBoth(){
        matrix.setCard(resourceDeck.getCards().get(15));
        matrix.setCard(resourceDeck.getCards().get(12),43,41);
        matrix.setCard(resourceDeck.getCards().get(21),40,42);

        assertFalse(topRight.isPattern(matrix,41,41));
        assertFalse(topRight.isPattern(matrix,43,41));
        assertFalse(topRight.isPattern(matrix,40,42));
    }
    @RepeatedTest(1000)
    void checkPattern(){
        boolean flag=false;
        setUp();

        for(int i=0;i<2;i++){
            int nRandom = (int) (Math.random() * 40);
            PlaceableCard card = resourceDeck.getCards().get(nRandom);
            matrix.setCard(card, 41+(i*2), 41);
            //BODY
            if (card.getColorCard() != Color.BLUE)
                flag = true;
        }

        int nRandom = (int) (Math.random() * 40);
        PlaceableCard card = resourceDeck.getCards().get(nRandom);
        matrix.setCard(card, 40,42);
        //TAIL
        if (card.getColorCard() != Color.RED)
            flag = true;

        int iRandom = (int) (Math.random() * 3);
        int[] rows = {41, 43, 40};
        int[] cols = {41, 41, 42};

        if (flag)
            assertFalse(topRight.isPattern(matrix, rows[iRandom], cols[iRandom]));
        else
            assertTrue(topRight.isPattern(matrix, rows[iRandom], cols[iRandom]));

    }
}