package it.polimi.ingsw.lb10.server.visitors.requestDispatch;


import it.polimi.ingsw.lb10.network.requests.match.JoinMatchRequest;

public interface MatchRequestVisitor {

    void visit(JoinMatchRequest joinMatchRequest);



}
