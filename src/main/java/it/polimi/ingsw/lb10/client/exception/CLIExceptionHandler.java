package it.polimi.ingsw.lb10.client.exception;

import com.sun.tools.javac.Main;
import it.polimi.ingsw.lb10.client.cli.clipages.CLI404Page;
import it.polimi.ingsw.lb10.client.cli.clipages.CLIErrorPage;
import it.polimi.ingsw.lb10.client.cli.clipages.CLIWaitingPage;
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

    public synchronized void handle(Exception e) {
        view.setPage(new CLIErrorPage());
        view.displayPage(new String[]{">> Exception : <<", e.getMessage()});
        if(controller.getView().getPage() instanceof CLIWaitingPage) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public synchronized void handle(IOException e) {
        view.setPage(new CLIErrorPage());
        view.displayPage(new String[]{">> Server closed connection <<", e.getMessage()});
        if(controller.getView().getPage() instanceof CLIWaitingPage) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public synchronized void handle(ConnectionErrorException e) {
        view.setPage(new CLI404Page());
        view.displayPage(new String[]{">> Server closed connection <<", e.getMessage()});
        if(controller.getView().getPage() instanceof CLIWaitingPage) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public synchronized void handle(ConnectionTimedOutException e){
        view.setPage(new CLIErrorPage());
        view.displayPage(new String[]{">> Connection timed out<<", null});
        if(controller.getView().getPage() instanceof CLIWaitingPage) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
