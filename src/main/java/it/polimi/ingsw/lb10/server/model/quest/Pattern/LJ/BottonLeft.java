package it.polimi.ingsw.lb10.server.model.quest.Pattern.LJ;

import it.polimi.ingsw.lb10.server.model.Matrix;
import it.polimi.ingsw.lb10.server.model.cards.Color;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.TypePatternStrategy;

public class BottonLeft implements TypePatternStrategy {
    private final Color bodyColor;
    private final Color toeColor;
    public BottonLeft(Color bodyColor,Color toeColor){
        this.bodyColor=bodyColor;
        this.toeColor=toeColor;
    }

    /**
     * @param matrix of the player
     * @param row and column are the top-left corner of the card!
     * @return true if it's all okay
     * Three different case of card to check!
     */

    @Override
    public boolean isPattern(Matrix matrix, int row, int column) {

        return false;
    }

    public boolean checkPatternBodyOne(Matrix matrix, int row, int column){
       if(checkCorner(matrix,row,column,Position.TOPLEFT) && checkCorner(matrix,row,column,Position.TOPLEFT) && checkCorner(matrix,row,column,Position.TOPRIGHT) )
           return true;

        return false;
    }

    public boolean checkCorner(Matrix matrix, int row,int column,Position position){
        Corner cornerToCheck = null;

        for(Corner corner : matrix.getNode(row,column).getCorners())
            if(corner.getPosition().equals(position))
                cornerToCheck=corner;

        assert cornerToCheck != null;
        return !cornerToCheck.isUsedForQuest() && cornerToCheck.getCardColor().equals(bodyColor);
    }



}
