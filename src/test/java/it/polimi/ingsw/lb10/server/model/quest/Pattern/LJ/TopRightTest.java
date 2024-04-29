package it.polimi.ingsw.lb10.server.model.quest.Pattern.LJ;

import it.polimi.ingsw.lb10.server.model.Matrix;
import it.polimi.ingsw.lb10.server.model.cards.decks.QuestDeck;
import it.polimi.ingsw.lb10.server.model.cards.decks.ResourceDeck;
import it.polimi.ingsw.lb10.server.model.quest.Quest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
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
        topRight=questDeck.getCards().getLast(); // Green, Purple
    }

    @AfterEach
    void cleanUp(){
        matrix.deleteCard(41,41);
        matrix.deleteCard(41,43);
        matrix.deleteCard(42,40);
    }

    @Test
    public void isPatternRightOne(){
        matrix.setCard(resourceDeck.getCards().get(15));
        matrix.setCard(resourceDeck.getCards().get(16),41,43);
        matrix.setCard(resourceDeck.getCards().get(27),42,40);

        assertTrue(topRight.isPattern(matrix,41,41));
    }

    @Test
    public void isPatternRightTwo(){
        matrix.setCard(resourceDeck.getCards().get(11));
        matrix.setCard(resourceDeck.getCards().get(12),41,43);
        matrix.setCard(resourceDeck.getCards().get(28),42,40);

        assertTrue(topRight.isPattern(matrix,41,43));
    }

    @Test
    public void isPatternRightThree(){
        matrix.setCard(resourceDeck.getCards().get(13));
        matrix.setCard(resourceDeck.getCards().get(14),41,43);
        matrix.setCard(resourceDeck.getCards().get(29),42,40);

        assertTrue(topRight.isPattern(matrix,42,40));
    }

    @Test
    public void isPatternNotRightTop(){
        matrix.setCard(resourceDeck.getCards().get(15));
        matrix.setCard(resourceDeck.getCards().get(16),41,43);
        matrix.setCard(resourceDeck.getCards().get(3),42,40);

        assertFalse(topRight.isPattern(matrix,41,41));
        assertFalse(topRight.isPattern(matrix,41,43));
        assertFalse(topRight.isPattern(matrix,42,40));
    }

    @Test
    public void isPatternNotRightMiddle(){
        matrix.setCard(resourceDeck.getCards().get(2));
        matrix.setCard(resourceDeck.getCards().get(15),41,43);
        matrix.setCard(resourceDeck.getCards().get(3),42,40);

        assertFalse(topRight.isPattern(matrix,41,41));
        assertFalse(topRight.isPattern(matrix,41,43));
        assertFalse(topRight.isPattern(matrix,42,40));
    }

    @Test
    public void isPatternNotRightBot(){
        matrix.setCard(resourceDeck.getCards().get(15));
        matrix.setCard(resourceDeck.getCards().get(12),41,43);
        matrix.setCard(resourceDeck.getCards().get(2),42,40);

        assertFalse(topRight.isPattern(matrix,41,41));
        assertFalse(topRight.isPattern(matrix,41,43));
        assertFalse(topRight.isPattern(matrix,42,40));
    }
}