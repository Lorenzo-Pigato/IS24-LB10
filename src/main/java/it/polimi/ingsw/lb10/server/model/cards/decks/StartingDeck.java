package it.polimi.ingsw.lb10.server.model.cards.decks;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.lb10.server.model.cards.StartingCard;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class StartingDeck {

    private ArrayList<StartingCard> cards;

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
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            cards = objectMapper.readValue(new File("src/main/resources/startingDeck.json"), new TypeReference<ArrayList<StartingCard>>() {});
        }catch (Exception e){
            e.printStackTrace();
        }
        shuffle();
    }
}
