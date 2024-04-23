package it.polimi.ingsw.lb10.server.model.cards.decks;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.lb10.server.model.cards.GoldenCard;
import it.polimi.ingsw.lb10.server.model.quest.Quest;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

public class GoldenDeck{
    private ArrayList<GoldenCard> cards= new ArrayList<>();

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
        ObjectMapper mapper = new ObjectMapper();
        try {
            cards = mapper.readValue(new File("src/main/resources/goldenDeck.json"),new TypeReference<ArrayList<GoldenCard>>() {});
        } catch (Exception e) {
            System.out.println(e);
        }
        //shuffle();
    }

    public Quest drawQuest() {
        return null;
    }
}
