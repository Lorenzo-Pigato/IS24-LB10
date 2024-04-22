package it.polimi.ingsw.lb10.server.model.cards.decks;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.lb10.server.model.quest.Quest;
import it.polimi.ingsw.lb10.server.model.quest.QuestCounter;

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

        ArrayList<QuestCounter> counterQuest = new ArrayList<QuestCounter>();

        try {
            counterQuest = mapper.readValue(new File("src/main/resources/it/polimi/ingsw/lb10/json/counterQuestDeck.json"),new TypeReference<ArrayList<QuestCounter>>() {});
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
