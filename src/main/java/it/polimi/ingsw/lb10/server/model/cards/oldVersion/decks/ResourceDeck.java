package it.polimi.ingsw.lb10.server.model.cards.oldVersion.decks;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.lb10.server.model.cards.ResourceCard_v1;
import it.polimi.ingsw.lb10.server.model.cards.corners.*;
import it.polimi.ingsw.lb10.server.model.cards.oldVersion.Card;
import it.polimi.ingsw.lb10.server.model.cards.oldVersion.ResourceCard;
import it.polimi.ingsw.lb10.server.model.quest.Quest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class ResourceDeck {

    private static ArrayList<ResourceCard_v1> cards ;

    // --------> METODI <--------

    public void shuffle() {
        Collections.shuffle(getCards());
    }

    public ResourceCard_v1 drawCard() {
        ResourceCard_v1 temp=cards.getLast();
        cards.removeLast();
        return temp;
    }
    public ArrayList<ResourceCard_v1> getCards() {
        return cards;
    }

    /**
     * This method calls the json with the complete resource cards' deck
     */
    public void fillDeck(){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File("src/main/resources/resourceDeck.json");
            cards = objectMapper.readValue(file, new TypeReference<ArrayList<ResourceCard_v1>>() {});
        }catch (IOException e){

        }
    }
    public Quest drawQuest() {
        return null;
    }
}


