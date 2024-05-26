package it.polimi.ingsw.lb10.network.response.lobby;

import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.ResponseVisitor;

import java.io.Serial;

public class BooleanResponse extends Response {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String username;

    public final Boolean responseState;

    @Override
    public void accept(ResponseVisitor handler) {
        handler.visit(this);
    }


    public BooleanResponse(String username, Boolean responseState) {
        this.username = username;
        this.responseState = responseState;
    }

    public Boolean getResponseState() {
        return responseState;
    }

    public String getUsername(){return username;}

}
