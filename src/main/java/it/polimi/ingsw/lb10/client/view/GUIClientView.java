package it.polimi.ingsw.lb10.client.view;

import it.polimi.ingsw.lb10.client.exception.CLIExceptionHandler;

import it.polimi.ingsw.lb10.client.gui.GUIConnectionPage;
import it.polimi.ingsw.lb10.client.gui.GUILoginPage;
import it.polimi.ingsw.lb10.client.gui.GUIPage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class GUIClientView extends Application implements ClientView {

    private GUIPage page;

    @Override
    public void start(Stage stage) throws Exception {
        try {
            setPage(new GUIConnectionPage());
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(page.getFXML())));
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            new CLIExceptionHandler(new CLIClientView()).handle(e);
        }
    }

    @Override
    public void setPage(Page page) {
        this.page = (GUIPage) page;
    }

    @Override
    public void updatePageState(State state) {

    }

    @Override
    public void displayPage(Object[] args) {

    }

    @Override
    public Page getPage() {
        return null;
    }
}
