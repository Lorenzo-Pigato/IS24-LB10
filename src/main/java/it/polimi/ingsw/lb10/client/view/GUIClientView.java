package it.polimi.ingsw.lb10.client.view;

import it.polimi.ingsw.lb10.client.exception.CLIExceptionHandler;

import it.polimi.ingsw.lb10.client.gui.GUIConnectionPage;
import it.polimi.ingsw.lb10.client.gui.GUIPage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class GUIClientView extends Application {

    private static GUIPage page;
    private static Stage stage;
    private static boolean active = true;

    private static GUIClientView instance;

    public static GUIClientView instance() {
        if(instance == null) {
            instance = new GUIClientView();
        }
        return instance;
    }

    @Override
    public void start(Stage stage) throws Exception {
        GUIClientView.stage = stage;

        GUIClientView.stage.setHeight(850);
        GUIClientView.stage.setWidth(1600);
        GUIClientView.stage.setResizable(false);

    }

    public void setPage(GUIPage page) {
        GUIClientView.page = page;
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(page.getFXML())));
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            new CLIExceptionHandler(new CLIClientView()).handle(e);
        }
    }

}
