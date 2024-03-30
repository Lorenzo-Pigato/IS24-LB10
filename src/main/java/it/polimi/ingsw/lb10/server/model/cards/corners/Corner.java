package it.polimi.ingsw.lb10.server.model.cards.corners;

import com.fasterxml.jackson.annotation.JsonAlias;


public abstract class Corner {
    private boolean available;
    private Position position;

    private int id;

    public Corner(Position position){
        this.position=position;
    }

    // --------> GETTER <--------
    @JsonAlias("position")
    public Position getPosition() {
        return position;
    }

    @JsonAlias("available")
    public boolean isAvailable() {
        return available;
    }

    public int getId() {
        return id;
    }
    // --------> SETTER <--------
    @JsonAlias("position")
    public void setPosition(Position position) {
        this.position = position;
    }

    @JsonAlias("available")
    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setId(int id) {
        this.id = id;
    }
}
