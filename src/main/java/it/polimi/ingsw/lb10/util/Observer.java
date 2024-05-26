package it.polimi.ingsw.lb10.util;


import it.polimi.ingsw.lb10.network.response.Response;

public interface Observer {
    void update(Response response);

    void updateConditional(Response response, int userHash);
}
