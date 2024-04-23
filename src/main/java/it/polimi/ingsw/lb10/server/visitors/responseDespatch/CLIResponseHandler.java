package it.polimi.ingsw.lb10.server.visitors.responseDespatch;
import it.polimi.ingsw.lb10.client.controller.CLIClientViewController;
import it.polimi.ingsw.lb10.network.response.BooleanResponse;

public class CLIResponseHandler implements ResponseVisitor {

    private static CLIResponseHandler instance;
    private static CLIClientViewController controller = CLIClientViewController.instance();


    public static CLIResponseHandler instance(){
        if (instance == null) instance = new CLIResponseHandler();
        return instance;
    }

    @Override
    public void visit(BooleanResponse response) {
        controller.getClient().setLogged(response.getResponseState());
    }

}
