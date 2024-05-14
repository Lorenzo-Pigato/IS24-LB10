package it.polimi.ingsw.lb10.client.exception;

import it.polimi.ingsw.lb10.client.controller.GUIClientViewController;
import it.polimi.ingsw.lb10.client.gui.GUIErrorPageController;
import javafx.application.Platform;

import java.io.IOException;
import java.net.UnknownHostException;

public class GUIExceptionHandler implements ExceptionHandler{

    private final GUIClientViewController controller;

    public GUIExceptionHandler(GUIClientViewController guiClientViewController) {
        this.controller = guiClientViewController;
    }

    @Override
    public void handle(Exception e) {
        Platform.runLater(() -> {
            GUIErrorPageController errorPage = new GUIErrorPageController();
            controller.changeScene(errorPage);
            errorPage.setErrorText("An Exception occurred: " + e.getMessage());
        });
    }

    @Override
    public void handle(UnknownHostException e) {
        Platform.runLater(() -> {
            GUIErrorPageController errorPage = new GUIErrorPageController();
            controller.changeScene(errorPage);
            errorPage.setErrorText("An Exception occurred: " + e.getMessage());
        });
    }

    @Override
    public void handle(IOException e) {
        Platform.runLater(() -> {
            GUIErrorPageController errorPage = new GUIErrorPageController();
            controller.changeScene(errorPage);
            errorPage.setErrorText("There was an issue with your sockets: " + e.getMessage());
        });
    }

    @Override
    public void handle(ConnectionErrorException e) {
        Platform.runLater(() -> {
            GUIErrorPageController errorPage = new GUIErrorPageController();
            controller.changeScene(errorPage);
            errorPage.setErrorText("There were some issues connecting to the server: " + e.getMessage());
        });
    }
}
