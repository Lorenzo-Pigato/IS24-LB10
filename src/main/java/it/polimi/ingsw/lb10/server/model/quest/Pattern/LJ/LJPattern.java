package it.polimi.ingsw.lb10.server.model.quest.Pattern.LJ;

import it.polimi.ingsw.lb10.server.model.Matrix;
import it.polimi.ingsw.lb10.server.model.cards.Color;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;
import it.polimi.ingsw.lb10.server.model.quest.Quest;

public abstract class LJPattern extends Quest {
    private final Color bodyColor;
    private final Color toeColor;

    public LJPattern(int id, int points, Color bodyColor, Color toeColor) {
        super(id, points);

        this.bodyColor=bodyColor;
        this.toeColor=toeColor;
    }

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
