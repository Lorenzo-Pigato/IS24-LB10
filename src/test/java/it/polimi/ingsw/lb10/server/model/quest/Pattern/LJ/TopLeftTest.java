package it.polimi.ingsw.lb10.server.model.quest.Pattern.LJ;

import it.polimi.ingsw.lb10.server.model.Matrix;
import it.polimi.ingsw.lb10.server.model.cards.decks.QuestDeck;
import it.polimi.ingsw.lb10.server.model.cards.decks.ResourceDeck;
import it.polimi.ingsw.lb10.server.model.quest.Quest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TopLeftTest {
    private static Matrix matrix;
    private static Quest bottomRightQuest;
    private static QuestDeck questDeck=new QuestDeck();
    private static ResourceDeck resourceDeck=new ResourceDeck();

    /**
     * test of the card 100
     */
    @BeforeAll
    static void setUp(){
        matrix=new Matrix();
        questDeck.fillDeck();
        resourceDeck.fillDeck();
        bottomRightQuest = questDeck.getCards().get(13); // purple blue
    }

    @AfterEach
    void cleanUp(){
        matrix.deleteCard(41,41);
        matrix.deleteCard(43,41);
        matrix.deleteCard(40,40);
    }

    @Test
    public void isPatternRightOne(){
        matrix.setCard(resourceDeck.getCards().get(21));
        matrix.setCard(resourceDeck.getCards().get(22),43,41);
        matrix.setCard(resourceDeck.getCards().get(34),40,40);

        assertTrue(bottomRightQuest.isPattern(matrix,41,41));
    }
    @Test
    public void isPatternRightTwo(){
        matrix.setCard(resourceDeck.getCards().get(23));
        matrix.setCard(resourceDeck.getCards().get(24),43,41);
        matrix.setCard(resourceDeck.getCards().get(35),40,40);

        assertTrue(bottomRightQuest.isPattern(matrix,43,41));
    }
    @Test
    public void isPatternRightThree(){
        matrix.setCard(resourceDeck.getCards().get(25));
        matrix.setCard(resourceDeck.getCards().get(26),43,41);
        matrix.setCard(resourceDeck.getCards().get(36),40,40);

        assertTrue(bottomRightQuest.isPattern(matrix,40,40));
    }

    @Test
    public void isPatternNotRightTop(){
        matrix.setCard(resourceDeck.getCards().get(23));
        matrix.setCard(resourceDeck.getCards().get(22),43,41);
        matrix.setCard(resourceDeck.getCards().get(1),40,40);

        assertFalse(bottomRightQuest.isPattern(matrix,41,41));
        assertFalse(bottomRightQuest.isPattern(matrix,43,41));
        assertFalse(bottomRightQuest.isPattern(matrix,40,40));
    }

    @Test
    public void isPatternNotRightMiddle(){
        matrix.setCard(resourceDeck.getCards().get(1));
        matrix.setCard(resourceDeck.getCards().get(23),43,41);
        matrix.setCard(resourceDeck.getCards().get(22),40,40);

        assertFalse(bottomRightQuest.isPattern(matrix,41,41));
        assertFalse(bottomRightQuest.isPattern(matrix,43,41));
        assertFalse(bottomRightQuest.isPattern(matrix,40,40));
    }

    @Test
    public void isPatternNotRightBot(){
        matrix.setCard(resourceDeck.getCards().get(22));
        matrix.setCard(resourceDeck.getCards().get(1),43,41);
        matrix.setCard(resourceDeck.getCards().get(23),40,40);

        assertFalse(bottomRightQuest.isPattern(matrix,41,41));
        assertFalse(bottomRightQuest.isPattern(matrix,43,41));
        assertFalse(bottomRightQuest.isPattern(matrix,40,40));
    }

}