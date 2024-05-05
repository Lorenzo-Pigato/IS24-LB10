package it.polimi.ingsw.lb10.server.model.quest.Pattern.Diagonal;

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

class TopLeftDiagonalTest {
    Matrix matrix = new Matrix();
    private static QuestDeck questDeck = new QuestDeck();
    private static ResourceDeck resourceDeck = new ResourceDeck();
    private static Quest diagonalQuest;

    /**
     * Quest card n 97
     */
    @BeforeAll
    static void setUp() {
        questDeck.fillDeck();
        resourceDeck.fillDeck();
        diagonalQuest = questDeck.getCards().get(10);//  2 RED

    }

    @AfterEach
    void cleanUp() {
        matrix.deleteCard(41, 41);
        matrix.deleteCard(40, 40);
        matrix.deleteCard(42, 42);
    }

    @RepeatedTest(1000)
    void checkingPattern() {
        boolean flag = false;
        setUp();
        for (int i = 0; i < 3; i++) {
            int nRandom = (int) (Math.random() * 40);
            PlaceableCard card = resourceDeck.getCards().get(nRandom);
            matrix.setCard(card, 40 + i, 40 + i);

            if (card.getColorCard() != Color.GREEN)
                flag = true;
        }

        int iRandom = (int) (Math.random() * 3);
        int row = 40 + iRandom;
        int column = 40 + iRandom;
        if (flag)
            assertFalse(diagonalQuest.isPattern(matrix, row, column));
        else
            assertTrue(diagonalQuest.isPattern(matrix, row, column));
    }

    //One card it's green, in the middle part, so it will return false

    @Test
    void unCorrectPatternMiddle() {
        matrix.setCard(resourceDeck.getCards().get(22));
        matrix.setCard(resourceDeck.getCards().get(12), 40, 40);
        matrix.setCard(resourceDeck.getCards().get(13), 42, 42);

        assertFalse(diagonalQuest.isPattern(matrix, 40, 40));
        assertFalse(diagonalQuest.isPattern(matrix, 41, 41));
        assertFalse(diagonalQuest.isPattern(matrix, 42, 42));
    }

    /**
     * One card it's green, in the top part, so it will return false
     */
    @Test
    void unCorrectPatternTop() {
        matrix.setCard(resourceDeck.getCards().get(12));
        matrix.setCard(resourceDeck.getCards().get(13), 40, 40);
        matrix.setCard(resourceDeck.getCards().get(22), 42, 42);

        assertFalse(diagonalQuest.isPattern(matrix, 40, 40));
        assertFalse(diagonalQuest.isPattern(matrix, 41, 41));
        assertFalse(diagonalQuest.isPattern(matrix, 42, 42));
    }

    /**
     * One card it's green, in the bottom part, so it will return false!
     */
    @Test
    void unCorrectPatternBottom() {
        matrix.setCard(resourceDeck.getCards().get(12));
        matrix.setCard(resourceDeck.getCards().get(13), 40, 40);
        matrix.setCard(resourceDeck.getCards().get(22), 42, 42);

        assertFalse(diagonalQuest.isPattern(matrix, 40, 40));
        assertFalse(diagonalQuest.isPattern(matrix, 41, 41));
        assertFalse(diagonalQuest.isPattern(matrix, 42, 42));
    }
}