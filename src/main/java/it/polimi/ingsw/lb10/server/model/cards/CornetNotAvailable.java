package it.polimi.ingsw.lb10.server.model.cards;


public class CornetNotAvailable extends Corner{

    public CornetNotAvailable(Position position) {
        super(position);
        setAvailable(false);
    }
}
