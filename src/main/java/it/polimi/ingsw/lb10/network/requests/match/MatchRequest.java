package it.polimi.ingsw.lb10.network.requests.match;

import it.polimi.ingsw.lb10.network.requests.Request;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.RequestVisitor;

public class MatchRequest extends Request {

    public MatchRequest(int hashCode) {
        super(hashCode);
    }

    @Override
    public void accept(RequestVisitor handler) {

    }
}
