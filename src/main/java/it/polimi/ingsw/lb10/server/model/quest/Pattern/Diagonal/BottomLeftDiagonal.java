package it.polimi.ingsw.lb10.server.model.quest.Pattern.Diagonal;

import it.polimi.ingsw.lb10.server.model.Matrix;
import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.Color;

import java.util.Map;

public class BottomLeftDiagonal extends TypeDiagonal {
    public BottomLeftDiagonal(int id, int points, Color color) {
        super(id, points, color);
    }

    /**
     * @param matrix of the player
     * @param row    and column are the top-left corner of the card!
     * @return true, if the card above the one that we placed exists, it's not used for another pattern quest and if it's of the same color of the card that it's placed
     * cornetToCheck can't be null because we pass the coordinate of the top left corner of the card
     */
    @Override
    public boolean isPattern(Matrix matrix, int row, int column) {

        if (isOfTheSameColor(matrix, row, column)) {
            addCoordinatePos(getCoordinatePositions(), row, column);

            if (isOfTheSameColor(matrix, row - 1, column + 1)) {
                addCoordinatePos(getCoordinatePositions(), row-1, column+1);

                if (isOfTheSameColor(matrix, row - 2, column + 2)){
                    addCoordinatePos(getCoordinatePositions(), row-2, column+2);
                    return true;
                }

                if (isOfTheSameColor(matrix, row + 1, column - 1)){
                    addCoordinatePos(getCoordinatePositions(), row+1, column-1);
                    return true;
                }
            } else {
                if (isOfTheSameColor(matrix, row + 1, column - 1)){
                    addCoordinatePos(getCoordinatePositions(), row+1, column-1);

                    if (isOfTheSameColor(matrix, row + 2, column - 2)){
                        addCoordinatePos(getCoordinatePositions(), row+2, column-2);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public int questAlgorithm(Map<Resource, Integer> onMapResources) {
        return 0;
    }
}
