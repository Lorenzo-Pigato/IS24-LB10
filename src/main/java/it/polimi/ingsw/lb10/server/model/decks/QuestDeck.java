package it.polimi.ingsw.lb10.server.model.decks;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.lb10.server.model.quest.Quest;
import it.polimi.ingsw.lb10.server.model.cards.Card;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class QuestDeck implements Deck {
    private ArrayList<Quest> cards= new ArrayList<>();
    @Override
    public void shuffle() {
//        Collections.shuffle(getCards());
    }

    @Override
    public Card draw() {
        return null;
        }
    public void fillDeck(){
        ObjectMapper mapper = new ObjectMapper();
        try {
            cards = mapper.readValue(new File("src/main/resources/it/polimi/ingsw/lb10/json/questDeck.json"),new TypeReference<ArrayList<Quest>>() {});
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
