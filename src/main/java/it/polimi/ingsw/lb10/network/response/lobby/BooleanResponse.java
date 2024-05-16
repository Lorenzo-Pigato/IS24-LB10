package it.polimi.ingsw.lb10.network.response.lobby;

import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.ResponseVisitor;

public class BooleanResponse extends Response {
    private static final long serialVersionUID = 1L;
    private String username;

    public Boolean responseState;

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
