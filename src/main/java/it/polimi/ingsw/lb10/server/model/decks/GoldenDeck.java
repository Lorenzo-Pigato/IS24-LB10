package it.polimi.ingsw.lb10.server.model.decks;

import it.polimi.ingsw.lb10.server.model.cards.Card;

import java.util.ArrayList;

public class GoldenDeck implements Deck {

    private ArrayList<Card> cards= new ArrayList<>();
    public void shuffle(){};

    //we have to think about the problem of the empty Deck!(
    public Card draw(){
        Card temp=cards.get(cards.size()-1);
        cards.remove(cards.size()-1);
        return temp;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    //We have to write the same code of ResourceDeck, GoldenDeck will do the same things.
    public void fillDeck(){}
}
