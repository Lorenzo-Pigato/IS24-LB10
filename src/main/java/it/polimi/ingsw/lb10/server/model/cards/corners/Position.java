package it.polimi.ingsw.lb10.server.model.cards.corners;

import java.io.Serializable;

public enum Position implements Serializable {
    TOPLEFT(0, 0, 140, 80),
    TOPRIGHT(16, 0, 140, 80),
    BOTTOMRIGHT(16, 5, 140, 80),
    BOTTOMLEFT(0, 5, 140, 80),
    ;

    private final int cliColOffset;
    private final int cliRowOffset;
    private final int guiOffsetX;
    private final int guiOffsetY;

    Position(int cliColOffset, int cliRowOffset, int guiOffsetX, int guiOffsetY) {
        this.cliRowOffset = cliRowOffset;
        this.cliColOffset = cliColOffset;
        this.guiOffsetX = guiOffsetX;
        this.guiOffsetY = guiOffsetY;
    }

    public int getCliColOffset() {
        return cliColOffset;
    }

    public int getCliRowOffset() {
        return cliRowOffset;
    }

    public double getGuiOffsetX() {return guiOffsetX;}

    public double getGuiOffsetY() {return guiOffsetY;}
}
