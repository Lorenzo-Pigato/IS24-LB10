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
     * this method is called after the insertion of a card, there are three different types of a pattern to check
     * we do not know if we start from the top, bottom or middle of the pattern
     * @return true if the cards respect the pattern and the rules.
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
