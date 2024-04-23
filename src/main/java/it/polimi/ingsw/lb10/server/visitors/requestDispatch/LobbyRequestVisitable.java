package it.polimi.ingsw.lb10.server.visitors.requestDispatch;

public interface LobbyRequestVisitable {
    void accept(LobbyRequestVisitor handler);
}
