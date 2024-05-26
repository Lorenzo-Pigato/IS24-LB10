package it.polimi.ingsw.lb10.client.view;
import it.polimi.ingsw.lb10.client.controller.GUIClientViewController;
import javafx.application.Application;
import javafx.stage.Stage;

public class GUIClientView extends Application {

    private final GUIClientViewController controller = GUIClientViewController.instance();

    @Override
    public void start(Stage stage) {
        controller.initialize(stage);
    }

}
