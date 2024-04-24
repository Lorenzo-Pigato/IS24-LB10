package it.polimi.ingsw.lb10.server.model.cards.decks;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.lb10.server.Server;
import it.polimi.ingsw.lb10.server.model.cards.GoldenCard;
import it.polimi.ingsw.lb10.server.model.cards.ResourceCard;
import it.polimi.ingsw.lb10.server.model.quest.Quest;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

public class GoldenDeck{

    private  ArrayList<GoldenCard> cards ;

    public GoldenDeck(){
        System.out.println("Building gold");
        cards = new ArrayList<>();
    }


    public void shuffle(){
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

    public void fillDeck(){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("resourceDeck.json");
            cards = objectMapper.readValue(inputStream , new TypeReference<ArrayList<GoldenCard>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Quest drawQuest() {
        return null;
    }
}
