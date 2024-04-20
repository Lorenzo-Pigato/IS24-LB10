package it.polimi.ingsw.lb10.server.model.cards.CardState;

import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.Card;
import it.polimi.ingsw.lb10.server.model.cards.CardState.CardState;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;

import java.util.ArrayList;
import java.util.HashMap;

public class NotFlippedCardState implements CardState {
    private Card card;

    final ArrayList<Resource> empty = new ArrayList<>() ;
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

    /**
     * @return null because it's the resource on the back of the card!
     */
    @Override
    public ArrayList<Resource> getCardResources() {
        return empty;
    }
}
