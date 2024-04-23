package it.polimi.ingsw.lb10.network.requests;

import it.polimi.ingsw.lb10.server.visitors.requestDispatch.LobbyRequestVisitor;

public class QuitRequest extends Request{

    @Override
    public void accept(LobbyRequestVisitor handler) {
        handler.visit(this);
    }
}
