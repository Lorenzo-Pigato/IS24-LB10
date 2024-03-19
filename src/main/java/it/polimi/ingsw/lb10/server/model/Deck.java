package it.polimi.ingsw.lb10.server.model;
/*
*   In their respective classes will be loaded the cards at the start of the game
*   The parsing will be done by gson, library of Google
*
 */
public interface Deck {

    public void shuffle();
    public void draw();
}
