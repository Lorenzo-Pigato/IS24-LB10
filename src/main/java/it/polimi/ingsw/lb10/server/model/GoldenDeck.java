package it.polimi.ingsw.lb10.server.model;

import java.util.ArrayList;

public class GoldenDeck implements Deck{

    private ArrayList<Card> cards= new ArrayList<>();
    public void shuffle(){};
    public void draw(){};

    public ArrayList<Card> getCards() {
        return cards;
    }
}
