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
     * @param matrix of the player
     * @param row and column are the top-left corner of the card!
     * @return true, if it's all okay,
     * Three different cases of card to check!
     */
    public boolean isPattern(Matrix matrix, int row, int column) {
        return checkPatternBodyOne(matrix, row, column) || checkPatternBodyTwo(matrix, row, column) ||  checkPatternBodyThree(matrix, row, column);
    }

    /**
     * The card from where it starts is at the top of the pattern
     *  I'm not considering the worst case, like if we want to insert in the edge of the matrix, this will be fix up in the future
     */
    public boolean checkPatternBodyOne(Matrix matrix, int row, int column){
        return checkCorner(matrix, row, column, Position.TOPLEFT, true) && checkCorner(matrix, row, column + 2, Position.TOPLEFT, true) && checkCorner(matrix, row, column + 3, Position.TOPRIGHT, false);
    }
    /**
     * The card from where it starts is in the middle of the pattern
     */
    public boolean checkPatternBodyTwo(Matrix matrix, int row, int column){
        return checkCorner(matrix, row, column, Position.TOPLEFT, true) && checkCorner(matrix, row, column -2 , Position.TOPLEFT, true) && checkCorner(matrix, row, column + 1, Position.TOPRIGHT, false);
    }
    /**
     * The card from where it starts is in the bottom parte of the pattern
     */
    public boolean checkPatternBodyThree(Matrix matrix, int row, int column){
        return checkCorner(matrix, row, column-3, Position.TOPLEFT, true) && checkCorner(matrix, row, column -1 , Position.TOPLEFT, true) && checkCorner(matrix, row, column , Position.TOPRIGHT, false);
    }

    @Override
    public int questAlgorithm(Map<Resource, Integer> onMapResources) {
        return 0;
    }
}
