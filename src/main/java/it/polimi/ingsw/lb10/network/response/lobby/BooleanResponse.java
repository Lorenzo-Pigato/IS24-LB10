package it.polimi.ingsw.lb10.network.response.lobby;

import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.CLIResponseHandler;

public class BooleanResponse extends Response {

    public Boolean responseState;

    @Override
    public void accept(CLIResponseHandler handler) {
        handler.visit(this);
    }


    public BooleanResponse(Boolean responseState){
        this.responseState = responseState;
    }

    public Boolean getResponseState() {
        return responseState;
    }

}
