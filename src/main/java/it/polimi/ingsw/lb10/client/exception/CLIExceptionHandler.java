package it.polimi.ingsw.lb10.client.exception;

import it.polimi.ingsw.lb10.client.cli.clipages.CLI404Page;
import it.polimi.ingsw.lb10.client.cli.clipages.CLIErrorPage;
import it.polimi.ingsw.lb10.client.cli.clipages.CLIPage;
import it.polimi.ingsw.lb10.client.cli.clipages.CLIWaitingPage;
import it.polimi.ingsw.lb10.client.controller.CLIClientViewController;
import it.polimi.ingsw.lb10.client.view.CLIClientView;

import java.io.IOException;

public class CLIExceptionHandler implements ExceptionHandler {
    private final CLIClientView view;
    private final CLIClientViewController controller = CLIClientViewController.instance();

    public CLIExceptionHandler(CLIClientView view) {
        this.view = view;
    }

    public synchronized void handle(Exception e) {
        CLIPage previousPage = view.getPage();
        view.setPage(new CLIErrorPage());
        view.displayPage(new String[]{">> Exception : <<", e.getMessage()});
        if(previousPage instanceof CLIWaitingPage) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public synchronized void handle(IOException e) {
        CLIPage previousPage = view.getPage();
        view.setPage(new CLIErrorPage());
        view.displayPage(new String[]{">> Server closed connection <<", e.getMessage()});
        if(previousPage instanceof CLIWaitingPage) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public synchronized void handle(ConnectionErrorException e) {
        CLIPage previousPage = view.getPage();
        view.setPage(new CLI404Page());
        view.displayPage(new String[]{">> Server closed connection <<", e.getMessage()});
        if(previousPage instanceof CLIWaitingPage) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public synchronized void handle(ConnectionTimedOutException e){
        CLIPage previousPage = view.getPage();
        view.setPage(new CLIErrorPage());
        view.displayPage(new String[]{">> Connection timed out<<", null});
        if(previousPage instanceof CLIWaitingPage) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
            }
        }
    }
}
