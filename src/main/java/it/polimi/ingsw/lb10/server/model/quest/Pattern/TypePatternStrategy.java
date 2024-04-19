package it.polimi.ingsw.lb10.server.model.quest.Pattern;


import it.polimi.ingsw.lb10.server.model.Matrix;

public interface TypePatternStrategy {
    boolean isPattern(Matrix matrix, int row, int column);
}
