package it.polimi.ingsw.lb10.server.model.cards.decks;

import it.polimi.ingsw.lb10.server.model.cards.GoldenCard;
import it.polimi.ingsw.lb10.server.model.cards.StartingCard;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class StartingDeckTest {

    private static StartingDeck startingDeck;

    public void fillDeckTest(){
        startingDeck = new StartingDeck();
        startingDeck.fillDeck();
        assertFalse(startingDeck.getCards().isEmpty());
        assertEquals(6, startingDeck.getCards().size());
    }

    @Test
    public void drawCardTest(){
        startingDeck = new StartingDeck();
        startingDeck.fillDeck();
        StartingCard oracle = startingDeck.getCards().getLast();
        StartingCard first = startingDeck.drawCard();
        assertEquals(first, oracle);
        assertEquals(5, startingDeck.getCards().size());
    }

    @Test
    public void shuffleTest(){
        startingDeck = new StartingDeck();
        startingDeck.fillDeck();
        StartingCard oracle = startingDeck.getCards().getLast();
        startingDeck.shuffle();
        assertNotEquals(startingDeck.getCards().getLast(), oracle);
    }

    @Test
    public void deckEndTest(){
        startingDeck = new StartingDeck();
        startingDeck.fillDeck();
        while(!startingDeck.getCards().isEmpty()){
            startingDeck.drawCard();
        }
        assertThrows(NoSuchElementException.class, () -> startingDeck.drawCard());
    }

}