package it.polimi.ingsw.lb10.server.model.cards.decks;

import it.polimi.ingsw.lb10.server.model.cards.ResourceCard;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class ResourceDeckTest {

    private static ResourceDeck resourceDeck;

    @Test
    public void fillDeckTest(){
        resourceDeck = new ResourceDeck();
        resourceDeck.fillDeck();
        assertFalse(resourceDeck.getCards().isEmpty());
        assertEquals(40, resourceDeck.getCards().size());
    }

    @Test
    public void drawCardTest(){
        resourceDeck = new ResourceDeck();
        resourceDeck.fillDeck();
        ResourceCard oracle = resourceDeck.getCards().getLast();
        ResourceCard first = resourceDeck.drawCard();
        assertEquals(first, oracle);
        assertEquals(39, resourceDeck.getCards().size());
    }
@Test
    public void shuffleTest(){
        resourceDeck = new ResourceDeck();
        resourceDeck.fillDeck();
        ResourceCard oracle = resourceDeck.getCards().getLast();
        resourceDeck.shuffle();
        assertNotEquals(resourceDeck.getCards().getLast(), oracle);
    }
    @Test
    public void deckEndTest(){
        resourceDeck = new ResourceDeck();
        resourceDeck.fillDeck();
        while(!resourceDeck.getCards().isEmpty()){
            resourceDeck.drawCard();
        }
        assertThrows(NoSuchElementException.class, () -> resourceDeck.drawCard());
    }
}