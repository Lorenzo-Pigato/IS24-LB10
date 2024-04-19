package it.polimi.ingsw.lb10.server.model.quest.Pattern.Diagonal;

import it.polimi.ingsw.lb10.server.model.Matrix;
import it.polimi.ingsw.lb10.server.model.cards.Color;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.TypePatternStrategy;

public class TopLeftDiagonal implements TypePatternStrategy {
    private final Color cardsColor;
    public TopLeftDiagonal(Color cardsColor){
        this.cardsColor =cardsColor;
    }

    /**
     * this method is called after the insertion of a card, there are 3 different type of pattern to check
     * if the card it's on the top, on middle or bottom
     * @return true if the cards respect the pattern and the rules.
     */
    @Override
    public boolean isPattern(Matrix matrix, int row, int column) {

        if(isOfTheSameColor(matrix,row-1,column-1)) {
            if (isOfTheSameColor(matrix, row - 2, column - 2))
                return true;
            if(isOfTheSameColor(matrix, row + 1, column + 1))
                return true;
        }
        else{
            if(isOfTheSameColor(matrix,row+1,column+1))
                if(isOfTheSameColor(matrix,row+2,column+2))
                    return true;
        }

        return false;
    }

    /**
     * @param matrix of the player
     * @param row and column are the top-left corner of the card!
     * @return true if the card above the one that we placed exists, it's not used for another pattern quest and if it's of the same color of the card that it's placed
     * The problem is I don’t know which one to look at between the first and the second corner
     */
    public boolean isOfTheSameColor(Matrix matrix, int row, int column){

//        return Optional.ofNullable(matrix.getNode(row, column))
//                .filter(node-> !node.getCorners().isEmpty())
//                .filter(node-> !node.isUsedForQuest())
//                .map(node-> node.getCorners().getFirst().getCardColor().equals(cardsColor))
//                .orElse(false);
        if(matrix.getNode(row,column).getCorners().isEmpty())
            return false;
        if(matrix.getNode(row,column).getCorners().get(0).isUsedForQuest())
            return false;
        if(matrix.getNode(row,column).getCorners().getLast().getCardColor().equals(cardsColor) )
            return true;
        return false;
    }


}
