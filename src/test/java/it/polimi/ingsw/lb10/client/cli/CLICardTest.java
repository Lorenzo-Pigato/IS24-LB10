package it.polimi.ingsw.lb10.client.cli;

import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.GoldenCard;
import it.polimi.ingsw.lb10.server.model.cards.ResourceCard;
import it.polimi.ingsw.lb10.server.model.cards.StartingCard;
import it.polimi.ingsw.lb10.server.model.cards.decks.GoldenDeck;
import it.polimi.ingsw.lb10.server.model.cards.decks.ResourceDeck;
import it.polimi.ingsw.lb10.server.model.cards.decks.StartingDeck;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;



import static org.junit.jupiter.api.Assertions.*;

public class CLICardTest {

    private static GoldenDeck goldenDeck;
    private static ResourceDeck resourceDeck;
    private static StartingDeck startingDeck;

    @BeforeAll
    public static void setUp() {
        goldenDeck = new GoldenDeck();
        goldenDeck.fillDeck();
        resourceDeck = new ResourceDeck();
        resourceDeck.fillDeck();
        startingDeck = new StartingDeck();
        startingDeck.fillDeck();

    }

    @Test
    public void goldenCardInHandPrintTest() {
        setUp();

        for(GoldenCard card : goldenDeck.getCards()) {
            CLICard.printPlaceableCard(card , 0, 0);
            assertDoesNotThrow(() -> new Throwable());
        }
    }

    @Test
    public void resourceDeckPrintTest() {
        setUp();

        for(ResourceCard resourceCard : resourceDeck.getCards()) {
            CLICard.printPlaceableCard(resourceCard, 0, 0);
            assertDoesNotThrow(() -> new Throwable());
        }
    }

    @Test
    public void startingDeckPrintTest() {
        setUp();
        for(StartingCard startingCard : startingDeck.getCards()) {
            CLICard.displayStartingCard(startingCard, 0, 0);
            assertDoesNotThrow(( ) -> new Throwable());
        }
    }

}