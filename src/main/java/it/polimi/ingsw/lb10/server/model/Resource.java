package it.polimi.ingsw.lb10.server.model;

public enum Resource {
    NULL("Null",0),
    MUSHROOM("Mushroom",1),
    ANIMAL("Animal",2),
    INSECT("Insect",3),
    PLANT("Plant",4),
    FEATHER("Feather",5),
    POTION("Potion",6),
    PERGAMENA("Pergamena",7);

    final private int id;
    final private String typeString;
    Resource (String type, int id){
        this.typeString = type;
        this.id = id;
    }

    public int getId(){return id;}

    public String getTypeString(){return typeString;}


}
