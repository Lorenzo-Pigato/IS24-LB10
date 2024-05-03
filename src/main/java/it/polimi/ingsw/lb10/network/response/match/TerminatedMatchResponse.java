package it.polimi.ingsw.lb10.network.response.match;

import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.CLIResponseHandler;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.ResponseVisitor;

public class TerminatedMatchResponse extends Response {
    private static final long serialVersionUID = 10L;

    @Override
    public void accept(ResponseVisitor handler) {
        handler.visit(this);
    }
}
