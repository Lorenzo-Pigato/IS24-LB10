package it.polimi.ingsw.lb10.network.response.match;

import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.CLIResponseHandler;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.ResponseVisitable;

public class StartedMatchResponse extends Response implements ResponseVisitable {
    /**
     * @param handler
     */
    @Override
    public void accept(CLIResponseHandler handler) {
        handler.visit(this);
    }
}
