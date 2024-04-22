package it.polimi.ingsw.lb10.server.model.quest;

import it.polimi.ingsw.lb10.server.model.Matrix;
import it.polimi.ingsw.lb10.server.model.Resource;

import java.util.Map;

public abstract class Quest {
    private int id;
    private int points;

    public Quest(int id, int points){
        this.id=id;
        this.points=points;
    }

    public abstract int questAlgorithm(Map<Resource, Integer> onMapResources);
    public abstract boolean isPattern(Matrix matrix, int row, int column);

    // --------> GETTER <--------
    public int getPoints() {
        return points;
    }

    public int getId() {
        return id;
    }

    // --------> SETTER <--------
    public void setPoints(int points) {
        this.points=points;
    }

    public void setId(int id) {
        this.id=id;
    }
}
