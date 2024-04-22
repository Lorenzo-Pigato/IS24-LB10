package it.polimi.ingsw.lb10.server.model.quest.Pattern.LJ;

import it.polimi.ingsw.lb10.server.model.Matrix;
import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.Color;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;
import it.polimi.ingsw.lb10.server.model.quest.Quest;

import java.util.Map;

public  abstract class LJPattern extends Quest {
    private final Color bodyColor;
    private final Color toeColor;

    public LJPattern(int id, int points, Color bodyColor, Color toeColor) {
        super(id, points);

        this.bodyColor=bodyColor;
        this.toeColor=toeColor;
    }

    public abstract boolean checkPatternBodyOne(Matrix matrix, int row, int column);
    public abstract boolean checkPatternBodyTwo(Matrix matrix, int row, int column);
    public abstract boolean checkPatternBodyThree(Matrix matrix, int row, int column);
    public boolean isPattern(Matrix matrix, int row, int column) {
        return checkPatternBodyOne(matrix, row, column) || checkPatternBodyTwo(matrix, row, column) ||  checkPatternBodyThree(matrix, row, column);
    }

    /**
     * @param matrix of the player
     * @param row and column of our interest
     * @param position that we want to control
     * @param bodyOrToe is true if we want to know the body colors otherwise it's the toe
     * @return true if the card has the same color of the card that we want to check, and it isn't used for another quest
     */
    public boolean checkCorner(Matrix matrix, int row, int column, Position position, boolean bodyOrToe){
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

    // --------> GETTER <--------
    public Color getBodyColor() {
        return bodyColor;
    }
    public Color getToeColor() {
        return toeColor;
    }
}
