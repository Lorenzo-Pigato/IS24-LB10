package it.polimi.ingsw.lb10.network.response.lobby;

import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.CLIResponseHandler;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.ResponseVisitor;

public class BooleanResponse extends Response {
    private static final long serialVersionUID = 1L;

    public Boolean responseState;

    @Override
    public void accept(ResponseVisitor handler) {
        handler.visit(this);
    }


    public BooleanResponse(Boolean responseState){
        this.responseState = responseState;
    }

    public Boolean getResponseState() {
        return responseState;
    }

}
