package it.polimi.ingsw.lb10.server.model;

import it.polimi.ingsw.lb10.server.model.cards.PlaceableCard;
import it.polimi.ingsw.lb10.server.model.cards.StartingCard;
import it.polimi.ingsw.lb10.server.model.cards.decks.GoldenDeck;
import it.polimi.ingsw.lb10.server.model.cards.decks.ResourceDeck;
import it.polimi.ingsw.lb10.server.model.cards.decks.StartingDeck;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.*;


public class MatrixIndexesTest {

    private static Matrix matrix;
    private static ResourceDeck resourceDeck;
    private static StartingDeck startingDeck;
    private static GoldenDeck goldenDeck;

    @BeforeAll
    public static void setUp() {
        resourceDeck = new ResourceDeck();
        startingDeck = new StartingDeck();
        goldenDeck = new GoldenDeck();
        matrix = new Matrix();
        resourceDeck.fillDeck();
        resourceDeck.shuffle();
        startingDeck.fillDeck();
        startingDeck.shuffle();
        goldenDeck.fillDeck();
        goldenDeck.shuffle();
    }

    @Test
    public void centreCard(){
        setUp();
        PlaceableCard card = resourceDeck.drawCard();
        matrix.setCard(card, 41, 41);
        int expectedColumn = matrix.getCardColumn(card.getId());
        int expectedRow = matrix.getCardRow(card.getId());
        assertEquals(expectedRow, 41);
        assertEquals(expectedColumn, 41);
    }

    @Test
    public void twoCardsWithStarting(){
        setUp();
        PlaceableCard card1 = resourceDeck.drawCard();
        StartingCard card2 = startingDeck.drawCard();
        card2.swapState();
        matrix.setCard(card2);
        matrix.setCard(card1, 40, 40);
        assertEquals(41, matrix.getCardColumn(card2.getId()));
        assertEquals(41, matrix.getCardRow(card2.getId()));
        assertEquals(40, matrix.getCardColumn(card1.getId()));
        assertEquals(40, matrix.getCardRow(card1.getId()));

    }

    @Test
    public void fourCardsAllTypes(){
        setUp();
        PlaceableCard card1 = resourceDeck.drawCard();
        PlaceableCard card2 = goldenDeck.drawCard();
        StartingCard card3 = startingDeck.drawCard();
        PlaceableCard card4 = goldenDeck.drawCard();
        card3.swapState();
        matrix.setCard(card3);
        matrix.setCard(card2, 40, 40);
        matrix.setCard(card1, 40, 42);
        matrix.setCard(card4, 42, 42);

        //starting card in the middle
        assertEquals(41, matrix.getCardColumn(card3.getId()));
        assertEquals(41, matrix.getCardRow(card3.getId()));

        //other cards around
        assertEquals(40, matrix.getCardColumn(card2.getId()));
        assertEquals(40, matrix.getCardRow(card2.getId()));

        assertEquals(42, matrix.getCardColumn(card1.getId()));
        assertEquals(40, matrix.getCardRow(card1.getId()));

        assertEquals(42, matrix.getCardColumn(card4.getId()));
        assertEquals(42, matrix.getCardRow(card4.getId()));

    }

    @Test
    public void checkValidMatrixIdTest(){
        setUp();
        PlaceableCard card1 = resourceDeck.drawCard();
        PlaceableCard card2 = goldenDeck.drawCard();
        PlaceableCard card3 = resourceDeck.drawCard();
        PlaceableCard card4 = goldenDeck.drawCard();
        matrix.setCard(card2, 40, 40);
        matrix.setCard(card1, 40, 42);
        matrix.setCard(card4, 42, 42);
        MatchModel model = new MatchModel(3, null);
        Player player = new Player(0, null);
        player.setMatrix(matrix);

        assertTrue(model.checkValidMatrixCardId(card1.getId(), player));
        assertTrue(model.checkValidMatrixCardId(card2.getId(), player));
        assertTrue(model.checkValidMatrixCardId(card4.getId(), player));
        assertFalse(model.checkValidMatrixCardId(card3.getId(), player));
    }

}
