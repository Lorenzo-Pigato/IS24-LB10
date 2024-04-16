package it.polimi.ingsw.lb10.network.requests.preMatch;

import it.polimi.ingsw.lb10.network.requests.Request;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.LobbyRequestVisitable;

public abstract class LobbyRequest extends Request implements LobbyRequestVisitable {

    public LobbyRequest(int hashCode) {
        super(hashCode);
    }
}
