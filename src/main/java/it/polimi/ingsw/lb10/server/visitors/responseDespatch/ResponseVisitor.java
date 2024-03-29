package it.polimi.ingsw.lb10.server.visitors.responseDespatch;

import it.polimi.ingsw.lb10.network.response.BooleanResponse;

public interface ResponseVisitor {

    void visit(BooleanResponse response);
}
