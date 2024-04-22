package it.polimi.ingsw.lb10.server.model.cards.decks;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.lb10.server.model.cards.GoldenCard;
import it.polimi.ingsw.lb10.server.model.quest.Quest;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class GoldenDeck{
    private ArrayList<GoldenCard> cards= new ArrayList<>();

    public static void main(String[] args){
        GoldenDeck gd = new GoldenDeck();
        gd.fillDeck();
    }

    public void shuffle(){
        Collections.shuffle(getCards());
    }

    public GoldenCard drawCard(){
        GoldenCard temp=cards.getLast();
        cards.removeLast();
        return temp;
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
    }

    public Quest drawQuest() {
        return null;
    }
}
