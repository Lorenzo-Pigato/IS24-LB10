package it.polimi.ingsw.lb10.server.model.quest.Pattern.Diagonal;

import it.polimi.ingsw.lb10.server.model.Matrix;
import it.polimi.ingsw.lb10.server.model.cards.decks.QuestDeck;
import it.polimi.ingsw.lb10.server.model.cards.decks.ResourceDeck;
import it.polimi.ingsw.lb10.server.model.cards.decks.StartingDeck;
import it.polimi.ingsw.lb10.server.model.quest.Quest;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BottomLeftDiagonalTest {
    Matrix matrix=new Matrix();
    private static QuestDeck questDeck = new QuestDeck();
    private static ResourceDeck resourceDeck= new ResourceDeck();
    private static Quest diagonalQuest;
    private static StartingDeck startingDeck = new StartingDeck();


    @BeforeAll
    static void setUp(){
        questDeck.fillDeck();
        resourceDeck.fillDeck();
        startingDeck.fillDeck();
        diagonalQuest=questDeck.getCards().get(8);//  2 RED

    }
    @AfterEach
    void cleanUp(){
        matrix.deleteCard(41,41);
        matrix.deleteCard(40,42);
        matrix.deleteCard(42,40);
    }

    /**
     * This test isn't important if the card is placeable or not, it's import the colors
     */
    @Test
    void isCorrectPattern() {
        matrix.setCard(startingDeck.getCards().get(0));
        matrix.setCard(resourceDeck.getCards().get(1),40,42);
        matrix.setCard(resourceDeck.getCards().get(2),42,40);

        assertTrue(diagonalQuest.isPattern(matrix,40,42));
        assertTrue(diagonalQuest.isPattern(matrix,41,41));
        assertTrue(diagonalQuest.isPattern(matrix,42,40));
    }

    /**
     * One card it's green, in the bottom part, so it will return false!
     */
    @Test
    void unCorrectPatternBottom(){
        matrix.setCard(startingDeck.getCards().get(0));
        matrix.setCard(resourceDeck.getCards().get(13),40,42);
        matrix.setCard(resourceDeck.getCards().get(2),42,40);

        assertFalse(diagonalQuest.isPattern(matrix,40,42));
        assertFalse(diagonalQuest.isPattern(matrix,41,41));
        assertFalse(diagonalQuest.isPattern(matrix,42,40));
    }

    /**
     * One card it's green, in the middle part, so it will return false
     */
    @Test
    void unCorrectPatternMiddle(){
        matrix.setCard(startingDeck.getCards().get(4));
        matrix.setCard(resourceDeck.getCards().get(0),40,42);
        matrix.setCard(resourceDeck.getCards().get(2),42,40);

        assertFalse(diagonalQuest.isPattern(matrix,40,42));
        assertFalse(diagonalQuest.isPattern(matrix,41,41));
        assertFalse(diagonalQuest.isPattern(matrix,42,40));
    }

    /**
     * One card it's green, in the top part, so it will return false
     */
    @Test
    void unCorrectPatternTop(){
        matrix.setCard(startingDeck.getCards().get(1));
        matrix.setCard(resourceDeck.getCards().get(0),40,42);
        matrix.setCard(resourceDeck.getCards().get(13),42,40);

        assertFalse(diagonalQuest.isPattern(matrix,40,42));
        assertFalse(diagonalQuest.isPattern(matrix,41,41));
        assertFalse(diagonalQuest.isPattern(matrix,42,40));
    }


}