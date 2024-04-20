package it.polimi.ingsw.lb10.server.model.cards.CardState;

import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.Card;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;

import java.util.ArrayList;
import java.util.HashMap;

public class NotFlippedStartingCardState implements CardState{
    private Card card;
    private HashMap<Resource,Integer> empty= new HashMap<>();
    public NotFlippedStartingCardState(Card card){
        this.card=card;
    }
    @Override
    public ArrayList<Corner> getCorners() {
        return card.getCorners();
    }

    @Override
    public int getPoints() {
        return 0;
    }

    @Override
    public HashMap<Resource, Integer> getActivationCost() {
        return empty;
    }

    @Override
    public ArrayList<Resource> getCardResources() {
        return card.getResources();
    }
}
