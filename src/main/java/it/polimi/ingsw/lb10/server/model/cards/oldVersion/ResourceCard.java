package it.polimi.ingsw.lb10.server.model.cards.oldVersion;

import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.CardState.CardState;
import it.polimi.ingsw.lb10.server.model.cards.CardState.FlippedCardState;
import it.polimi.ingsw.lb10.server.model.cards.CardState.NotFlippedCardState;
import it.polimi.ingsw.lb10.server.model.cards.Color;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;
import it.polimi.ingsw.lb10.server.model.cards.oldVersion.Card;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *     same reasoning as StartingCard, we need to define the "color" of the card
 *     to define the flipped card, should the method be here or in the controller?--> Controller, but to finish the work we need the json.
 */

public class ResourceCard extends Card {
    private CardState cardState;

    public ResourceCard(int id, int points, ArrayList<Corner> corners, Color color, ArrayList<Resource> resources){
        this.setId(id);
        this.setPoints(points);
        this.setCorners(corners);
        this.setColor(color);
        this.setResources(resources);

        setNotFlippedState();
    }

    @Override
    public void swapState(){
        if (getCardState() instanceof FlippedCardState)
            setNotFlippedState();
        else
            setFlippedState();
    }

    // --------> SETTER <--------

    @Override
    public void setFlippedState() {
        cardState = new FlippedCardState(this);
    }

    public void setNotFlippedState() {
        cardState = new NotFlippedCardState(this);
    }
    // --------> GETTER <--------

    public CardState getCardState() {
        return cardState;
    }

    @Override
    public ArrayList<Corner> getStateCardCorners() {
        return getCardState().getCorners();
    }

    @Override
    public int getStateCardPoints(){
        return getCardState().getPoints();
    }

    @Override
    public ArrayList<Resource> getStateCardResources(){return getCardState().getCardResources();}

    @Override
    public HashMap<Resource, Integer> getStateCardActivationCost() {
        return new HashMap<>();
    }
    @Override
    public Resource getStateCardGoldenBuffResource() {
        return Resource.NULL;
    }
}
