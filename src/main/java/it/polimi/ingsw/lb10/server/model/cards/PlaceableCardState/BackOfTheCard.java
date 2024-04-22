package it.polimi.ingsw.lb10.server.model.cards.PlaceableCardState;

import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.PlaceableCard;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class BackOfTheCard implements StateOfTheCard{
    private static PlaceableCard placeableCard;
    private ArrayList<Corner> flippedCorners = new ArrayList<>();
    public BackOfTheCard(PlaceableCard placeableCard){
        BackOfTheCard.placeableCard =placeableCard;

         flippedCorners= new ArrayList<>(Arrays.asList(new Corner(placeableCard.getId(),true,false,Position.TOPLEFT,Resource.EMPTY,placeableCard.getColorCard()),
                new Corner(placeableCard.getId(),true,false,Position.TOPLEFT,Resource.EMPTY,placeableCard.getColorCard()),
                new Corner(placeableCard.getId(),true,false,Position.TOPLEFT,Resource.EMPTY,placeableCard.getColorCard()),
                new Corner(placeableCard.getId(),true,false,Position.TOPLEFT,Resource.EMPTY,placeableCard.getColorCard())));
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
    public Resource getMiddleResource() {
        return placeableCard.getResource();
    }

    @Override
    public Resource goldenBuffResource() {
        return Resource.NULL;
    }

    @Override
    public HashMap<Resource, Integer> activationCost() {
        return new HashMap<>();
    }
}
