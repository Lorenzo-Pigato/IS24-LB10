package it.polimi.ingsw.lb10.server.model.cards.corners;

import com.fasterxml.jackson.annotation.JsonAlias;
import it.polimi.ingsw.lb10.server.model.Resource;


public abstract class Corner {
    private boolean available;
    private Position position;
    private Resource resource;
    private int id;

    public Corner(Position position,Resource resource){
        this.position=position;
        this.resource=resource;
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

    @JsonAlias("resource")
    public Resource getResource() {
        return resource;
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

    @JsonAlias("resource")
    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public void setId(int id) {
        this.id = id;
    }
}
