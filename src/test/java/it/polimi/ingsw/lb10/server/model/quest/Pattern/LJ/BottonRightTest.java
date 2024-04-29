package it.polimi.ingsw.lb10.server.model.quest.Pattern.LJ;

import it.polimi.ingsw.lb10.server.model.Matrix;
import it.polimi.ingsw.lb10.server.model.cards.decks.QuestDeck;
import it.polimi.ingsw.lb10.server.model.cards.decks.ResourceDeck;
import it.polimi.ingsw.lb10.server.model.quest.Quest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BottonRightTest {
    Matrix matrix=new Matrix();
    private static Quest bottomRightQuest;
    private static final QuestDeck questDeck=new QuestDeck();
    private static final ResourceDeck resourceDeck=new ResourceDeck();

    /**
     * test of the card 101
     */
    @BeforeAll
    static void setUp(){
        questDeck.fillDeck();
        resourceDeck.fillDeck();
        bottomRightQuest = questDeck.getCards().get(14); // red green
    }

    @AfterEach
    void cleanUp(){
        matrix.deleteCard(41,41);
        matrix.deleteCard(41,43);
        matrix.deleteCard(42,44);
    }

    @Test
    public void isPatternRightOne(){
        matrix.setCard(resourceDeck.getCards().get(1));
        matrix.setCard(resourceDeck.getCards().get(2),41,43);
        matrix.setCard(resourceDeck.getCards().get(19),42,44);

        assertTrue(bottomRightQuest.isPattern(matrix,41,41));
    }

    @Test
    public void isPatternRightTwo(){
        matrix.setCard(resourceDeck.getCards().get(3));
        matrix.setCard(resourceDeck.getCards().get(4),41,43);
        matrix.setCard(resourceDeck.getCards().get(17),42,44);

        assertTrue(bottomRightQuest.isPattern(matrix,41,43));
    }

    @Test
    public void isPatternRightThree(){
        matrix.setCard(resourceDeck.getCards().get(5));
        matrix.setCard(resourceDeck.getCards().get(6),41,43);
        matrix.setCard(resourceDeck.getCards().get(16),42,44);

        assertTrue(bottomRightQuest.isPattern(matrix,42,44));
    }

    @Test
    public void isPatternNotRightTop(){
        matrix.setCard(resourceDeck.getCards().get(31));
        matrix.setCard(resourceDeck.getCards().get(2),41,43);
        matrix.setCard(resourceDeck.getCards().get(3),42,44);

        assertFalse(bottomRightQuest.isPattern(matrix,41,41));
        assertFalse(bottomRightQuest.isPattern(matrix,41,43));
        assertFalse(bottomRightQuest.isPattern(matrix,42,44));
    }
    @Test
    public void isPatternNotRightMiddle(){
        matrix.setCard(resourceDeck.getCards().get(2));
        matrix.setCard(resourceDeck.getCards().get(31),41,43);
        matrix.setCard(resourceDeck.getCards().get(3),42,44);

        assertFalse(bottomRightQuest.isPattern(matrix,41,41));
        assertFalse(bottomRightQuest.isPattern(matrix,41,43));
        assertFalse(bottomRightQuest.isPattern(matrix,42,44));
    }

    @Test
    public void isPatternNotRightBot(){
        matrix.setCard(resourceDeck.getCards().get(2));
        matrix.setCard(resourceDeck.getCards().get(3),41,43);
        matrix.setCard(resourceDeck.getCards().get(31),42,44);

        assertFalse(bottomRightQuest.isPattern(matrix,41,41));
        assertFalse(bottomRightQuest.isPattern(matrix,41,43));
        assertFalse(bottomRightQuest.isPattern(matrix,42,44));
    }
}