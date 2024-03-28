package it.polimi.ingsw.lb10.server.model.decks;

import it.polimi.ingsw.lb10.server.model.cards.Card;

import java.io.IOException;

public interface Deck {

    public void shuffle();
    public void fillDeck() throws IOException;
    public Card draw();

}
