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

    Matrix matrix=new Matrix();
    private static Quest topRight;
    private static final QuestDeck questDeck=new QuestDeck();
    private static final ResourceDeck resourceDeck=new ResourceDeck();


    /**
     * test of card 102
     */
    @BeforeAll
    static void setUp(){
        questDeck.fillDeck();
        resourceDeck.fillDeck();
        topRight=questDeck.getCards().getLast();
    }

    @AfterEach
    void cleanUp(){
        matrix.deleteCard(41,41);
        matrix.deleteCard(43,41);
        matrix.deleteCard(40,42);
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
            if (card.getColorCard() != Color.GREEN)
                flag = true;
        }

        int nRandom = (int) (Math.random() * 40);
        PlaceableCard card = resourceDeck.getCards().get(nRandom);
        matrix.setCard(card, 40,42);
        //TAIL
        if (card.getColorCard() != Color.PURPLE)
            flag = true;

        int iRandom = (int) (Math.random() * 3);
        int[] rows = {41, 43, 40};
        int[] cols = {41, 41, 42};

        if (flag)
            assertFalse(topRight.isPattern(matrix, rows[iRandom], cols[iRandom]));
        else
            assertTrue(topRight.isPattern(matrix, rows[iRandom], cols[iRandom]));

    }

    @Test
    public void isPatternRightOne(){
        matrix.setCard(resourceDeck.getCards().get(15));
        matrix.setCard(resourceDeck.getCards().get(16),43,41);
        matrix.setCard(resourceDeck.getCards().get(27),40,42);

        assertTrue(topRight.isPattern(matrix,41,41));
    }

    @Test
    public void isPatternRightTwo(){
        matrix.setCard(resourceDeck.getCards().get(11));
        matrix.setCard(resourceDeck.getCards().get(12),43,41);
        matrix.setCard(resourceDeck.getCards().get(28),40,42);

        assertTrue(topRight.isPattern(matrix,43,41));
    }

    @Test
    public void isPatternRightThree(){
        matrix.setCard(resourceDeck.getCards().get(13));
        matrix.setCard(resourceDeck.getCards().get(14),43,41);
        matrix.setCard(resourceDeck.getCards().get(29),40,42);

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
        matrix.setCard(resourceDeck.getCards().get(2));
        matrix.setCard(resourceDeck.getCards().get(15),43,41);
        matrix.setCard(resourceDeck.getCards().get(3),40,42);

        assertFalse(topRight.isPattern(matrix,41,41));
        assertFalse(topRight.isPattern(matrix,43,41));
        assertFalse(topRight.isPattern(matrix,40,42));
    }

    @Test
    public void isPatternNotRightBot(){
        matrix.setCard(resourceDeck.getCards().get(15));
        matrix.setCard(resourceDeck.getCards().get(12),43,41);
        matrix.setCard(resourceDeck.getCards().get(2),40,42);

        assertFalse(topRight.isPattern(matrix,41,41));
        assertFalse(topRight.isPattern(matrix,43,41));
        assertFalse(topRight.isPattern(matrix,40,42));
    }
}