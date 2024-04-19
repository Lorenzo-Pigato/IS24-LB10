package it.polimi.ingsw.lb10.server.model.quest.Pattern;

import it.polimi.ingsw.lb10.server.model.Matrix;
import it.polimi.ingsw.lb10.server.model.cards.Color;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.Diagonal.BottomLeftDiagonal;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.Diagonal.TopLeftDiagonal;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.LJ.BottonLeft;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.LJ.BottonRight;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.LJ.TopLeft;
import it.polimi.ingsw.lb10.server.model.quest.QuestPattern;

public class LJPattern extends QuestPattern {
    private final boolean coveringTop;
    private final boolean coveringLeft;
    private final Color bodyColor;
    private final Color toeColor;
    private TypePatternStrategy typeLJ;
    public LJPattern(int id, int points, boolean coveringLeft, boolean coveringTop,Color bodyColor,Color toeColor) {
        super(id, points);
        this.coveringLeft=coveringLeft;
        this.coveringTop=coveringTop;
        this.bodyColor=bodyColor;
        this.toeColor=toeColor;
    }

    public boolean chooseAlgorithm(Matrix matrix, int row, int column) {
        setTypeDiagonal();
        return typeLJ.isPattern(matrix,row,column);
    }

    public void setTypeDiagonal() {
        if(isCoveringTop())
            if (isCoveringLeft())
                typeLJ= new TopLeft();
            else
                typeLJ= new TopLeft();
        else
            if(isCoveringLeft())
                typeLJ=new BottonLeft(bodyColor,toeColor);
            else
                typeLJ=new BottonRight();
    }
    // --------> GETTER <--------
    public Color getBodyColor() {
        return bodyColor;
    }
    public Color getToeColor() {
        return toeColor;
    }
    public boolean isCoveringLeft() {
        return coveringLeft;
    }
    public boolean isCoveringTop() {
        return coveringTop;
    }
}
