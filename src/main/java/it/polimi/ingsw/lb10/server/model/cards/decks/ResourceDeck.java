package it.polimi.ingsw.lb10.server.model.cards.decks;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.lb10.server.Server;
import it.polimi.ingsw.lb10.server.model.cards.ResourceCard;
import it.polimi.ingsw.lb10.server.model.quest.Quest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

public class ResourceDeck {

    private ArrayList<ResourceCard> cards = new ArrayList<>(0);

    public ResourceDeck(ArrayList<ResourceCard> cards) {
        this.cards = cards;
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
        cards.removeAll(cards);
        System.out.println("removed cards");
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            System.out.println("created mapper");
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("resourceDeck.json");
            System.out.println("ResourceDeck fillDeck " + inputStream.available());
            cards = objectMapper.readValue(inputStream , new TypeReference<ArrayList<ResourceCard>>() {});
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}


