package it.polimi.ingsw.lb10.server.model.quest.Pattern.Diagonal;

import it.polimi.ingsw.lb10.server.model.Matrix;
import it.polimi.ingsw.lb10.server.model.cards.Color;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.TypePatternStrategy;

import java.util.Optional;

public class BottomLeftDiagonal implements TypePatternStrategy {
    private final Color cardsColor;
    public BottomLeftDiagonal(Color cardsColor){
        this.cardsColor=cardsColor;
    }

    @Override
    public boolean isPattern(Matrix matrix, int row, int column) {
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

        return Optional.ofNullable(matrix.getNode(row, column))
                .filter(node-> !node.getCorners().isEmpty())
                .filter(node-> !node.getCorners().get(0).isUsedForQuest())
                .map(node-> node.getCorners().getLast().getCardColor().equals(cardsColor))
                .orElse(false);
    }
}
