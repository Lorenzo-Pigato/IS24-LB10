package it.polimi.ingsw.lb10.network.requests;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.RequestVisitable;

import java.io.Serializable;

public abstract class Request implements RequestVisitable , Serializable {
    private int hashCode;

    public Request(int hashCode) {
        this.hashCode = hashCode;
    }

    public int getHashCode(){
        return hashCode;
    }

}
