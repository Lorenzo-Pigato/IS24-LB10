package it.polimi.ingsw.lb10.server.model.cards.CardState;

import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.Card;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;
import it.polimi.ingsw.lb10.server.model.cards.corners.CornerAvailable;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class FlippedCardState implements CardState {

    private static final ArrayList<Corner> flippedCorners= new ArrayList<>(Arrays.asList(new CornerAvailable(Position.TOPLEFT,Resource.EMPTY),new CornerAvailable(Position.TOPRIGHT,Resource.EMPTY),new CornerAvailable(Position.BOTTOMLEFT,Resource.EMPTY),new CornerAvailable(Position.BOTTOMRIGHT,Resource.EMPTY)));
    private Card card;

    public FlippedCardState(Card card){
        this.card=card;
    }

    @Override
    public ArrayList<Corner> getCorners() {
        return flippedCorners;
    }

    @Override
    public int getPoints() {
        return 0;
    }

    @Override
    public HashMap<Resource, Integer> getActivationCost() {
        return  new HashMap<>();
    }

    @Override
    public ArrayList<Resource> getCardResources(){
        return card.getResources();
    }

    @Override
    public Resource getGoldenBuffResource() {
        return Resource.NULL;
    }
}
