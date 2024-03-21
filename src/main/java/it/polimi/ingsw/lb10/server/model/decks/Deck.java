package it.polimi.ingsw.lb10.server.model.decks;

import it.polimi.ingsw.lb10.server.model.cards.Card;

/*
*   In their respective classes will be loaded the cards at the start of the game
*   The parsing will be done by gson, library of Google
*
 */
public interface Deck {

    public void shuffle();
    public Card draw();

}
