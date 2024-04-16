package it.polimi.ingsw.lb10.server.visitors.responseDespatch;

import it.polimi.ingsw.lb10.network.response.lobby.BooleanResponse;
import it.polimi.ingsw.lb10.network.response.match.JoinMatchResponse;
import it.polimi.ingsw.lb10.network.response.match.TerminatedMatchResponse;

public interface ResponseVisitor {

    void visit (JoinMatchResponse response);

    void visit(BooleanResponse response);

    void visit(TerminatedMatchResponse response);
}
