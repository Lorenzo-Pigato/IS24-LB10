package it.polimi.ingsw.lb10.server.visitors.requestDispatch;

import it.polimi.ingsw.lb10.network.requests.QuitRequest;
import it.polimi.ingsw.lb10.network.requests.match.MatchRequest;
import it.polimi.ingsw.lb10.network.requests.preMatch.LobbyToMatchRequest;
import it.polimi.ingsw.lb10.network.requests.preMatch.LoginRequest;
import it.polimi.ingsw.lb10.network.requests.preMatch.NewMatchRequest;

public interface LobbyRequestVisitor {

    void visit(LoginRequest lr);

    void visit(LobbyToMatchRequest jmr);

    /**
     * @param mr the match request received by the client controller
     *           This is a key method to handle MatchRequest objects sent by the client, LobbyController just finds out which MatchController
     *           the request refers to and submits it to his BlockingQueue
     */
    void visit(MatchRequest mr);

    /**
     * @param newMatchRequest request sent by the client when a new match must be created, contains number of players required to start the match
     */
    void visit(NewMatchRequest newMatchRequest);

    /**
     * @param quitRequest sent by the client whwnwver he wants to leave the match or the match is terminated
     */
    void visit(QuitRequest quitRequest);

}
