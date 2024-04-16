package it.polimi.ingsw.lb10.network.response.match;

import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.CLIResponseHandler;

public class TerminatedMatchResponse extends Response {

    @Override
    public void accept(CLIResponseHandler handler) {
        handler.visit(this);
    }
}
