package it.polimi.ingsw.lb10.network.response;

import it.polimi.ingsw.lb10.server.visitors.responseDespatch.ResponseHandler;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.ResponseVisitable;

public class BooleanResponse extends Response implements ResponseVisitable {

    public Boolean responseState;

    @Override
    public void accept(ResponseHandler handler) {
    }


    public BooleanResponse(Boolean responseState){
        this.responseState = responseState;
    }

    public Boolean getResponseState() {
        return responseState;
    }

}
