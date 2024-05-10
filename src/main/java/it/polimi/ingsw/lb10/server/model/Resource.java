package it.polimi.ingsw.lb10.server.model;

import it.polimi.ingsw.lb10.client.cli.ansi.AnsiColor;

import java.io.Serial;
import java.io.Serializable;

public enum Resource implements Serializable {

    NULL(null, null, null),
    PATTERN(AnsiColor.YELLOW, "▛", "Pattern"),

    EMPTY(AnsiColor.YELLOW, null, "Empty"),
    MUSHROOM(AnsiColor.RED, null, "Mushroom"),
    ANIMAL(AnsiColor.CYAN, null, "Animal"),
    INSECT(AnsiColor.PURPLE, null, "Insect"),
    PLANT(AnsiColor.GREEN, null, "Plant"),
    FEATHER(AnsiColor.YELLOW, "F", "Feather"),
    POTION(AnsiColor.YELLOW, "P", "Potion"),
    PERGAMENA(AnsiColor.YELLOW, "S", "Pergamena"),;

    final private AnsiColor color;
    final private String letter;
    final private String stringName;
    @Serial
    private static final long serialVersionUID = 38L;

    Resource(AnsiColor color, String string, String stringName) {
        this.color = color;
        this.letter = string;
        this.stringName = stringName;
    }

    public AnsiColor getColor() {
        return color;
    }

    public String getLetter() {
        return letter;
    }

    public String getResourceString() {return stringName;}
}
