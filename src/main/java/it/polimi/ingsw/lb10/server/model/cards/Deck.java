package it.polimi.ingsw.lb10.server.model.cards;

import java.util.ArrayList;
import java.util.Collections;

public class Deck<T> {
    private ArrayList<T> cards = new ArrayList<>();
    public void shuffle() {
        Collections.shuffle(cards);
    }

    public T drawCard() {
        if (!cards.isEmpty()) {
            T temp = cards.removeLast();
            return temp;
        } else
            return null;
    }

    public void addCard(T card) {
        cards.add(card);
    }


    public ArrayList<T> getCards() {
        return cards;
    }

    public void fillDeck() {

    }

}