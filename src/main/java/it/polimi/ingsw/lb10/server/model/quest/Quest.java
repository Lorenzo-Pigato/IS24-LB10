package it.polimi.ingsw.lb10.server.model.quest;

public abstract class Quest {
    private int id;
    private int points;

    public Quest(int id, int points){
        this.id=id;
        this.points=points;
    }

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
