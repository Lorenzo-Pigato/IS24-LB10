package it.polimi.ingsw.lb10.server.visitors.responseDespatch;
import it.polimi.ingsw.lb10.client.controller.CLIClientViewController;
import it.polimi.ingsw.lb10.network.requests.QuitRequest;
import it.polimi.ingsw.lb10.network.requests.match.PrivateQuestsRequest;
import it.polimi.ingsw.lb10.network.response.lobby.BooleanResponse;
import it.polimi.ingsw.lb10.network.response.match.JoinMatchResponse;
import it.polimi.ingsw.lb10.network.response.match.PrivateQuestsResponse;
import it.polimi.ingsw.lb10.network.response.match.StartedMatchResponse;
import it.polimi.ingsw.lb10.network.response.match.TerminatedMatchResponse;

public class CLIResponseHandler implements ResponseVisitor {

    private static CLIResponseHandler instance;
    private static CLIClientViewController controller = CLIClientViewController.instance();


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
        System.out.println("QUI");
        controller.getClient().setStartedMatch(true);
        controller.setMatchId(response.getMatchId());
        controller.send(new PrivateQuestsRequest(controller.getMatchId()));
        controller.syncReceive().accept(this);
    }

    @Override
    public void visit(PrivateQuestsResponse response) {
        controller.privateQuestSelection(response);

    }



}
