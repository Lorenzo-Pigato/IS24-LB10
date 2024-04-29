package it.polimi.ingsw.lb10.server.model;

import it.polimi.ingsw.lb10.server.controller.MatchController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MatchModelTest {
    static MatchModel matchModel;
    static MatchController matchController;
    static Player player1;
    static Player player2 ;
    static Player player3 ;

    static ArrayList<Player> players= new ArrayList<>();
    @BeforeAll
    static void setUp(){
        matchController=new MatchController(3);
        matchModel=new MatchModel(3,matchController);
        player1= new Player(123, "TestPlayer1");
        player2= new Player(456, "TestPlayer2");
        player3= new Player(789, "TestPlayer3");
        players.add(player1);
        players.add(player2);
        players.add(player3);
        matchModel.setPlayers(players);
    }
    @AfterEach
    void cleanUp(){
        matchModel.getGoldenDeck().getCards().clear();
        matchModel.getResourceDeck().getCards().clear();
        matchModel.getQuestDeck().getCards().clear();
    }

    /**
     * 321ms
     */
    @Test
    void initializeDecks() throws IOException {
        matchModel.initializeDecks();
        assertFalse(matchModel.getResourceDeck().getCards().isEmpty());
        assertEquals(40, matchModel.getResourceDeck().getCards().size());

        assertFalse(matchModel.getGoldenDeck().getCards().isEmpty());
        assertEquals(40, matchModel.getGoldenDeck().getCards().size());

        assertFalse(matchModel.getQuestDeck().getCards().isEmpty());
        assertEquals(16, matchModel.getQuestDeck().getCards().size());

        assertFalse(matchModel.getStartingDeck().getCards().isEmpty());
        assertEquals(6, matchModel.getStartingDeck().getCards().size());
    }

    /**
     * There isn't a method to set up the players inside the match model by constructor!
     */
    @Test
    void initializeMatrix(){
        matchModel.initializeMatrix();
        assertNotNull(player1.getMatrix());
        assertNotNull(player2.getMatrix());
        assertNotNull(player3.getMatrix());
    }


    @Test
    void initializeCardsOnHand() {
        matchModel.getGoldenDeck().fillDeck();
        matchModel.getResourceDeck().fillDeck();
        matchModel.getQuestDeck().fillDeck();


        matchModel.initializeCardsOnHand();
        assertNotNull(player1.getHand());
        assertNotNull(player2.getHand());
        assertNotNull(player3.getHand());

        assertEquals(3,player1.getHand().size());
        assertEquals(3,player2.getHand().size());
        assertEquals(3,player3.getHand().size());

    }

    @Test
    void startingUncoveredCards(){
        matchModel.getGoldenDeck().fillDeck();
        matchModel.getResourceDeck().fillDeck();
        matchModel.getQuestDeck().fillDeck();


        matchModel.startingUncoveredCards();
        assertNotNull(matchModel.getGoldenUncovered());
        assertNotNull(matchModel.getResourceUncovered());
        assertNotNull(matchModel.getCommonQuests());

        assertEquals(2,matchModel.getGoldenUncovered().size());
        assertEquals(2,matchModel.getResourceUncovered().size());
        assertEquals(2,matchModel.getCommonQuests().size());
    }

}

