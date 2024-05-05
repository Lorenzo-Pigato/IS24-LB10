package it.polimi.ingsw.lb10.server.model.cards;

import it.polimi.ingsw.lb10.client.cli.ansi.AnsiColor;

import java.io.Serializable;

public enum Color implements Serializable {
    STARTER(AnsiColor.YELLOW),
    RED(AnsiColor.RED),
    BLUE(AnsiColor.CYAN),
    GREEN(AnsiColor.GREEN),
    PURPLE(AnsiColor.PURPLE);

    private final AnsiColor color;

    Color(AnsiColor color) {
        this.color = color;
    }

    public AnsiColor getAnsi() {
        return color;
    }
}
