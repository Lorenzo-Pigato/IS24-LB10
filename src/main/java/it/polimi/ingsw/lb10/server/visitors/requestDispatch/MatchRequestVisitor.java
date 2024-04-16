package it.polimi.ingsw.lb10.server.visitors.requestDispatch;


import it.polimi.ingsw.lb10.network.requests.preMatch.JoinMatchRequest;

public interface MatchRequestVisitor {

    void visit(JoinMatchRequest joinMatchRequest);



}
