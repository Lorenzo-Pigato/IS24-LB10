package it.polimi.ingsw.lb10.network.requests;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.LobbyRequestVisitable;

import java.io.Serializable;

public abstract class Request implements Serializable , LobbyRequestVisitable{
    private static final long serialVersionUID = 3L;
    private int userHash;

    public int getUserHash(){
        return userHash;
    }

    public void setUserHash(int hash){this.userHash = hash;}

}
