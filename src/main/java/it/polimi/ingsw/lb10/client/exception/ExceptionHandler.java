package it.polimi.ingsw.lb10.client.exception;

import java.io.EOFException;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public interface ExceptionHandler {

    void handle(Exception e);

    void handle(IOException e);

    void handle(ConnectionErrorException e);

    void handle (ConnectionTimedOutException e);
}
