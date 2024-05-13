package it.polimi.ingsw.lb10.server.visitors.responseDespatch;

import it.polimi.ingsw.lb10.client.controller.GUIClientViewController;
import it.polimi.ingsw.lb10.client.gui.GUIMatchPageController;
import it.polimi.ingsw.lb10.client.gui.GUIChooseQuestPageController;
import it.polimi.ingsw.lb10.network.requests.match.PrivateQuestsRequest;
import it.polimi.ingsw.lb10.network.response.lobby.BooleanResponse;
import it.polimi.ingsw.lb10.network.response.match.*;
import it.polimi.ingsw.lb10.server.model.Player;
import javafx.application.Platform;

import java.util.ArrayList;

public class GUIResponseHandler implements ResponseVisitor {

    private static GUIResponseHandler instance;
    private static final GUIClientViewController controller = GUIClientViewController.instance();


    public static GUIResponseHandler instance() {
        if (instance == null) instance = new GUIResponseHandler();
        return instance;
    }

    private GUIMatchPageController getMatchPageFromController(){
        return ((GUIMatchPageController)(controller.getPage()));
    }

    @Override
    public void visit(JoinMatchResponse response) {
        controller.getClient().setInMatch(response.getJoined());
        controller.setMatchId(response.getMatchId());
    }

    @Override
    public void visit(BooleanResponse response) {
        controller.getClient().setLogged(response.getResponseState());
    }

    @Override
    public void visit(TerminatedMatchResponse response) {

    }

    @Override
    public void visit(StartedMatchResponse response) {
        controller.send(new PrivateQuestsRequest(controller.getMatchId()));
    }

    @Override
    public void visit(PrivateQuestsResponse response) {
        GUIChooseQuestPageController.setQuests(response.getPrivateQuests().getFirst(), response.getPrivateQuests().getLast());
        Platform.runLater(() -> {
            controller.setGameSize();
            controller.changeScene(new GUIChooseQuestPageController());
        });
    }

    @Override
    public void visit(GameSetupResponse response) {
        Player thisPlayer = response.getPlayers().stream().filter(p -> p.getUsername().equals(controller.getClient().getUsername())).findFirst().orElseThrow(RuntimeException::new);
        GUIMatchPageController.setStartingCard(thisPlayer.getStartingCard());
        GUIMatchPageController.setCommonQuests(response.getPublicQuests());
        GUIMatchPageController.setThisPlayer(thisPlayer);

        ArrayList<Player> others =  response.getPlayers();
        others.remove(thisPlayer);

        GUIMatchPageController.setOtherPlayers(others);

        Platform.runLater(() -> {
            controller.changeScene(new GUIMatchPageController());
            thisPlayer.getHand().forEach(GUIMatchPageController::insertCardOnHand);
        });
    }

    @Override
    public void visit(ChatMessageResponse response) {
        Platform.runLater(()-> {
            getMatchPageFromController().newMessage(response.getSender(), response.getMessage());
        });
    }

    @Override
    public void visit(PickedCardResponse pickedCardResponse) {

    }

    @Override
    public void visit(PlaceStartingCardResponse placeStartingCardResponse) {
        Platform.runLater(() -> {
            getMatchPageFromController().placeStartingCard();
            getMatchPageFromController().updateResources(placeStartingCardResponse.getResources());
        });
    }

    @Override
    public void visit(PlaceCardResponse placeCardResponse) {
        Platform.runLater(() -> {
            if(placeCardResponse.getStatus()){
                getMatchPageFromController().placeCardOnTable(placeCardResponse.getCard());
                getMatchPageFromController().updateResources(placeCardResponse.getPlayerResources());
            }else{

            }
        });
    }

    @Override
    public void visit(ShowPickingPossibilitiesResponse showPickingPossibilitiesResponse) {

    }

    @Override
    public void visit(NotYourTurnResponse notYourTurnResponse) {

    }

    @Override
    public void visit(PlayerPointsUpdateResponse playerPointsUpdateResponse) {
        ((GUIMatchPageController)controller.getPage()).updateBoard(playerPointsUpdateResponse.getUsername(), playerPointsUpdateResponse.getPoints());
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

    @Override
    public void visit(DeckUpdateResponse deckUpdateResponse) {
        Platform.runLater(() -> {
            getMatchPageFromController().updateTablePickingOptions(deckUpdateResponse.getPickables());

        });
    }
}
