package it.polimi.ingsw.lb10.server.model.quest.Pattern.Diagonal;

import it.polimi.ingsw.lb10.server.model.Matrix;
import it.polimi.ingsw.lb10.server.model.cards.Color;
import it.polimi.ingsw.lb10.server.model.cards.PlaceableCard;
import it.polimi.ingsw.lb10.server.model.cards.decks.GoldenDeck;
import it.polimi.ingsw.lb10.server.model.cards.decks.QuestDeck;
import it.polimi.ingsw.lb10.server.model.cards.decks.ResourceDeck;
import it.polimi.ingsw.lb10.server.model.quest.Quest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BottomLeftDiagonalTest {
    final Matrix matrix = new Matrix();
    private static final QuestDeck questDeck = new QuestDeck();
    private static final ResourceDeck resourceDeck = new ResourceDeck();
    private static final GoldenDeck goldenDeck = new GoldenDeck();
    private static Quest diagonalQuest;

    @BeforeAll
    static void setUp() {
        questDeck.fillDeck();
        resourceDeck.fillDeck();
        goldenDeck.fillDeck();
        diagonalQuest = questDeck.getCards().get(8);//  2 RED
    }


    void cleanUp() {
        matrix.deleteCard(42, 40);
        matrix.deleteCard(41, 41);
        matrix.deleteCard(40, 42);
    }

    @RepeatedTest(100)
    void checkingResourcePattern() {
        boolean flag = false;
        setUp();
        for (int i = 0; i < 3; i++) {
            int nRandom = (int) (Math.random() * 40);
            PlaceableCard card = resourceDeck.getCards().get(nRandom);
            matrix.setCard(card, 42 - i, 40 + i);

            if (card.getColorCard() != Color.RED)
                flag = true;
        }

        int iRandom = (int) (Math.random() * 3);
        int row = 42 - iRandom;
        int column = 40 + iRandom;
        if (flag)
            assertFalse(diagonalQuest.isPattern(matrix, row, column));
        else
            assertTrue(diagonalQuest.isPattern(matrix, row, column));
        cleanUp();
    }

    @RepeatedTest(1000)
    void checkingGoldenPattern() {
        boolean flag = false;
        setUp();
        for (int i = 0; i < 3; i++) {
            int nRandom = (int) (Math.random() * 40);
            PlaceableCard card = goldenDeck.getCards().get(nRandom);
            matrix.setCard(card, 42 - i, 40 + i);

            if (card.getColorCard() != Color.RED)
                flag = true;
        }

        int iRandom = (int) (Math.random() * 3);
        int row = 42 - iRandom;
        int column = 40 + iRandom;
        if (flag)
            assertFalse(diagonalQuest.isPattern(matrix, row, column));
        else
            assertTrue(diagonalQuest.isPattern(matrix, row, column));
        cleanUp();
    }

    //For this test, isn't important if the card is placeable or not, it's import the colors
    @Test
    void isCorrectPattern() {
        matrix.setCard(resourceDeck.getCards().get(0));
        matrix.setCard(resourceDeck.getCards().get(1), 40, 42);
        matrix.setCard(resourceDeck.getCards().get(2), 42, 40);
    }
    //One card it's green, in the middle part, so it will return false
    @Test
    void unCorrectPatternMiddle() {
        matrix.setCard(resourceDeck.getCards().get(13));
        matrix.setCard(resourceDeck.getCards().get(0), 40, 42);
        matrix.setCard(resourceDeck.getCards().get(2), 42, 40);
    }

    //One card it's green, in the top part, so it will return false
    @Test
    void unCorrectPatternTop() {
        matrix.setCard(resourceDeck.getCards().get(1));
        matrix.setCard(resourceDeck.getCards().get(0), 40, 42);
        matrix.setCard(resourceDeck.getCards().get(13), 42, 40);

        assertFalse(diagonalQuest.isPattern(matrix, 40, 42));
        assertFalse(diagonalQuest.isPattern(matrix, 41, 41));
        assertFalse(diagonalQuest.isPattern(matrix, 42, 40));
    }
}