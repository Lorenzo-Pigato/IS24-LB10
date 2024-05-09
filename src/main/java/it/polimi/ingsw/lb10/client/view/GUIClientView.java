package it.polimi.ingsw.lb10.client.view;

import it.polimi.ingsw.lb10.client.exception.CLIExceptionHandler;

import it.polimi.ingsw.lb10.client.gui.GUIConnectionPage;
import it.polimi.ingsw.lb10.client.gui.GUIPage;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class GUIClientView extends Application implements ClientView {

    private GUIPage page;
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        setPage(new GUIConnectionPage());
        updatePageState(null);

        stage.setHeight(850);
        stage.setWidth(1600);
        stage.setResizable(false);

        displayPage(null);
    }

    @Override
    public void setPage(Page page) {
        this.page = (GUIPage) page;
    }

    @Override
    public void updatePageState(State state) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(page.getFXML())));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (Exception e) {
            new CLIExceptionHandler(new CLIClientView()).handle(e);
        }
    }

    @Override
    public void displayPage(Object[] args) {
        stage.show();
    }

    @Override
    public Page getPage() {
        return page;
    }
}
