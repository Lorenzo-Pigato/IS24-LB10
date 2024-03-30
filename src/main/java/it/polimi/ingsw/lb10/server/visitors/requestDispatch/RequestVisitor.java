package it.polimi.ingsw.lb10.server.visitors.requestDispatch;

import it.polimi.ingsw.lb10.network.requests.preMatch.LoginRequest;

public interface RequestVisitor {

    void visit(LoginRequest lr);
}
