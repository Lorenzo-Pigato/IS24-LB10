package it.polimi.ingsw.lb10.network.requests;

import it.polimi.ingsw.lb10.network.requests.preMatch.LobbyRequest;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.LobbyRequestVisitor;

public class PongRequest extends LobbyRequest {
    /**
     * @param handler
     */
    @Override
    public void accept(LobbyRequestVisitor handler) {
        handler.visit(this);
    }
}
