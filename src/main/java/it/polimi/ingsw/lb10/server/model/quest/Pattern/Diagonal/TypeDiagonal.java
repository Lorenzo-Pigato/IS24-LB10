package it.polimi.ingsw.lb10.server.model.quest.Pattern.Diagonal;

import it.polimi.ingsw.lb10.server.model.Matrix;
import it.polimi.ingsw.lb10.server.model.cards.Color;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;
import it.polimi.ingsw.lb10.server.model.quest.Quest;

public abstract class TypeDiagonal extends Quest {
    private Color color;
    public TypeDiagonal(int id, int points, Color color) {
        super(id, points);
        this.color=color;
    }

    public abstract boolean isPattern(Matrix matrix, int row, int column);
    /**
     * @param matrix of the player
     * @param row and column are the top-left corner of the card!
     * @return true,
     * if the card above the one that we placed exists,
     * it's not used for another pattern quest and if it's of the same color of the card that it's placed
     * cornetToCheck can't be null because we pass the coordinate of the top left corner of the card
     */
    public boolean isOfTheSameColor(Matrix matrix, int row, int column){
        Corner cornerToCheck = null;
        if(matrix.getNode(row,column).getCorners().isEmpty())
            return false;

        for(Corner corner : matrix.getNode(row,column).getCorners())
            if(corner.getPosition().equals(Position.TOPLEFT))
                cornerToCheck=corner;

        assert cornerToCheck != null;
        return !cornerToCheck.isUsedForQuest() && cornerToCheck.getCardColor().equals(color);

    }


}
