package it.polimi.ingsw.lb10.server.model.quest.Pattern.LJ;

import it.polimi.ingsw.lb10.server.model.Matrix;
import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.Color;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;

import java.util.Map;

public class TopLeft extends LJPattern {
    public TopLeft(int id, int points, Color bodyColor, Color toeColor) {
        super(id, points, bodyColor, toeColor);
    }

    /**
            * Starting from the first card of the body,
     *  like if we want to insert in the edge of the matrix, this will be fix up in the future
     */
    public boolean checkPatternBodyOne(Matrix matrix, int row, int column){
        if( checkCorner(matrix, row, column, Position.TOPLEFT, true) && checkCorner(matrix, row, column+2 , Position.TOPLEFT, true) && checkCorner(matrix, row, column, Position.BOTTOMRIGHT, false)){
            matrix.setUsedForQuest(row,column);
            matrix.setUsedForQuest(row,column+2);
            matrix.setUsedForQuest(row-1,column-1);
            return true;
        }
        return false;
    }
    /**
            * Starting from the second card of the body
     */
    public boolean checkPatternBodyTwo(Matrix matrix, int row, int column){
        if( checkCorner(matrix, row, column-2, Position.TOPLEFT, true) && checkCorner(matrix, row, column , Position.TOPLEFT, true) && checkCorner(matrix, row, column-2 , Position.BOTTOMRIGHT, false)){
            matrix.setUsedForQuest(row,column-2);
            matrix.setUsedForQuest(row,column);
            matrix.setUsedForQuest(row-1,column-3);
            return true;
        }
        return false;
    }
    /**
     * Starting from the toe
     */
    public boolean checkPatternBodyThree(Matrix matrix, int row, int column){
        if( checkCorner(matrix, row+1, column+1, Position.TOPLEFT, true) && checkCorner(matrix, row+1, column+3 , Position.TOPLEFT, true) && checkCorner(matrix, row, column , Position.TOPLEFT, false)){
            matrix.setUsedForQuest(row+1,column+1);
            matrix.setUsedForQuest(row+1,column+3);
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