package it.polimi.ingsw.lb10.server.model.cards;

import com.fasterxml.jackson.annotation.JsonAlias;
import it.polimi.ingsw.lb10.server.model.Resource;
import java.util.ArrayList;


public abstract class Corner {
    private boolean available;
    private Position position;

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

    // --------> SETTER <--------
    @JsonAlias("position")
    public void setPosition(Position position) {
        this.position = position;
    }

    @JsonAlias("available")
    public void setAvailable(boolean available) {
        this.available = available;
    }

}
