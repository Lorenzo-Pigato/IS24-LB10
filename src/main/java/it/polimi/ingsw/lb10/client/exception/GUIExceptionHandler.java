package it.polimi.ingsw.lb10.client.exception;

import it.polimi.ingsw.lb10.client.controller.GUIClientViewController;
import it.polimi.ingsw.lb10.client.gui.GUIErrorPageController;
import javafx.application.Platform;

import java.io.EOFException;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class GUIExceptionHandler implements ExceptionHandler{

    private final GUIClientViewController controller;

    public GUIExceptionHandler(GUIClientViewController guiClientViewController) {
        this.controller = guiClientViewController;
    }

    @Override
    public void handle(Exception e) {
        GUIErrorPageController errorPage = new GUIErrorPageController();
        Platform.runLater(() -> {
            GUIClientViewController.instance().setGameSize();
            controller.changeScene(errorPage);
            ((GUIErrorPageController)(controller.getPage())).setErrorText("An Exception occurred: " + (e.getStackTrace() != null ? e.getMessage() : ""));
        });
    }

    @Override
    public void handle(UnknownHostException e) {
        GUIErrorPageController errorPage = new GUIErrorPageController();
        Platform.runLater(() -> {
            controller.changeScene(errorPage);
            ((GUIErrorPageController)(controller.getPage())).setErrorText("An Exception occurred: " + e.getMessage());
        });
    }

    @Override
    public void handle(IOException e) {
        GUIErrorPageController errorPage = new GUIErrorPageController();
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
        Platform.runLater(() -> {
            controller.changeScene(errorPage);
            ((GUIErrorPageController)(controller.getPage())).setErrorText("There were some issues connecting to the server: " + (e.getMessage() != null ? e.getMessage() : ""));
        });
    }

    @Override
    public void handle(SocketException e) {
        GUIErrorPageController errorPage = new GUIErrorPageController();
        if(controller.getClient().isActive()) {
            Platform.runLater(() -> {
                controller.changeScene(errorPage);
                ((GUIErrorPageController) (controller.getPage())).setErrorText("There were some issues connecting to the server: " + (e.getMessage() != null ? e.getMessage() : ""));
            });
        }else{
            Platform.exit();
        }
    }

    @Override
    public void handle(EOFException e) {
        GUIErrorPageController errorPage = new GUIErrorPageController();
        Platform.runLater(() -> {
            controller.changeScene(errorPage);
            ((GUIErrorPageController) (controller.getPage())).setErrorText("Server closed connection");
        });

    }

}
