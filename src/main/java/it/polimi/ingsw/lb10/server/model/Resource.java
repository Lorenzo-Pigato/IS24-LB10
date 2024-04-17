package it.polimi.ingsw.lb10.server.model;

import it.polimi.ingsw.lb10.client.cli.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiString;

public enum Resource {
    NULL(AnsiColor.DEFAULT, null),
    MUSHROOM(AnsiColor.RED, null),
    ANIMAL(AnsiColor.BLUE, null),
    INSECT(AnsiColor.PURPLE, null),
    PLANT(AnsiColor.GREEN, null),
    FEATHER(AnsiColor.YELLOW, "F"),
    POTION(AnsiColor.YELLOW ,"P"),
    PERGAMENA(AnsiColor.YELLOW, "S");

    final private AnsiColor color;
    final private String string;

    Resource (AnsiColor color, String string){
        this.color = color;
        this.string = string;
    }


//  If the json works without these getter and setter then delete

//    final private int id;
//    final private String typeString;
//    Resource (String type, int id){
//        this.typeString = type;
//        this.id = id;
//    }
//
//    public int getId(){return id;}
//
//    public String getTypeString(){return typeString;}


}
