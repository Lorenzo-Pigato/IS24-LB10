package it.polimi.ingsw.lb10.server.visitors.requestDispatch;


import it.polimi.ingsw.lb10.network.requests.match.*;

public interface MatchRequestVisitor {

    void visit(JoinMatchRequest joinMatchRequest);

    void visit(PrivateQuestsRequest privateQuestsRequest);

    void visit(PrivateQuestSelectedRequest privateQuestSelectedRequest);

    void visit(ChatRequest chatRequest);

    void visit(ShowPlayerRequest showPlayerRequest);

    void visit(DrawGoldenFromTableRequest drawGoldenFromTableRequest);

    void visit(DrawResourceFromTableRequest drawResourceFromTableRequest);

    void visit(DrawResourceFromDeckRequest drawResourceFromDeckRequest);

    void visit(DrawGoldenFromDeckRequest drawGoldenFromDeckRequest);

    void visit(PlaceStartingCardRequest placeStartingCardRequest);

    void visit(PlaceCardRequest placeCardRequest);

    void visit(PickRequest pickRequest);

    void visit(MoveBoardRequest moveBoardRequest);
}
