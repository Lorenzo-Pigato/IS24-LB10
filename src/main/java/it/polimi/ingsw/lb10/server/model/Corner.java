package it.polimi.ingsw.lb10.server.model;

import java.util.ArrayList;

/*
 *  Define how to set the null element (null or 0)
 * */
public class Corner {
    private int id;
//    private Resource resource;
    private boolean left_right;
    private boolean up_down;

    public Corner(int id,boolean left_right, boolean up_down){
        this.left_right=left_right;
        this.up_down=up_down;
        this.id=id;
    }

    // --------> GETTER <--------
    public boolean isLeft() {
        return left_right;
    }

    public boolean isUp() {
        return up_down;
    }
    public int getId() {
        return id;
    }

//        public Resource getResource() {
//        return resource;
//

    // --------> SETTER <--------
    public void setLeft_right(boolean left_right) {
        this.left_right = left_right;
    }
    public void setUp_down(boolean up_down) {
        this.up_down = up_down;
    }
    public void setId(int id) {
        this.id = id;
    }
    //    public void setResource(Resource resource) {
//        this.resource = resource;
//    }
}
