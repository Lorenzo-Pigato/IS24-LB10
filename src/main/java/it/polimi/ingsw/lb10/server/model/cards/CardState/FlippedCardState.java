package it.polimi.ingsw.lb10.server.model.cards.CardState;

import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;
import it.polimi.ingsw.lb10.server.model.cards.corners.CornerAvailable;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class FlippedCardState implements CardState {

    private static ArrayList<Corner> flippedCorners= new ArrayList<>(Arrays.asList(new CornerAvailable(Position.TOPLEFT,Resource.NULL),new CornerAvailable(Position.TOPRIGHT,Resource.NULL),new CornerAvailable(Position.BOTTOMLEFT,Resource.NULL),new CornerAvailable(Position.BOTTOMRIGHT,Resource.NULL)));
    private ArrayList<Resource> middleResource;
    public FlippedCardState(int id,ArrayList<Resource> middleResource){
        setFlippedCorners(flippedCorners,id);
        this.middleResource=middleResource;
    }

    public void setFlippedCorners(ArrayList<Corner> corners,int id) {
        for(Corner corner : corners){
            corner.setResource(Resource.NULL);
            corner.setId(id);
        }
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
        return null;
    }

    @Override
    public ArrayList<Resource> getCardResources(){
        return middleResource;
    }
}
