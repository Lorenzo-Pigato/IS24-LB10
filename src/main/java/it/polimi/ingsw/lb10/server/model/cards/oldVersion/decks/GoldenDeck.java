package it.polimi.ingsw.lb10.server.model.cards.oldVersion.decks;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.lb10.server.model.cards.oldVersion.Card;
import it.polimi.ingsw.lb10.server.model.cards.oldVersion.GoldenCard;
import it.polimi.ingsw.lb10.server.model.quest.Quest;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class GoldenDeck implements Deck {

    private ArrayList<GoldenCard> cards= new ArrayList<>();
    public void shuffle(){
        Collections.shuffle(getCards());
    }

    public Card drawCard(){
        Card temp=cards.getLast();
        cards.removeLast();
        return temp;
    }

    public ArrayList<GoldenCard> getCards() {
        return cards;
    }

    public void fillDeck(){
        ObjectMapper mapper = new ObjectMapper();
        try {
            cards = mapper.readValue(new File("src/main/resources/it/polimi/ingsw/lb10/json/goldenDeck.json"),new TypeReference<ArrayList<GoldenCard>>() {});
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    @Override
    public Quest drawQuest() {
        return null;
    }
}
