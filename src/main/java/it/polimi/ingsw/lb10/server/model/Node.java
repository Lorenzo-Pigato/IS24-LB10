package it.polimi.ingsw.lb10.server.model;

import it.polimi.ingsw.lb10.server.model.cards.Card;

import java.util.ArrayList;

/**
 * It's the node of the Matrix! It may contain 2 cards, every node is a corner(free/occupied) of the card!
 */
public class Node {
    private ArrayList<Card> cards;
    private boolean available;
    public Node(){
        cards=new ArrayList<>();
        available=false;
    }

    public void addNode(Card card){
        cards.add(card);
    }
    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
    public boolean isAvailable() {
        return available;
    }
}
