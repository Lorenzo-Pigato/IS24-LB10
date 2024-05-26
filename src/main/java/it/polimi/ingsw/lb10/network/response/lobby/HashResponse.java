package it.polimi.ingsw.lb10.network.response.lobby;

import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.ResponseVisitor;

import java.io.Serial;

public class HashResponse extends Response {

    @Serial
    private static final long serialVersionUID = 2L;
    private final int hash;

    public HashResponse(int i) {
        this.hash = i;
    }

    public int getHash() {
        return hash;
    }

    @Override
    public void accept(ResponseVisitor handler) {

    }
}
