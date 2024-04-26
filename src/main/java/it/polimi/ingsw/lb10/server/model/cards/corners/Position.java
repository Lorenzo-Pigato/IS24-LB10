package it.polimi.ingsw.lb10.server.model.cards.corners;

public enum Position {
    TOPLEFT(0,0),
    TOPRIGHT(16,0),
    BOTTOMRIGHT(16,5),
    BOTTOMLEFT(0,5),;

    private final int cliColOffset;
    private final int cliRowOffset;

    Position(int cliColOffset, int cliRowOffset){
        this.cliRowOffset = cliRowOffset;
        this.cliColOffset = cliColOffset;
    }

    public int getCliColOffset(){
        return cliColOffset;
    }

    public int getCliRowOffset() {
        return cliRowOffset;
    }
}
