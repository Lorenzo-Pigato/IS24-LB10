package it.polimi.ingsw.lb10.server.model.cards.CardState;

import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.Card;
import it.polimi.ingsw.lb10.server.model.cards.CardState.CardState;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;

import java.util.ArrayList;
import java.util.HashMap;

public class NotFlippedCardState implements CardState {
    private Card card;
    public NotFlippedCardState(Card card){
        this.card=card;
    }

    // --------> GETTER <--------

    @Override
    public ArrayList<Corner> getCorners() {
        return card.getCorners();
    }

    @Override
    public int getPoints() {
        return card.getPoints();
    }

    @Override
    public HashMap<Resource, Integer> getActivationCost() {
        return card.getActivationCost();
    }

    @Override
    public ArrayList<Resource> getCardResources() {
        return card.getResources();
    }
}
