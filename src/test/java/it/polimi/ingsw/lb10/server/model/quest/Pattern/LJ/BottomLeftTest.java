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

class BottomLeftTest {
    final Matrix matrix=new Matrix();
    private static Quest bottomLeftQuest;
    private static final QuestDeck questDeck=new QuestDeck();
    private static final ResourceDeck resourceDeck=new ResourceDeck();

    @BeforeAll
    static void setUp(){
        questDeck.fillDeck();
        resourceDeck.fillDeck();
        bottomLeftQuest = questDeck.getCards().get(12);
    }

    @AfterEach
    void cleanUp(){
        matrix.deleteCard(41,41);
        matrix.deleteCard(43,41);
        matrix.deleteCard(44,40);
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
        matrix.setCard(card, 44,40);
        //TAIL
        if (card.getColorCard() != Color.RED)
            flag = true;

        int iRandom = (int) (Math.random() * 3);
        int[] rows = {41, 43, 44};
        int[] cols = {41, 41, 40};

        if (flag)
            assertFalse(bottomLeftQuest.isPattern(matrix, rows[iRandom], cols[iRandom]));
        else
            assertTrue(bottomLeftQuest.isPattern(matrix, rows[iRandom], cols[iRandom]));

    }
    @Test
    public void isPatternRightOne(){
        matrix.setCard(resourceDeck.getCards().get(31));
        matrix.setCard(resourceDeck.getCards().get(32),43,41);
        matrix.setCard(resourceDeck.getCards().get(1),44,40);

       assertTrue(bottomLeftQuest.isPattern(matrix,41,41));
    }

    @Test
    public void isPatternRightTwo(){
        matrix.setCard(resourceDeck.getCards().get(33));
        matrix.setCard(resourceDeck.getCards().get(34),43,41);
        matrix.setCard(resourceDeck.getCards().get(2),44,40);

        assertTrue(bottomLeftQuest.isPattern(matrix,43,41));
    }

    @Test
    public void isPatternRightThree(){
        matrix.setCard(resourceDeck.getCards().get(35));
        matrix.setCard(resourceDeck.getCards().get(36),43,41);
        matrix.setCard(resourceDeck.getCards().get(3),44,40);

        assertTrue(bottomLeftQuest.isPattern(matrix,44,40));
    }

    @Test
    public void isPatternNotRightTop(){
        matrix.setCard(resourceDeck.getCards().get(1));
        matrix.setCard(resourceDeck.getCards().get(32),43,41);
        matrix.setCard(resourceDeck.getCards().get(31),44,40);

        assertFalse(bottomLeftQuest.isPattern(matrix,41,41));
        assertFalse(bottomLeftQuest.isPattern(matrix,43,41));
        assertFalse(bottomLeftQuest.isPattern(matrix,44,41));
    }
    @Test
    public void isPatternNotRightMiddle(){
        matrix.setCard(resourceDeck.getCards().get(32));
        matrix.setCard(resourceDeck.getCards().get(1),43,41);
        matrix.setCard(resourceDeck.getCards().get(31),44,40);

        assertFalse(bottomLeftQuest.isPattern(matrix,41,41));
        assertFalse(bottomLeftQuest.isPattern(matrix,43,41));
        assertFalse(bottomLeftQuest.isPattern(matrix,44,41));
    }


}