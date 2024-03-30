package it.polimi.ingsw.lb10.server.model.cards.corners;


import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;

public class CornetNotAvailable extends Corner {

    public CornetNotAvailable(Position position) {
        super(position);
        setAvailable(false);
    }
}
