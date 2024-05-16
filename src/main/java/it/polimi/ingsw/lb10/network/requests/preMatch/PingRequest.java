package it.polimi.ingsw.lb10.network.requests.preMatch;

import it.polimi.ingsw.lb10.server.visitors.requestDispatch.LobbyRequestVisitor;

public class PingRequest extends LobbyRequest{

    @Override
    public void accept(LobbyRequestVisitor handler) {
        handler.visit(this);
    }
}
