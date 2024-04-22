package it.polimi.ingsw.lb10.server.model.cards.CardState;

import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.oldVersion.Card;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;

import java.util.ArrayList;
import java.util.HashMap;

public class NotFlippedStartingCardState implements CardState{
    private Card card;
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
        return new HashMap<>();
    }
    @Override
    public ArrayList<Resource> getCardResources() {
        return card.getResources();
    }
    @Override
    public Resource getGoldenBuffResource() {
        return Resource.NULL;
    }
}