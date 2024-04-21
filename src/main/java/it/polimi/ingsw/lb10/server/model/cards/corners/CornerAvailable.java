package it.polimi.ingsw.lb10.server.model.cards.corners;

import com.fasterxml.jackson.annotation.JsonAlias;
import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.Color;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;

public class CornerAvailable extends Corner {


    public CornerAvailable(int id, boolean available,  Position position, Resource resource, Color cardColor) {
        super(id, available, position, resource, cardColor);
    }

    @Override
    public boolean isAvailable() {
        return true;
    }
}
