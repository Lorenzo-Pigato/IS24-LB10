package it.polimi.ingsw.lb10.client.exception;

import com.sun.tools.javac.Main;
import it.polimi.ingsw.lb10.client.cli.clipages.CLI404Page;
import it.polimi.ingsw.lb10.client.cli.clipages.CLIErrorPage;
import it.polimi.ingsw.lb10.client.controller.CLIClientViewController;
import it.polimi.ingsw.lb10.client.view.CLIClientView;

import java.io.EOFException;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class CLIExceptionHandler implements ExceptionHandler {
    private final CLIClientView view;
    private final CLIClientViewController controller = CLIClientViewController.instance();

    public CLIExceptionHandler(CLIClientView view) {
        this.view = view;
    }

    public void handle(Exception e) {
        view.setPage(new CLIErrorPage());
        view.displayPage(new String[]{">> Exception : <<", e.getMessage()});
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

    @Override
    public void handle(SocketException e) {

    }

    @Override
    public void handle(EOFException e) {
        view.setPage(new CLIErrorPage());
        view.displayPage(new String[]{">> Server closed connection<<", null});
    }

    @Override
    public void handle(ConnectionTimedOutException e){
        view.setPage(new CLIErrorPage());
        view.displayPage(new String[]{">> Connection timed out<<", null});
    }
}
