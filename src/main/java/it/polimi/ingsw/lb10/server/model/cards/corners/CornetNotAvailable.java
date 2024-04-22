package it.polimi.ingsw.lb10.server.model.cards.corners;


import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;

public class CornetNotAvailable extends Corner {

    public CornetNotAvailable(Position position,Resource resource) {
        super(position,resource);
        setAvailable(false);
    }

    @Override
    public Resource getResource() {
        return Resource.NULL;
    }

    @Override
    public boolean isAvailable() {
        return false;
    }
}