package it.polimi.ingsw.lb10.server.model.cards.corners;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.Color;

import java.io.Serializable;


public class Corner implements Serializable {

    @JsonProperty("id")
    private int id;
    @JsonProperty("available")
    private boolean available;
    @JsonIgnore
    private boolean usedForQuest;
    @JsonProperty("position")
    private Position position;
    @JsonProperty("resource")
    private Resource resource;
    @JsonProperty("color")
    private Color cardColor;

@JsonCreator
    public Corner( @JsonProperty("id") int id, @JsonProperty("available") boolean available, @JsonProperty("position") Position position, @JsonProperty("resource") Resource resource, @JsonProperty("color") Color cardColor){
        this.id = id;
        this.available = available;
        this.usedForQuest = false;
        this.position = position;
        this.resource = resource;
        this.cardColor = cardColor;
    }

    // --------> GETTER <--------

    public Position getPosition() {
        return position;
    }

    public boolean isAvailable() {
        return available;
    }

    public boolean isUsedForQuest(){
        return usedForQuest;
    }

    public Resource getResource() {
        return resource;
    }

    public int getId() {
        return id;
    }

    public Color getCardColor() {
        return cardColor;
    }

    // --------> SETTER <--------

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCardColor(Color cardColor) {
        this.cardColor = cardColor;
    }
    public void setUsedForQuest(boolean usedForQuest){ this.usedForQuest=usedForQuest;}
}
