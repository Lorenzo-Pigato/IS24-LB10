package it.polimi.ingsw.lb10.server.model.cards.decks;

import it.polimi.ingsw.lb10.server.model.cards.GoldenCard;
import it.polimi.ingsw.lb10.server.model.cards.StartingCard;
import org.junit.Test;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class StartingDeckTest {

    private static StartingDeck startingDeck;

    @Test
    public void fillDeckTest(){
        startingDeck = new StartingDeck();
        startingDeck.fillDeck();
        assertTrue(!startingDeck.getCards().isEmpty());
        assertTrue(startingDeck.getCards().size() == 6);
    }

    @Test
    public void drawCardTest(){
        startingDeck = new StartingDeck();
        startingDeck.fillDeck();
        StartingCard oracle = startingDeck.getCards().getLast();
        StartingCard first = startingDeck.drawCard();
        assertEquals(first, oracle);
        assertTrue(startingDeck.getCards().size() == 5);

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