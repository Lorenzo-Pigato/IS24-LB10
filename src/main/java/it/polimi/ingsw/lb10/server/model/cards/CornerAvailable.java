package it.polimi.ingsw.lb10.server.model.cards;

import com.fasterxml.jackson.annotation.JsonAlias;
import it.polimi.ingsw.lb10.server.model.Resource;

public class CornerAvailable extends Corner{
    private Resource resource;

    public CornerAvailable(Position position) {
        super(position);
        setAvailable(true);
    }
    @JsonAlias("resource")
    public Resource getResource() {
        return resource;
    }
    @JsonAlias("resource")
    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
