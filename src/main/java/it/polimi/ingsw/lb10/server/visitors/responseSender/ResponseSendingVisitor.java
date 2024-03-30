package it.polimi.ingsw.lb10.server.visitors.responseSender;

import it.polimi.ingsw.lb10.network.response.BooleanResponse;

public interface ResponseSendingVisitor {

    void visit(BooleanResponse br);
}
