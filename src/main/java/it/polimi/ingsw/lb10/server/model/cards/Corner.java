package it.polimi.ingsw.lb10.server.model.cards;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import it.polimi.ingsw.lb10.server.model.Resource;
import java.util.ArrayList;

/*
 *  Define how to set the null element (null or 0)
 * */

// I tried to add Lombok, but I don't know how to make the de-serializing work
@Getter
@Setter
public class Corner {


    private Resource resource;
    private boolean left_right;
    private boolean up_down;


    public Corner(){}

    public Corner(boolean left_right, boolean up_down, Resource resource){
        this.left_right=left_right;
        this.up_down=up_down;

        this.resource=resource;
    }

    // --------> GETTER <--------
    public boolean isLeft() {
        return left_right;
    }

    public boolean isUp() {
        return up_down;
    }
    public Resource getResource() {
        return resource;
    }

    // --------> SETTER <--------
    public void setLeft_right(boolean left_right) {
        this.left_right = left_right;
    }
    public void setUp_down(boolean up_down) {
        this.up_down = up_down;
    }
    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
