package it.polimi.ingsw.lb10.server.model.cards.decks;

import it.polimi.ingsw.lb10.server.model.cards.GoldenCard;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class GoldenDeckTest {

    private static GoldenDeck goldenDeck;
    @Test
    public void fillDeckTest(){
        goldenDeck = new GoldenDeck();
        goldenDeck.fillDeck();
        assertTrue(!goldenDeck.getCards().isEmpty());
        assertTrue(goldenDeck.getCards().size() == 40);
    }

    @Test
    public void drawCardTest(){
        goldenDeck = new GoldenDeck();
        goldenDeck.fillDeck();
        GoldenCard oracle = goldenDeck.getCards().getLast();
        GoldenCard first = goldenDeck.drawCard();
        assertEquals(first, oracle);
        assertEquals(39, goldenDeck.getCards().size());
    }

    @Test
    public void deckEndTest(){
        goldenDeck = new GoldenDeck();
        goldenDeck.fillDeck();
        while(!goldenDeck.getCards().isEmpty()){
            goldenDeck.drawCard();
        }
        assertThrows(NoSuchElementException.class, () -> goldenDeck.drawCard());
    }

}