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
        matrix.deleteCard(43,41);
        matrix.deleteCard(40,42);
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