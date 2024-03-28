package it.polimi.ingsw.lb10.server.model.cards;

public enum Position {
    TOPLEFT("topLeft",0),
    TOPRIGHT("topRight",1),
    BOTTOMRIGHT("bottomRight",2),
    BOTTOMLEFT("bottomLeft",3);

    final private int id;
    final private String typeString;
    Position (String type, int id){
        this.typeString = type;
        this.id = id;
    }
    public int getId(){return id;}

    public String getTypeString(){return typeString;}
}
