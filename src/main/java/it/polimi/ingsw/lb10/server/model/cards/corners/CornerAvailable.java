package it.polimi.ingsw.lb10.server.model.cards.corners;

import com.fasterxml.jackson.annotation.JsonAlias;
import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;

public class CornerAvailable extends Corner {

    public CornerAvailable(Position position) {
        super(position);
        setAvailable(true);
    }
    @Override
    public boolean isAvailable() {
        return true;
    }
}
