package it.polimi.ingsw.lb10.client.exception;

import it.polimi.ingsw.lb10.client.cli.clipages.CLI404Page;
import it.polimi.ingsw.lb10.client.cli.clipages.CLIErrorPage;
import it.polimi.ingsw.lb10.client.view.ClientView;

import java.io.IOException;
import java.net.UnknownHostException;

public interface ExceptionHandler {

    void handle(Exception e);

    void handle(UnknownHostException e);

    void handle(IOException e);

    void handle(ConnectionErrorException e);
}
