package it.polimi.ingsw.lb10.server.model.decks;

import it.polimi.ingsw.lb10.server.model.cards.Card;

public interface Deck {

    public void shuffle();
    public void fillDeck();
    public Card draw();

}
