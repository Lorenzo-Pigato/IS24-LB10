package it.polimi.ingsw.lb10.server.visitors.responseDespatch;

import it.polimi.ingsw.lb10.network.response.PingResponse;
import it.polimi.ingsw.lb10.network.response.PongResponse;
import it.polimi.ingsw.lb10.network.response.lobby.BooleanResponse;
import it.polimi.ingsw.lb10.network.response.match.*;

public interface ResponseVisitor {

    void visit(JoinMatchResponse response);

    void visit(BooleanResponse response);

    void visit(StartedMatchResponse response);

    void visit(PrivateQuestsResponse response);

    void visit(GameSetupResponse response);

    void visit(ChatMessageResponse response);

    void visit(PickedCardResponse pickedCardResponse);

    void visit(PlaceStartingCardResponse placeStartingCardResponse);

    void visit(PlaceCardResponse placeCardResponse);

    void visit(ShowPickingPossibilitiesResponse showPickingPossibilitiesResponse);

    void visit(NotYourTurnResponse notYourTurnResponse);

    void visit(PlayerPointsUpdateResponse playerPointsUpdateResponse);

    void visit(EndGameResponse endGameResponse);

    void visit(ServerNotification serverNotification);

    void visit(MoveBoardResponse moveBoardResponse);

    void visit(PlayerLeftResponse playerLeftResponse);

    void visit(DeckUpdateResponse deckUpdateResponse);

    void visit(PongResponse pongResponse);

    void visit(PingResponse pingResponse);
}
