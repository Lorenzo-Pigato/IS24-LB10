package it.polimi.ingsw.lb10.server.model.cards;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.PlaceableCardState.FrontOfTheCard;
import it.polimi.ingsw.lb10.server.model.cards.StartingCardState.BackStartingCard;
import it.polimi.ingsw.lb10.server.model.cards.StartingCardState.FrontStartingCard;
import it.polimi.ingsw.lb10.server.model.cards.StartingCardState.StateStartingCard;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;

import java.util.ArrayList;

public class StartingCard extends BaseCard {

    private ArrayList<Corner> flippedCardCorners;
    private ArrayList<Resource> middleResources;
    @JsonIgnore
    private StateStartingCard stateStartingCard;

    @JsonCreator
    public StartingCard(@JsonProperty("id") int id,@JsonProperty("color") Color colorCard, @JsonProperty("corners") ArrayList<Corner> corners, @JsonProperty("flippedCardCorners") ArrayList<Corner> flippedCardCorners, @JsonProperty("middleResource") ArrayList<Resource> middleResources) {
        super(id, colorCard, corners);
        this.flippedCardCorners = flippedCardCorners;
        this.middleResources = middleResources;
        this.stateStartingCard = new FrontStartingCard(this);
    }

    public void swapState(){
        if (getStateStartingCard() instanceof FrontOfTheCard)
            setNotFlippedState();
        else
            setFlippedState();
    }

    public ArrayList<Corner> getStateCardCorners() {
        return getStateStartingCard().getCorners();
    }
    public ArrayList<Resource> getStateCardResources(){
        return getStateStartingCard().getMiddleResources();
    }
    // --------> SETTER <--------
    public void setFlippedState() {
        stateStartingCard = new FrontStartingCard(this);
    }
    public void setNotFlippedState() {
        stateStartingCard = new BackStartingCard(this);
    }
    public void setMiddleResources(ArrayList<Resource> middleResources) {
        this.middleResources = middleResources;
    }
    public void setFlippedCardCorners(ArrayList<Corner> flippedCardCorners) {
        this.flippedCardCorners = flippedCardCorners;
    }
    // --------> GETTER <--------

    public ArrayList<Corner> getFlippedCardCorners() {
        return flippedCardCorners;
    }
    public ArrayList<Resource> getMiddleResources() {
        return middleResources;
    }
    public StateStartingCard getStateStartingCard() {
        return stateStartingCard;
    }

}