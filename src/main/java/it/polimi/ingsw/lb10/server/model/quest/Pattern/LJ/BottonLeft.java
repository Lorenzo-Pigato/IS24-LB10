package it.polimi.ingsw.lb10.server.model.quest.Pattern.LJ;

import it.polimi.ingsw.lb10.server.model.Matrix;
import it.polimi.ingsw.lb10.server.model.cards.Color;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.LJPattern;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.TypePatternStrategy;

public class BottonLeft extends LJPattern {

    public BottonLeft(int id, int points, Color bodyColor, Color toeColor) {
        super(id, points, bodyColor, toeColor);

    }
    /**
     * @param matrix of the player
     * @param row and column are the top-left corner of the card!
     * @return true if it's all okay
     * Three different case of card to check!
     */
    public boolean isPattern(Matrix matrix, int row, int column) {
        return checkPatternBodyOne(matrix, row, column) || checkPatternBodyTwo(matrix, row, column) ||  checkPatternBodyThree(matrix, row, column);
    }

    /**
     * The card  from where it starts is at the top of the pattern
     *  I'm not considering the worst case, like if we want to insert in the edge of the matrix, this will be fix up in the future
     */
    public boolean checkPatternBodyOne(Matrix matrix, int row, int column){
        return checkCorner(matrix, row, column, Position.TOPLEFT, true) && checkCorner(matrix, row, column + 2, Position.TOPLEFT, true) && checkCorner(matrix, row, column + 3, Position.TOPRIGHT, false);
    }
    /**
     * The card  from where it starts is in middle of the pattern
     */
    public boolean checkPatternBodyTwo(Matrix matrix, int row, int column){
        return checkCorner(matrix, row, column, Position.TOPLEFT, true) && checkCorner(matrix, row, column -2 , Position.TOPLEFT, true) && checkCorner(matrix, row, column + 1, Position.TOPRIGHT, false);
    }
    /**
     * The card  from where it starts is in the bottom parte of the pattern
     */
    public boolean checkPatternBodyThree(Matrix matrix, int row, int column){
        return checkCorner(matrix, row, column-3, Position.TOPLEFT, true) && checkCorner(matrix, row, column -1 , Position.TOPLEFT, true) && checkCorner(matrix, row, column , Position.TOPRIGHT, false);
    }

    public boolean checkCorner(Matrix matrix, int row,int column,Position position,boolean bodyOrToe){
        Corner cornerToCheck = null;

        for(Corner corner : matrix.getNode(row,column).getCorners())
            if(corner.getPosition().equals(position))
                cornerToCheck=corner;

        assert cornerToCheck != null;
        if(!cornerToCheck.isUsedForQuest()){
            return checkColor(cornerToCheck, bodyOrToe);
        }
        return false;
    }

    public boolean checkColor(Corner cornerToCheck,boolean bodyOrToe){
        if(bodyOrToe)
            return cornerToCheck.getCardColor().equals(getBodyColor());
        else
            return cornerToCheck.getCardColor().equals(getToeColor());
    }



}
