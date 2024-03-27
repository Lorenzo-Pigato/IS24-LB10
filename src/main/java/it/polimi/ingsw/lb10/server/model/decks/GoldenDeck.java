package it.polimi.ingsw.lb10.server.model.decks;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.lb10.server.model.cards.Card;
import it.polimi.ingsw.lb10.server.model.cards.GoldenCard;
import it.polimi.ingsw.lb10.server.model.cards.ResourceCard;

import java.io.File;
import java.util.ArrayList;

public class GoldenDeck implements Deck {

    private ArrayList<GoldenCard> cards= new ArrayList<>();
    public void shuffle(){};

    public Card draw(){
        Card temp=cards.get(cards.size()-1);
        cards.remove(cards.size()-1);
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
}
