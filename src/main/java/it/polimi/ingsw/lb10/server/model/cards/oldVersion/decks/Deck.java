package it.polimi.ingsw.lb10.server.model.cards.oldVersion.decks;

import it.polimi.ingsw.lb10.server.model.cards.oldVersion.Card;
import it.polimi.ingsw.lb10.server.model.quest.Quest;

import java.io.IOException;

public interface Deck {

    void shuffle();
    void fillDeck() throws IOException;
    Card drawCard();
    Quest drawQuest();
}
