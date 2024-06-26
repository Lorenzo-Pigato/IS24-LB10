package it.polimi.ingsw.lb10.server.model.cards;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * in this class are written all methods and attributes that are common to all cards (excluding Quest card)
 */
public abstract class BaseCard implements Serializable {

    private int id;
    private Color colorCard;
    private ArrayList<Corner> corners;

    public BaseCard() {
    }

    @JsonCreator
    public BaseCard(@JsonProperty("id") int id, @JsonProperty("color") Color colorCard, @JsonProperty("corners") ArrayList<Corner> corners) {
        this.id = id;
        this.colorCard = colorCard;
        this.corners = corners;
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

    public ArrayList<Corner> getStateCardCorners() {
        return null;
    }
}
