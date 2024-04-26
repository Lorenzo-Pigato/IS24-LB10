package it.polimi.ingsw.lb10.server.model.cards.decks;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.lb10.server.model.cards.StartingCard;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

public class StartingDeck {

    private ArrayList<StartingCard> cards;

    public StartingDeck(){
        cards = new ArrayList<>();
    }

    public void shuffle() {
        Collections.shuffle(getCards());
    }

    public StartingCard drawCard() {
        StartingCard card = cards.getLast();
        cards.removeLast();
        return card;
    }

    public ArrayList<StartingCard> getCards() {
        return cards;
    }

    /**
     * This method calls the json with the complete resource cards' deck
     */
    public void fillDeck(){
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("startingDeck.json");
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            cards = objectMapper.readValue(inputStream, new TypeReference<ArrayList<StartingCard>>() {});
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
