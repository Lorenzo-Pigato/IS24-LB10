package it.polimi.ingsw.lb10.server.model.quest.Pattern;

import it.polimi.ingsw.lb10.server.model.Matrix;
import it.polimi.ingsw.lb10.server.model.cards.Color;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.Diagonal.BottomLeftDiagonal;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.Diagonal.TopLeftDiagonal;
import it.polimi.ingsw.lb10.server.model.quest.QuestPattern;

public class DiagonalPattern extends QuestPattern {
    private Color color;
    private TypePatternStrategy typeDiagonal;

    public DiagonalPattern(int id, int points) {
        super(id, points);
    }
    public boolean chooseAlgorithm(Matrix matrix, int row,int column) {
        setTypeDiagonal();
        return typeDiagonal.isPattern(matrix,row,column);
    }

    public void setTypeDiagonal() {
        if(isTopLeft())
            typeDiagonal = new TopLeftDiagonal(color);
        else
            typeDiagonal = new BottomLeftDiagonal(color);
    }
    public void setTypeDiagonal(TypePatternStrategy typeDiagonal) {
        this.typeDiagonal = typeDiagonal;
    }
    public Color getColor() {
        return color;
    }
}
