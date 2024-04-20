package it.polimi.ingsw.lb10.server.model.cards;

import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;

import java.util.ArrayList;

public class BaseCard {
    private int id;
    private Color colorCard;
    private ArrayList<Corner> corners;

    public BaseCard(int id, Color colorCard, ArrayList<Corner> corners){
        this.id=id;
        this.colorCard=colorCard;
        this.corners=corners;
    }

    // --------> GETTER <--------
    public int getId() {
        return id;
    }
    public ArrayList<Corner> getCorners() {
        return corners;
    }
    public Color getColorCard() {
        return colorCard;
    }
    // --------> SETTER <--------

    public void setCorners(ArrayList<Corner> corners) {
        this.corners = corners;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setColorCard(Color colorCard) {
        this.colorCard = colorCard;
    }
}
