package it.polimi.ingsw.lb10.server.model.cards.decks;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.lb10.server.model.cards.ResourceCard;
import it.polimi.ingsw.lb10.server.model.quest.Quest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

public class ResourceDeck {

    private static ArrayList<ResourceCard> cards ;

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
            File file = new File("src/main/resources/resourceDeck.json");
            cards = objectMapper.readValue(file, new TypeReference<ArrayList<ResourceCard>>() {});
        }catch (IOException e){

        }
    }

}


