package it.polimi.ingsw.lb10.server.visitors.requestDispatch;

import it.polimi.ingsw.lb10.network.requests.match.MatchRequest;
import it.polimi.ingsw.lb10.network.requests.match.JoinMatchRequest;
import it.polimi.ingsw.lb10.network.requests.preMatch.LobbyToMatchRequest;
import it.polimi.ingsw.lb10.network.requests.preMatch.LoginRequest;

public interface LobbyRequestVisitor {

    void visit(LoginRequest lr);

    void visit(LobbyToMatchRequest jmr);

    /**
     * @param mr the match request received by the client controller
     * This is a key method to handle MatchRequest objects sent by the client, LobbyController just finds out which MatchController
     * the request refers to and submits it to his BlockingQueue
     */
    void visit(MatchRequest mr);
}
