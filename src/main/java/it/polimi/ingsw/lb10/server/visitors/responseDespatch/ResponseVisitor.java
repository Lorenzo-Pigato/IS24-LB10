package it.polimi.ingsw.lb10.server.visitors.responseDespatch;

import it.polimi.ingsw.lb10.network.response.ChatMessageResponse;
import it.polimi.ingsw.lb10.network.response.EndTurnBroadcastResponse;
import it.polimi.ingsw.lb10.network.response.lobby.BooleanResponse;
import it.polimi.ingsw.lb10.network.response.match.*;

public interface ResponseVisitor {

    void visit (JoinMatchResponse response);

    void visit(BooleanResponse response);

    void visit(TerminatedMatchResponse response);

    void visit(StartedMatchResponse response);

    void visit(PrivateQuestsResponse response);

    void visit(GameSetupResponse response);

    void visit(ChatMessageResponse response);

    void visit(EndTurnBroadcastResponse response);

    void visit(UnavailableResourceDeckResponse unavalaibleResourceDeckResponse);

    void visit(UnavailableGoldenDeckResponse unavalaibleGoldenDeckResponse);

    void visit(PickedCardResponse pickedCardResponse);

    void visit(UnavailableTableResourceResponse unavalaibleTableResourceResponse);
}
