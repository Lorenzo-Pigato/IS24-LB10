package it.polimi.ingsw.lb10.server.model.quest.Pattern.LJ;

import it.polimi.ingsw.lb10.server.model.Matrix;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;
import it.polimi.ingsw.lb10.server.model.cards.decks.QuestDeck;
import it.polimi.ingsw.lb10.server.model.cards.decks.ResourceDeck;
import it.polimi.ingsw.lb10.server.model.quest.Quest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BottomLeftTest {
    Matrix matrix=new Matrix();
    private static Quest bottomLeftQuest;
    private static QuestDeck questDeck=new QuestDeck();
    private static ResourceDeck resourceDeck=new ResourceDeck();

    @BeforeAll
    static void setUp(){
        questDeck.fillDeck();
        resourceDeck.fillDeck();
        bottomLeftQuest = questDeck.getCards().get(12);
    }

    @AfterEach
    void cleanUp(){
        matrix.deleteCard(41,41);
        matrix.deleteCard(41,43);
        matrix.deleteCard(40,44);
    }

    @Test
    public void isPatternRightOne(){
        matrix.setCard(resourceDeck.getCards().get(31));
        matrix.setCard(resourceDeck.getCards().get(32),41,43);
        matrix.setCard(resourceDeck.getCards().get(1),40,44);

        assertTrue(bottomLeftQuest.isPattern(matrix,41,41));
    }

    @Test
    public void isPatternRightTwo(){
        matrix.setCard(resourceDeck.getCards().get(33));
        matrix.setCard(resourceDeck.getCards().get(34),41,43);
        matrix.setCard(resourceDeck.getCards().get(2),40,44);

        assertTrue(bottomLeftQuest.isPattern(matrix,41,43));
    }

    @Test
    public void isPatternRightThree(){
        matrix.setCard(resourceDeck.getCards().get(35));
        matrix.setCard(resourceDeck.getCards().get(36),41,43);
        matrix.setCard(resourceDeck.getCards().get(3),40,44);

        assertTrue(bottomLeftQuest.isPattern(matrix,40,44));
    }

    @Test
    public void isPatternNotRightTop(){
        matrix.setCard(resourceDeck.getCards().get(1));
        matrix.setCard(resourceDeck.getCards().get(32),41,43);
        matrix.setCard(resourceDeck.getCards().get(31),40,44);

        assertFalse(bottomLeftQuest.isPattern(matrix,41,41));
        assertFalse(bottomLeftQuest.isPattern(matrix,41,43));
        assertFalse(bottomLeftQuest.isPattern(matrix,41,44));
    }
    @Test
    public void isPatternNotRightMiddle(){
        matrix.setCard(resourceDeck.getCards().get(32));
        matrix.setCard(resourceDeck.getCards().get(1),41,43);
        matrix.setCard(resourceDeck.getCards().get(31),40,44);

        assertFalse(bottomLeftQuest.isPattern(matrix,41,41));
        assertFalse(bottomLeftQuest.isPattern(matrix,41,43));
        assertFalse(bottomLeftQuest.isPattern(matrix,41,44));
    }


}