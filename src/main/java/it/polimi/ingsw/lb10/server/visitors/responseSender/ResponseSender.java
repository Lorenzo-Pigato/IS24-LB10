package it.polimi.ingsw.lb10.server.visitors.responseSender;

import it.polimi.ingsw.lb10.network.ClientConnection;
import it.polimi.ingsw.lb10.network.response.BooleanResponse;

public class ResponseSender implements ResponseSendingVisitor{

    private final ClientConnection clientConnection;

    public ResponseSender(ClientConnection clientConnection){
        this.clientConnection = clientConnection;
    }

    public void visit(BooleanResponse br){

    }
}
