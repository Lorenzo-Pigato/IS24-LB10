package it.polimi.ingsw.lb10.server.visitors.responseDespatch;

import it.polimi.ingsw.lb10.client.controller.GUIClientViewController;
import it.polimi.ingsw.lb10.network.response.lobby.BooleanResponse;
import it.polimi.ingsw.lb10.network.response.match.*;

public class GUIResponseHandler implements ResponseVisitor {

    private static GUIResponseHandler instance;
    private static final GUIClientViewController controller = GUIClientViewController.instance();


    public static GUIResponseHandler instance() {
        if (instance == null) instance = new GUIResponseHandler();
        return instance;
    }

    @Override
    public void visit(JoinMatchResponse response) {

    }

    @Override
    public void visit(BooleanResponse response) {

    }

    @Override
    public void visit(TerminatedMatchResponse response) {

    }

    @Override
    public void visit(StartedMatchResponse response) {

    }

    @Override
    public void visit(PrivateQuestsResponse response) {

    }

    @Override
    public void visit(GameSetupResponse response) {

    }

    @Override
    public void visit(ChatMessageResponse response) {

    }

    @Override
    public void visit(PickedCardResponse pickedCardResponse) {

    }

    @Override
    public void visit(PlaceStartingCardResponse placeStartingCardResponse) {

    }

    @Override
    public void visit(PlaceCardResponse placeCardResponse) {

    }

    @Override
    public void visit(ShowPickingPossibilitiesResponse showPickingPossibilitiesResponse) {

    }

    @Override
    public void visit(NotYourTurnResponse notYourTurnResponse) {

    }

    @Override
    public void visit(PlayerPointsUpdateResponse playerPointsUpdateResponse) {

    }

    @Override
    public void visit(EndGameResponse endGameResponse) {

    }

    @Override
    public void visit(ServerNotification serverNotification) {

    }

    @Override
    public void visit(MoveBoardResponse moveBoardResponse) {

    }

    @Override
    public void visit(PlayerLeftResponse playerLeftResponse) {

    }
}
