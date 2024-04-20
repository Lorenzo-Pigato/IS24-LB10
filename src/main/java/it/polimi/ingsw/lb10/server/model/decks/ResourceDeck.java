package it.polimi.ingsw.lb10.server.model.decks;

import it.polimi.ingsw.lb10.server.model.cards.*;
import it.polimi.ingsw.lb10.server.model.cards.corners.*;
import it.polimi.ingsw.lb10.server.model.quest.Quest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class ResourceDeck implements Deck {

    ArrayList<ResourceCard> cards= new ArrayList<>();
    ArrayList<Corner> corners= new ArrayList<>();


    // --------> METODI <--------

    public void shuffle() {
        Collections.shuffle(getCards());
    }

    public Card drawCard() {
        Card temp=cards.getLast();
        cards.removeLast();
        return temp;
    }
    public ArrayList<ResourceCard> getCards() {
        return cards;
    }

    /**
     * This method calls the json with the complete resource cards' deck
     */
    public void fillDeck() throws IOException {


    }
    @Override
    public Quest drawQuest() {
        return null;
    }
}


