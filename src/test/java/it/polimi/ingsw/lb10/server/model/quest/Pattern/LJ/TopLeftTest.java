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

class TopLeftTest {
    private static Matrix matrix;
    private static Quest topLeftQuest;
    private static final QuestDeck questDeck=new QuestDeck();
    private static final ResourceDeck resourceDeck=new ResourceDeck();

    /**
     * test of the card 100
     */
    @BeforeAll
    static void setUp(){
        matrix=new Matrix();
        questDeck.fillDeck();
        resourceDeck.fillDeck();
        topLeftQuest = questDeck.getCards().get(13);
    }

    @AfterEach
    void cleanUp(){
        matrix.deleteCard(41,41);
        matrix.deleteCard(43,41);
        matrix.deleteCard(40,40);
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
            if (card.getColorCard() != Color.PURPLE)
                flag = true;
        }

        int nRandom = (int) (Math.random() * 40);
        PlaceableCard card = resourceDeck.getCards().get(nRandom);
        matrix.setCard(card, 40,40);
        //TAIL
        if (card.getColorCard() != Color.BLUE)
            flag = true;

        int iRandom = (int) (Math.random() * 3);
        int[] rows = {41, 43, 40};
        int[] cols = {41, 41, 40};

        if (flag)
            assertFalse(topLeftQuest.isPattern(matrix, rows[iRandom], cols[iRandom]));
        else
            assertTrue(topLeftQuest.isPattern(matrix, rows[iRandom], cols[iRandom]));

    }
    @Test
    public void isPatternRightOne(){
        setUp();
        matrix.setCard(resourceDeck.getCards().get(21));
        matrix.setCard(resourceDeck.getCards().get(22),43,41);
        matrix.setCard(resourceDeck.getCards().get(34),40,40);

        assertTrue(topLeftQuest.isPattern(matrix,41,41));
    }
    @Test
    public void isPatternRightTwo(){
        matrix.setCard(resourceDeck.getCards().get(23));
        matrix.setCard(resourceDeck.getCards().get(24),43,41);
        matrix.setCard(resourceDeck.getCards().get(35),40,40);

        assertTrue(topLeftQuest.isPattern(matrix,43,41));
    }
    @Test
    public void isPatternRightThree(){
        matrix.setCard(resourceDeck.getCards().get(25));
        matrix.setCard(resourceDeck.getCards().get(26),43,41);
        matrix.setCard(resourceDeck.getCards().get(36),40,40);

        assertTrue(topLeftQuest.isPattern(matrix,40,40));
    }

    @Test
    public void isPatternNotRightTop(){
        matrix.setCard(resourceDeck.getCards().get(23));
        matrix.setCard(resourceDeck.getCards().get(22),43,41);
        matrix.setCard(resourceDeck.getCards().get(1),40,40);

        assertFalse(topLeftQuest.isPattern(matrix,41,41));
        assertFalse(topLeftQuest.isPattern(matrix,43,41));
        assertFalse(topLeftQuest.isPattern(matrix,40,40));
    }

    @Test
    public void isPatternNotRightMiddle(){
        matrix.setCard(resourceDeck.getCards().get(1));
        matrix.setCard(resourceDeck.getCards().get(23),43,41);
        matrix.setCard(resourceDeck.getCards().get(22),40,40);

        assertFalse(topLeftQuest.isPattern(matrix,41,41));
        assertFalse(topLeftQuest.isPattern(matrix,43,41));
        assertFalse(topLeftQuest.isPattern(matrix,40,40));
    }

    @Test
    public void isPatternNotRightBot(){
        matrix.setCard(resourceDeck.getCards().get(22));
        matrix.setCard(resourceDeck.getCards().get(1),43,41);
        matrix.setCard(resourceDeck.getCards().get(23),40,40);

        assertFalse(topLeftQuest.isPattern(matrix,41,41));
        assertFalse(topLeftQuest.isPattern(matrix,43,41));
        assertFalse(topLeftQuest.isPattern(matrix,40,40));
    }

}