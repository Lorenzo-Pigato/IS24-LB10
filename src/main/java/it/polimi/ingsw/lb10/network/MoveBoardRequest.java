package it.polimi.ingsw.lb10.network;

import it.polimi.ingsw.lb10.network.requests.Request;
import it.polimi.ingsw.lb10.network.requests.match.MatchRequest;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.LobbyRequestVisitor;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.MatchRequestVisitor;

public class MoveBoardRequest extends MatchRequest {


    public MoveBoardRequest(int matchId) {
        super(matchId);
    }


    @Override
    public void accept(MatchRequestVisitor visitor) {
        visitor.visit(this);
    }
}
