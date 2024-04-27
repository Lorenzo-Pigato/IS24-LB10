package it.polimi.ingsw.lb10.server.visitors.responseDespatch;
import it.polimi.ingsw.lb10.client.cli.clipages.CLIMatchPage;
import it.polimi.ingsw.lb10.client.controller.CLIClientViewController;
import it.polimi.ingsw.lb10.network.requests.QuitRequest;
import it.polimi.ingsw.lb10.network.requests.match.PrivateQuestsRequest;
import it.polimi.ingsw.lb10.network.response.ChatMessageResponse;
import it.polimi.ingsw.lb10.network.response.EndTurnBroadcastResponse;
import it.polimi.ingsw.lb10.network.response.lobby.BooleanResponse;
import it.polimi.ingsw.lb10.network.response.match.*;
import it.polimi.ingsw.lb10.server.model.Player;

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
        controller.setOnTurn(((Player[])(response.getPlayers()))[0]);
        controller.getView().setPage(new CLIMatchPage());
        controller.getView().displayPage(response.getPlayers());
    }

    @Override
    public void visit(ChatMessageResponse chatMessageResponse){

    }

    @Override
    public void visit(EndTurnBroadcastResponse response) {
        controller.setOnTurn(response.getOnTurn());
    }

    @Override
    public void visit(GoldenPickResponse response) {

    }

    @Override
    public void visit(ResourcePickResponse response) {

    }

}
