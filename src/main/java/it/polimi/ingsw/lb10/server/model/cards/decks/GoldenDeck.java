package it.polimi.ingsw.lb10.server.model.cards.decks;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.lb10.server.Server;
import it.polimi.ingsw.lb10.server.model.cards.GoldenCard;
import it.polimi.ingsw.lb10.server.model.quest.Quest;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

public class GoldenDeck {

    private ArrayList<GoldenCard> cards;

    /**
     * Complete deck of GoldenCard, it has 40 cards
     * The cards are taken from the json in resource.
     */
    public GoldenDeck() {
        cards = new ArrayList<>();
    }


    public void shuffle() {
        Collections.shuffle(getCards());
    }

    public GoldenCard drawCard() throws NoSuchElementException {
        GoldenCard card = cards.getLast();
        cards.removeLast();
        return card;
    }

    public ArrayList<GoldenCard> getCards() {
        return cards;
    }

    public void fillDeck() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("goldenDeck.json");
            cards = objectMapper.readValue(inputStream, new TypeReference<ArrayList<GoldenCard>>() {
            });
        } catch (Exception e) {
            Server.log(">> ERROR [" + e.getMessage() + "]");
        }
    }

    public Quest drawQuest() {
        return null;
    }
}
