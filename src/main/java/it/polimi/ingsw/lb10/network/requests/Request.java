package it.polimi.ingsw.lb10.network.requests;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.LobbyRequestVisitable;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.MatchRequestVisitable;

import java.io.Serializable;

public abstract class Request implements Serializable , LobbyRequestVisitable{
    private int hashCode;

    public Request(int hashCode) {
        this.hashCode = hashCode;
    }

    public int getHashCode(){
        return hashCode;
    }

}
