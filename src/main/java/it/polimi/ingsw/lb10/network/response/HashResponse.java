package it.polimi.ingsw.lb10.network.response;

import it.polimi.ingsw.lb10.server.visitors.responseDespatch.CLIResponseHandler;

public class HashResponse extends Response {

    private int hash;

    public HashResponse(int i) {
        this.hash = i;
    }

    public int getHash(){
        return hash;
    }

    @Override
    public void accept(CLIResponseHandler handler) {

    }
}
