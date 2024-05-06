package it.polimi.ingsw.lb10.client.exception;

import it.polimi.ingsw.lb10.client.view.GUIClientView;

import java.io.IOException;
import java.net.UnknownHostException;

public class GUIExceptionHandler implements ExceptionHandler{

    private final GUIClientView view;

    public GUIExceptionHandler(GUIClientView view) {
        this.view = view;
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
