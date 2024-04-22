package it.polimi.ingsw.lb10.server.model.cards.decks;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.lb10.server.model.quest.Quest;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class QuestDeck {
    private ArrayList<Quest> cards= new ArrayList<>();

    public void shuffle() {
        Collections.shuffle(getCards());
    }

    public Quest drawQuest() {
        Quest card = cards.getLast();
        cards.removeLast();
        return card;
    }

    public ArrayList<Quest> getCards() {
        return cards;
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
