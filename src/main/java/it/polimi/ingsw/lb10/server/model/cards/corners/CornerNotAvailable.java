package it.polimi.ingsw.lb10.server.model.cards.corners;


import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.Color;

public class CornerNotAvailable extends Corner {


    public CornerNotAvailable(int id, boolean available, boolean usedForQuest, Position position, Resource resource, Color cardColor) {
        super(id, available, usedForQuest, position, resource, cardColor);
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
