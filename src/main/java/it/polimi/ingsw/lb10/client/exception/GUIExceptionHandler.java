package it.polimi.ingsw.lb10.client.exception;

import it.polimi.ingsw.lb10.client.controller.GUIClientViewController;

import java.io.IOException;
import java.net.UnknownHostException;

public class GUIExceptionHandler implements ExceptionHandler{

    private final GUIClientViewController guiClientViewController;

    public GUIExceptionHandler(GUIClientViewController guiClientViewController) {
        this.guiClientViewController = guiClientViewController;
    }

    @Override
    public void handle(Exception e) {

    }

    @Override
    public void handle(UnknownHostException e) {

    }

    @Override
    public void handle(IOException e) {

    }

    @Override
    public void handle(ConnectionErrorException e) {

    }
}
