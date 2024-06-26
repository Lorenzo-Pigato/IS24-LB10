package it.polimi.ingsw.lb10.network.requests;

import it.polimi.ingsw.lb10.server.visitors.requestDispatch.LobbyRequestVisitable;

import java.io.Serial;
import java.io.Serializable;

public abstract class Request implements Serializable, LobbyRequestVisitable {
    @Serial
    private static final long serialVersionUID = 31L;
    private int userHash;

    public int getUserHash() {
        return userHash;
    }

    public void setUserHash(int hash) {
        this.userHash = hash;
    }

}
