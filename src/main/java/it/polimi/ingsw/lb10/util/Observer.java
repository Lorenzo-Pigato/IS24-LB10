package it.polimi.ingsw.lb10.util;


public interface Observer <T>{
    public void update(T response);
    public void updateConditional(T response, int userHash);
}
