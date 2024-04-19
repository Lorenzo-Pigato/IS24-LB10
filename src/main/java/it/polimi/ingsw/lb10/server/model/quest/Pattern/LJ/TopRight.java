package it.polimi.ingsw.lb10.server.model.quest.Pattern.LJ;

import it.polimi.ingsw.lb10.server.model.Matrix;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.TypePatternStrategy;

public class TopRight implements TypePatternStrategy {
    @Override
    public boolean isPattern(Matrix matrix, int row, int column) {
        return false;
    }
}
