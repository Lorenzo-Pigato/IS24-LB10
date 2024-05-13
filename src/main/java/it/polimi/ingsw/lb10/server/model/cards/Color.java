package it.polimi.ingsw.lb10.server.model.cards;

import it.polimi.ingsw.lb10.client.cli.ansi.AnsiColor;

import java.io.Serializable;

public enum Color implements Serializable {
    STARTER(AnsiColor.YELLOW, "yellow"),
    RED(AnsiColor.RED, "red"),
    BLUE(AnsiColor.CYAN, "cyan"),
    GREEN(AnsiColor.GREEN, "green"),
    PURPLE(AnsiColor.PURPLE, "purple");

    private final AnsiColor color;
    private final String cssString;

    Color(AnsiColor color, String cssString) {
        this.color = color;this.cssString = cssString;
    }

    public AnsiColor getAnsi() {
        return color;
    }

    public String getCssString() {return cssString;}
}
