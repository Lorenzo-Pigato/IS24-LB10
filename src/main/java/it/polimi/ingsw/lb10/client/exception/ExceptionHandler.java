package it.polimi.ingsw.lb10.client.exception;

import java.io.IOException;

public interface ExceptionHandler {

    void handle(Exception e);

    void handle(IOException e);

    void handle(ConnectionErrorException e);

    void handle (ConnectionTimedOutException e);
}
