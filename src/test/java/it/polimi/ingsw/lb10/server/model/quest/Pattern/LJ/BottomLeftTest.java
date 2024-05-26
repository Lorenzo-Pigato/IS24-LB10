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
    private static final ResourceDeck resourceDeck=new ResourceDeck();

    @BeforeAll
    static void setUp(){
        resourceDeck.fillDeck();
        bottomLeftQuest = new BottomLeft(102, 3, Color.GREEN, Color.PURPLE);
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
            if (card.getColorCard() != Color.GREEN)
                flag = true;
        }

        int nRandom = (int) (Math.random() * 40);
        PlaceableCard card = resourceDeck.getCards().get(nRandom);
        matrix.setCard(card, 44,40);
        //TAIL
        if (card.getColorCard() != Color.PURPLE)
            flag = true;

        int iRandom = (int) (Math.random() * 3);
        int[] rows = {41, 43, 44};
        int[] cols = {41, 41, 40};

        if (flag)
            assertFalse(bottomLeftQuest.isPattern(matrix, rows[iRandom], cols[iRandom]));
        else
            assertTrue(bottomLeftQuest.isPattern(matrix, rows[iRandom], cols[iRandom]));

    }



}