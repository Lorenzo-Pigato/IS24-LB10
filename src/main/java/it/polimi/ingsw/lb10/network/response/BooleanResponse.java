package it.polimi.ingsw.lb10.network.response;

import it.polimi.ingsw.lb10.server.visitors.responseDespatch.ResponseHandler;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.ResponseVisitable;
import it.polimi.ingsw.lb10.server.visitors.responseSender.ResponseSender;
import it.polimi.ingsw.lb10.server.visitors.responseSender.ResponseSendingVisitable;
import it.polimi.ingsw.lb10.server.visitors.responseSender.ResponseSendingVisitor;

public class BooleanResponse extends Response implements ResponseVisitable, ResponseSendingVisitable {

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

    @Override
    public void accept(ResponseSender sender) {
        sender.visit(this);
    }
}
