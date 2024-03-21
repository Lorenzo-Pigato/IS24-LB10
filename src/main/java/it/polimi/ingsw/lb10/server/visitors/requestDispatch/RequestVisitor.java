package it.polimi.ingsw.lb10.server.visitors.requestDispatch;

import it.polimi.ingsw.lb10.network.requests.match.MatchRequest;
import it.polimi.ingsw.lb10.network.requests.preMatch.PreMatchRequest;

public interface RequestVisitor {

    public void visit(PreMatchRequest pmr);
    public void visit(MatchRequest mr);

}
