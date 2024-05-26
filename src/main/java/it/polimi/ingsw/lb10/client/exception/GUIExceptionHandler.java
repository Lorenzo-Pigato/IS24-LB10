package it.polimi.ingsw.lb10.client.exception;

import it.polimi.ingsw.lb10.client.controller.GUIClientViewController;
import it.polimi.ingsw.lb10.client.gui.GUIErrorPageController;
import javafx.application.Platform;

import java.io.IOException;

public class GUIExceptionHandler implements ExceptionHandler{

    private final GUIClientViewController controller;

    public GUIExceptionHandler(GUIClientViewController guiClientViewController) {
        this.controller = guiClientViewController;
    }

    @Override
    public void handle(Exception e) {
        GUIErrorPageController errorPage = new GUIErrorPageController();
        controller.setGameSize();
        Platform.runLater(() -> {
            GUIClientViewController.instance().setGameSize();
            controller.changeScene(errorPage);
            ((GUIErrorPageController)(controller.getPage())).setErrorText("An Exception occurred: " + (e.getStackTrace() != null ? e.getMessage() : "") + Thread.currentThread().getName());
        });
    }

    @Override
    public void handle(IOException e) {
        GUIErrorPageController errorPage = new GUIErrorPageController();
        controller.setGameSize();
        if(controller.getClient().isActive()) {
            Platform.runLater(() -> {
                controller.changeScene(errorPage);
                ((GUIErrorPageController) (controller.getPage())).setErrorText("There was an issue with your sockets: " + (e.getMessage() != null ? e.getMessage() : ""));
            });
        }else Platform.exit();
    }

    @Override
    public void handle(ConnectionErrorException e) {
        GUIErrorPageController errorPage = new GUIErrorPageController();
        controller.setGameSize();
        Platform.runLater(() -> {
            controller.changeScene(errorPage);
            ((GUIErrorPageController)(controller.getPage())).setErrorText("There were some issues connecting to the server: " + (e.getMessage() != null ? e.getMessage() : ""));
        });
    }


    @Override
    public void handle(ConnectionTimedOutException e) {
        GUIErrorPageController errorPage = new GUIErrorPageController();
        controller.setGameSize();
        Platform.runLater(() -> {
            controller.changeScene(errorPage);
            ((GUIErrorPageController) (controller.getPage())).setErrorText("Connection timed out");
        });
    }

}
