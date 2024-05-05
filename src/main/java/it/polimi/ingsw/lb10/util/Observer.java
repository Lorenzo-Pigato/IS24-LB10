package it.polimi.ingsw.lb10.util;


import it.polimi.ingsw.lb10.network.response.Response;

public interface Observer {
    public void update(Response response);

    public void updateConditional(Response response, int userHash);
}
