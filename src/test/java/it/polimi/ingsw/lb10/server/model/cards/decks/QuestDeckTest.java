package it.polimi.ingsw.lb10.server.model.cards.decks;
import it.polimi.ingsw.lb10.server.model.quest.Quest;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class QuestDeckTest {

    private static QuestDeck questDeck;
    @Test
    public void fillDeckTest(){
        questDeck = new QuestDeck();
        questDeck.fillDeck();
        assertTrue(!questDeck.getCards().isEmpty());
        assertTrue(questDeck.getCards().size() == 16);
    }

    @Test
    public void drawCardTest(){
        questDeck = new QuestDeck();
        questDeck.fillDeck();
        Quest oracle = questDeck.getCards().getLast();
        Quest first = questDeck.drawCard();
        assertEquals(first, oracle);
        assertTrue(questDeck.getCards().size() == 15);
    }

    @Test
    public void shuffleTest(){
        questDeck = new QuestDeck();
        questDeck.fillDeck();
        Quest oracle = questDeck.getCards().getLast();
        questDeck.shuffle();
        assertNotEquals(questDeck.getCards().getLast(), oracle);
    }

    @Test
    public void deckEndTest(){
        questDeck = new QuestDeck();
        questDeck.fillDeck();
        while(!questDeck.getCards().isEmpty()){
            questDeck.drawCard();
        }
        assertThrows(NoSuchElementException.class, () -> questDeck.drawCard());
    }

}

