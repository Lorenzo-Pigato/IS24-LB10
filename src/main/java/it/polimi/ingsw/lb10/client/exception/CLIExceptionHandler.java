package it.polimi.ingsw.lb10.client.exception;

import it.polimi.ingsw.lb10.client.cli.clipages.CLI404Page;
import it.polimi.ingsw.lb10.client.cli.clipages.CLIErrorPage;
import it.polimi.ingsw.lb10.client.view.CLIClientView;

import java.io.IOException;
import java.net.UnknownHostException;

public class CLIExceptionHandler implements ExceptionHandler {
    private final CLIClientView view;

    public CLIExceptionHandler(CLIClientView view) {
        this.view = view;
    }

    public void handle(Exception e) {
        view.setPage(new CLIErrorPage());
        view.displayPage(new String[]{">> Server closed connection<<", e.getMessage()});
    }

    public void handle(UnknownHostException e) {
        view.setPage(new CLIErrorPage());
        view.displayPage(new String[]{">> Error closing sockets <<", e.getMessage()});
    }

    public void handle(IOException e) {
        view.setPage(new CLIErrorPage());
        view.displayPage(new String[]{">> Server closed connection <<", e.getMessage()});
    }

    public void handle(ConnectionErrorException e) {
        view.setPage(new CLI404Page());
        view.displayPage(new String[]{">> Server closed connection <<", e.getMessage()});
    }
}
