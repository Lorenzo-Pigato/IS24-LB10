package it.polimi.ingsw.lb10.server.visitors.responseDespatch;

import it.polimi.ingsw.lb10.server.visitors.responseDespatch.ResponseHandler;

public interface ResponseVisitable {

    void accept(ResponseHandler handler);
}
