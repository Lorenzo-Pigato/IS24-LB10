package it.polimi.ingsw.lb10.server.model.cards.corners;


import it.polimi.ingsw.lb10.server.model.Resource;

public class CornerNotAvailable extends Corner {

    public CornerNotAvailable(Position position, Resource resource) {
        super(position,resource);
        setAvailable(false);
    }

    @Override
    public Resource getResource() {
        return Resource.EMPTY;
    }

    @Override
    public boolean isAvailable() {
        return false;
    }
}
