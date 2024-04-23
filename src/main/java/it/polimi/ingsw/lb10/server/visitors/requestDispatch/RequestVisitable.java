package it.polimi.ingsw.lb10.server.visitors.requestDispatch;

import it.polimi.ingsw.lb10.network.requests.Request;

public interface RequestVisitable {
    void accept(RequestVisitor handler);
}
