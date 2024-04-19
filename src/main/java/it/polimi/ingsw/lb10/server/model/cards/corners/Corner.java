package it.polimi.ingsw.lb10.server.model.cards.corners;

import com.fasterxml.jackson.annotation.JsonAlias;
import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.Color;


public abstract class Corner {
    private boolean available;
    private boolean usedForQuest;
    private Position position;
    private Resource resource;
    private int id;
    private Color cardColor;

    public Corner(Position position,Resource resource){
        this.position=position;
        this.resource=resource;
    }

    // --------> GETTER <--------

    @JsonAlias("position")
    public Position getPosition() {
        return position;
    }

    @JsonAlias("available")
    public boolean isAvailable() {
        return available;
    }
    public boolean isUsedForQuest(){
        return isUsedForQuest();
    }
    @JsonAlias("resource")
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
    @JsonAlias("position")
    public void setPosition(Position position) {
        this.position = position;
    }

    @JsonAlias("available")
    public void setAvailable(boolean available) {
        this.available = available;
    }

    @JsonAlias("resource")
    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCardColor(Color cardColor) {
        this.cardColor = cardColor;
    }
    private void setUsedForQuest(boolean usedForQuest){ this.usedForQuest=usedForQuest;}
}
