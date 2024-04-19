package it.polimi.ingsw.lb10.server.model.quest.Pattern.Diagonal;

import it.polimi.ingsw.lb10.server.model.Matrix;
import it.polimi.ingsw.lb10.server.model.cards.Color;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.TypePatternStrategy;

import java.util.Optional;

public class BottomLeftDiagonal implements TypePatternStrategy {
    private final Color cardsColor;
    public BottomLeftDiagonal(Color cardsColor){
        this.cardsColor=cardsColor;
    }

    /**
     * @param matrix of the player
     * @param row and column are the top-left corner of the card!
     * @return true if the card above the one that we placed exists, it's not used for another pattern quest and if it's of the same color of the card that it's placed
     * cornetToCheck can't be null because we pass the coordinate of the top left corner of the card
     */
    @Override
    public boolean isPattern(Matrix matrix, int row, int column) {
        if(isOfTheSameColor(matrix,row,column))
            if(isOfTheSameColor(matrix,row+1,column-1)) {
                if (isOfTheSameColor(matrix, row+2, column-2))
                    return true;
                if(isOfTheSameColor(matrix, row - 1, column + 1))
                    return true;
            }
            else{
                if(isOfTheSameColor(matrix,row-1,column+1))
                    if(isOfTheSameColor(matrix,row-2,column-2))
                        return true;
            }
        return  false;
    }

    public boolean isOfTheSameColor(Matrix matrix, int row, int column){
        Corner cornerToCheck = null;
        if(matrix.getNode(row,column).getCorners().isEmpty())
            return false;

        for(Corner corner : matrix.getNode(row,column).getCorners())
            if(corner.getPosition().equals(Position.TOPLEFT))
                cornerToCheck=corner;

        assert cornerToCheck != null;
        return !cornerToCheck.isUsedForQuest() && cornerToCheck.getCardColor().equals(cardsColor);

    }
}
