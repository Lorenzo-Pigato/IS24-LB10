package it.polimi.ingsw.lb10.server.visitors.responseDespatch;
import it.polimi.ingsw.lb10.client.cli.clipages.CLIEndOfMatchPage;
import it.polimi.ingsw.lb10.client.cli.clipages.CLILoginPage;
import it.polimi.ingsw.lb10.client.cli.clipages.CLIMatchPage;
import it.polimi.ingsw.lb10.client.controller.CLIClientViewController;
import it.polimi.ingsw.lb10.network.requests.QuitRequest;
import it.polimi.ingsw.lb10.network.requests.match.PickRequest;
import it.polimi.ingsw.lb10.network.requests.match.PrivateQuestsRequest;

import it.polimi.ingsw.lb10.network.response.lobby.BooleanResponse;
import it.polimi.ingsw.lb10.network.response.match.*;
import it.polimi.ingsw.lb10.server.model.Player;
import it.polimi.ingsw.lb10.server.model.cards.GoldenCard;
import it.polimi.ingsw.lb10.server.model.cards.ResourceCard;


public class CLIResponseHandler implements ResponseVisitor {

    private static CLIResponseHandler instance;
    private static final CLIClientViewController controller = CLIClientViewController.instance();


    public static CLIResponseHandler instance(){
        if (instance == null) instance = new CLIResponseHandler();
        return instance;
    }

    @Override
    public void visit(JoinMatchResponse response){
        controller.getClient().setInMatch(response.getJoined());
        controller.setMatchId(response.getMatchId());
    }

    @Override
    public void visit(BooleanResponse response) {
        controller.getClient().setLogged(response.getResponseState());
    }

    @Override
    public void visit(TerminatedMatchResponse response) {
        controller.send(new QuitRequest());
        controller.close();
    }

    @Override
    public void visit(StartedMatchResponse response){
        controller.getClient().setStartedMatch(true);
        controller.setMatchId(response.getMatchId());
        controller.send(new PrivateQuestsRequest(controller.getMatchId()));
        controller.syncReceive().accept(this); //PrivateQuestResponse
    }

    @Override
    public void visit(PrivateQuestsResponse response) {
        controller.privateQuestSelection(response);
        controller.syncReceive().accept(this); //GameSetUp response
    }

    @Override
    public void visit(GameSetupResponse response){
        Player player = ((response.getPlayers().stream().filter(p -> p.getUserHash() == controller.getUserHash())).findFirst().orElseThrow(RuntimeException::new));
        controller.setHand(player.getHand());
        controller.setStartingCard(player.getStartingCard());
        controller.getView().setPage(new CLIMatchPage());

        CLIMatchPage.setPlayer(player);
        CLIMatchPage.setPlayers(response.getPlayers());

        controller.getView().updatePageState(new CLIMatchPage.StartingTurn());
        controller.getView().displayPage(new Object[]{player, player.getStartingCard(), player.getPrivateQuest(), response.getPublicQuests(), player.getHand()});
    }

    @Override
    public void visit(ChatMessageResponse chatMessageResponse){
        CLIMatchPage.chatLog(chatMessageResponse.getSender(), chatMessageResponse.getMessage());
    }

    @Override
    public void visit(PickedCardResponse pickedCardResponse) {
        if(pickedCardResponse.getStatus()){
            controller.getHand().add(pickedCardResponse.getCard());
            controller.getView().getPage().changeState(new CLIMatchPage.Default());
            controller.getView().getPage().print(null);

            CLIMatchPage.printBoard(pickedCardResponse.getMatrix());
            CLIMatchPage.displayHand(controller.getHand());

        }else{
            CLIMatchPage.serverReply(pickedCardResponse.getMessage() + ", pick another card");
        }

    }

    @Override
    public void visit(PlaceStartingCardResponse placeStartingCardResponse) {
        controller.getView().getPage().changeState(new CLIMatchPage.InterfaceSetup());
        controller.getView().getPage().print(null);

        controller.getView().getPage().changeState(new CLIMatchPage.Default());
        controller.getView().getPage().print(null);

        CLIMatchPage.placeCard(placeStartingCardResponse.getStartingCard(), 41, 41, null);
        CLIMatchPage.updateResourceCounter(placeStartingCardResponse.getResources());
    }

    @Override
    public void visit(PlaceCardResponse placeCardResponse) {
        if(placeCardResponse.getStatus()){
            CLIMatchPage.updateResourceCounter(placeCardResponse.getPlayerResources());
            CLIMatchPage.placeCard(placeCardResponse.getCard(), placeCardResponse.getCol(), placeCardResponse.getRow(),controller.getHand().indexOf(controller.getHand().stream().filter(placeableCard -> placeableCard.getId() == placeCardResponse.getCard().getId()).findFirst().orElse(null)));

            controller.getHand().remove(controller.getHand().stream().filter(placeableCard -> placeableCard.getId() == placeCardResponse.getCard().getId()).findFirst().orElse(null));
            controller.send(new PickRequest(controller.getMatchId()));
        }else{
            CLIMatchPage.serverReply("Invalid card placement, retry!");
        };
    }

    @Override
    public void visit(ShowPickingPossibilitiesResponse showPickingPossibilitiesResponse) {
        GoldenCard g1 = null;
        GoldenCard g2 = null;

        ResourceCard r1 = null;
        ResourceCard r2 = null;

        if(showPickingPossibilitiesResponse.getGoldenUncovered().size() == 2){
            g1 = showPickingPossibilitiesResponse.getGoldenUncovered().get(0);
            g2 = showPickingPossibilitiesResponse.getGoldenUncovered().get(1);
        } else if (showPickingPossibilitiesResponse.getGoldenUncovered().size() == 1) {
            g1 = showPickingPossibilitiesResponse.getGoldenUncovered().get(0);
        }

        if(showPickingPossibilitiesResponse.getResourceUncovered().size() == 2){
            r1 = showPickingPossibilitiesResponse.getResourceUncovered().get(0);
            r2 = showPickingPossibilitiesResponse.getResourceUncovered().get(1);
        } else if (showPickingPossibilitiesResponse.getResourceUncovered().size() == 1) {
            r1 = showPickingPossibilitiesResponse.getResourceUncovered().get(0);
        }

        controller.getView().getPage().changeState(new CLIMatchPage.PickCard());
        controller.getView().getPage().print(new Object[]{g1, g2, r1, r2, showPickingPossibilitiesResponse.getGoldenCard(),  showPickingPossibilitiesResponse.getResourceCard()});
        CLIMatchPage.serverReply("Choose which card you want to pick by typing command <pick> <id>"); //ugly message
    }


    @Override
    public void visit(NotYourTurnResponse notYourTurnResponse) {
        CLIMatchPage.serverReply("It's " + notYourTurnResponse.getUsername() + ", wait for your turn...");
    }

    /**
     * @param playerPointsUpdateResponse
     */
    @Override
    public void visit(PlayerPointsUpdateResponse playerPointsUpdateResponse) {
        CLIMatchPage.updatePlayerScore(playerPointsUpdateResponse.getUsername(), playerPointsUpdateResponse.getPoints());

    }

    /**
     * @param endGameResponse
     */
    @Override
    public void visit(EndGameResponse endGameResponse) {
        controller.getView().setPage(new CLIEndOfMatchPage());
        controller.getView().getPage().print(new Object[]{endGameResponse.getPlayer(), endGameResponse.getPlayers()});


    }

    /**
     * @param badRequestResponse
     */
    @Override
    public void visit(BadRequestResponse badRequestResponse) {
        CLIMatchPage.serverReply(badRequestResponse.getMessage());
    }


    @Override
    public void visit(MoveBoardResponse moveBoardResponse) {
        CLIMatchPage.moveBoard(moveBoardResponse.getBoard(), moveBoardResponse.getxOffset(), moveBoardResponse.getyOffset());
    }

    @Override
    public void visit(PlayerLeftResponse playerLeftResponse) {
        CLIMatchPage.removePlayer(playerLeftResponse.getUsername());
    }

}
