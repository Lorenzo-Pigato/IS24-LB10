package it.polimi.ingsw.lb10.server.model;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiColor;

import java.io.Serializable;

public enum Resource implements Serializable {
    NULL(null, null),
    PATTERN(AnsiColor.YELLOW, "▛"),

    EMPTY(AnsiColor.GREY, null),
    MUSHROOM(AnsiColor.RED, null),
    ANIMAL(AnsiColor.CYAN, null),
    INSECT(AnsiColor.PURPLE, null),
    PLANT(AnsiColor.GREEN, null),
    FEATHER(AnsiColor.YELLOW, "F"),
    POTION(AnsiColor.YELLOW ,"P"),
    PERGAMENA(AnsiColor.YELLOW, "S");

    final private AnsiColor color;
    final private String letter;

    Resource (AnsiColor color, String string){
        this.color = color;
        this.letter = string;
    }

    public AnsiColor getColor() {
        return color;
    }

    public String getLetter() {
        return letter;
    }
}
