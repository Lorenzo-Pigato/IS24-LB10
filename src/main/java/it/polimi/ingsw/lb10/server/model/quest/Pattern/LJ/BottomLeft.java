package it.polimi.ingsw.lb10.server.model.quest.Pattern.LJ;

import it.polimi.ingsw.lb10.server.model.Matrix;
import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.Color;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;

import java.util.Map;

public class BottomLeft extends LJPattern {

    public BottomLeft(int id, int points, Color bodyColor, Color toeColor) {
        super(id, points, bodyColor, toeColor);
    }

    /**
     * The card from where it starts is at the top of the pattern
     *  I'm not considering the worst case, like if we want to insert in the edge of the matrix, this will be fix up in the future
     */
    public boolean checkPatternBodyOne(Matrix matrix, int row, int column){
        if( checkCorner(matrix, row, column, Position.TOPLEFT, true) && checkCorner(matrix, row+2, column, Position.TOPLEFT, true) && checkCorner(matrix, row+ 3, column, Position.TOPRIGHT, false) ){
            matrix.setUsedForQuest(row,column);
            matrix.setUsedForQuest(row +2,column);
            matrix.setUsedForQuest(row+3,column-1);
            return true;
        }
        return false;
    }
    /**
     * The card from where it starts is in the middle of the pattern
     */
    public boolean checkPatternBodyTwo(Matrix matrix, int row, int column){
        if( checkCorner(matrix, row, column, Position.TOPLEFT, true) && checkCorner(matrix, row - 2, column  , Position.TOPLEFT, true) && checkCorner(matrix, row + 1, column, Position.TOPRIGHT, false)){
            matrix.setUsedForQuest(row,column);
            matrix.setUsedForQuest(row - 2,column);
            matrix.setUsedForQuest(row+1,column-1);
            return true;
        }
        return false;
    }
    /**
     * The card from where it starts is in the bottom parte of the pattern
     */
    public boolean checkPatternBodyThree(Matrix matrix, int row, int column){
        if( checkCorner(matrix, row-3, column+1, Position.TOPLEFT, true) && checkCorner(matrix, row-1, column +1 , Position.TOPLEFT, true) && checkCorner(matrix, row, column , Position.TOPLEFT, false)){
            matrix.setUsedForQuest(row -3,column + 1);
            matrix.setUsedForQuest(row-1,column+1);
            matrix.setUsedForQuest(row,column);
            return true;
        }
        return false;
    }

    @Override
    public int questAlgorithm(Map<Resource, Integer> onMapResources) {
        return 0;
    }
}