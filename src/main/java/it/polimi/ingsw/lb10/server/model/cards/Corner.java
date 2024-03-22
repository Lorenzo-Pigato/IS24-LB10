package it.polimi.ingsw.lb10.server.model.cards;

import com.fasterxml.jackson.annotation.JsonAlias;
import it.polimi.ingsw.lb10.server.model.Resource;
import java.util.ArrayList;

/*
 *  Define how to set the null element (null or 0)
 * */

// I tried to add Lombok, but I don't know how to make the de-serializing work

public class Corner {


    private Resource resource;
    private boolean left;
    private boolean up;


    public Corner(){}

    public Corner(Resource resource, boolean up_down,boolean left_right){
        this.left=left_right;
        this.up=up_down;
        this.resource=resource;
    }

    // --------> GETTER <--------
    @JsonAlias("left")
    public boolean isLeft_Right() {
        return left;
    }
    @JsonAlias("up")
    public boolean isUp_Down() {
        return up;
    }
    @JsonAlias("resource")
    public Resource getResource() {
        return resource;
    }

    // --------> SETTER <--------
    @JsonAlias("left")
    public void setLeft_right(boolean left_right) {
        this.left = left_right;
    }
    @JsonAlias("up")
    public void setUp_down(boolean up_down) {
        this.up = up_down;
    }
    @JsonAlias("resource")
    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
