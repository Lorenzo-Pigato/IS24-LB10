package it.polimi.ingsw.lb10.network.response.match;

import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.CLIResponseHandler;

public class JoinMatchResponse extends Response {

    private final boolean joined;

    public JoinMatchResponse(boolean joined) {
        this.joined = joined;
    }

    @Override
    public void accept(CLIResponseHandler handler) {
        handler.visit(this);
    }

    public boolean getJoined() {return joined;}
}
