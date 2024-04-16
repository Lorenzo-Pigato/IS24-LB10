package it.polimi.ingsw.lb10.server.visitors.requestDispatch;

public interface MatchRequestVisitable {

    void accept(MatchRequestVisitor visitor);
}
