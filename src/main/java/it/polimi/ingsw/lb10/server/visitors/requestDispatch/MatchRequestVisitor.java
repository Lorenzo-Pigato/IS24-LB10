package it.polimi.ingsw.lb10.server.visitors.requestDispatch;


import it.polimi.ingsw.lb10.network.requests.match.JoinMatchRequest;
import it.polimi.ingsw.lb10.network.requests.match.PrivateQuestsRequest;

public interface MatchRequestVisitor {

    void visit(JoinMatchRequest joinMatchRequest);

    void visit(PrivateQuestsRequest privateQuestsRequest);

}
