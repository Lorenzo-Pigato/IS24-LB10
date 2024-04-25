package it.polimi.ingsw.lb10.server.model.cards.decks;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.lb10.server.model.cards.ResourceCard;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

public class ResourceDeck {

    private ArrayList<ResourceCard> cards ;

    public ResourceDeck(){
        cards = new ArrayList<>();
    }


    public void shuffle() {
        Collections.shuffle(getCards());
    }

    public ResourceCard drawCard() throws NoSuchElementException {
            ResourceCard card = cards.getLast();
            cards.removeLast();
        return card;
    }

    public ArrayList<ResourceCard> getCards() {
        return cards;
    }

    /**
     * This method calls the json with the complete resource cards' deck
     */
    public void fillDeck(){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("resourceDeck.json");
            cards = objectMapper.readValue(inputStream , new TypeReference<ArrayList<ResourceCard>>() {});
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}


