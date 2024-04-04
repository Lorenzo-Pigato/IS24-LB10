package it.polimi.ingsw.lb10.server.model.cards;

import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;
import it.polimi.ingsw.lb10.server.model.cards.corners.CornerAvailable;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;

import java.util.ArrayList;

public class FlippedCardState implements CardState{

    private ArrayList<Corner> flippedCorners= new ArrayList<>();
    public FlippedCardState(int id){
        initializeFlippedCorner(getFlippedCorners());
        setFlippedCorners(flippedCorners,id);
    }

    public ArrayList<Corner> getFlippedCorners() {
        return flippedCorners;
    }

    public void setFlippedCorners(ArrayList<Corner> corners,int id) {
        for(Corner corner : corners){
            corner.setResource(Resource.NULL);
            corner.setId(id);
        }
    }
    public void initializeFlippedCorner(ArrayList<Corner> corners){
        corners.add(new CornerAvailable(Position.TOPLEFT,Resource.NULL));
        corners.add(new CornerAvailable(Position.TOPRIGHT,Resource.NULL));
        corners.add(new CornerAvailable(Position.BOTTOMLEFT,Resource.NULL));
        corners.add(new CornerAvailable(Position.BOTTOMRIGHT,Resource.NULL));
    }

    @Override
    public ArrayList<Corner> getCorners() {
        return flippedCorners;
    }

    @Override
    public int getPoints() {
        return 0;
    }
}
