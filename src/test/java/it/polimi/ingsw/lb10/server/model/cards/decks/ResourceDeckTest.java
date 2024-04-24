package it.polimi.ingsw.lb10.server.model.cards.decks;

import it.polimi.ingsw.lb10.server.model.cards.ResourceCard;
import org.junit.Test;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class ResourceDeckTest {

    private static ResourceDeck resourceDeck;

    @Test
    public void fillDeckTest(){
        resourceDeck = new ResourceDeck(new ArrayList<>());
        resourceDeck.fillDeck();
        assertTrue(!resourceDeck.getCards().isEmpty());
        assertTrue(resourceDeck.getCards().size() == 40);
    }

    @Test
    public void drawCardTest(){
        resourceDeck = new ResourceDeck(new ArrayList<>());
        resourceDeck.fillDeck();
        ResourceCard oracle = resourceDeck.getCards().getLast();
        ResourceCard first = resourceDeck.drawCard();
        assertEquals(first, oracle);
        assertTrue(resourceDeck.getCards().size() == 39);
    }

    @Test
    public void deckEndTest(){
        resourceDeck = new ResourceDeck(new ArrayList<>());
        resourceDeck.fillDeck();
        while(!resourceDeck.getCards().isEmpty()){
            resourceDeck.drawCard();
        }
        assertThrows(NoSuchElementException.class, () -> resourceDeck.drawCard());
    }
}