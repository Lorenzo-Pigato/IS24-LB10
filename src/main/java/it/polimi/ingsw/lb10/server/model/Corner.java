package it.polimi.ingsw.lb10.server.model;
/*
 *  Define how to set the null element (null or 0)
 * */
public class Corner {
    private Resource resource;
    private boolean left_right;
    private boolean up_down;

    public boolean isLeft() {
        return left_right;
    }

    public boolean isUp() {
        return up_down;
    }


}
