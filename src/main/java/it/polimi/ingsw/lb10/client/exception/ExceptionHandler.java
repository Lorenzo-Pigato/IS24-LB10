package it.polimi.ingsw.lb10.client.exception;

import it.polimi.ingsw.lb10.client.cli.clipages.CLIErrorPage;
import it.polimi.ingsw.lb10.client.view.ClientView;

import java.io.IOException;
import java.net.UnknownHostException;

public abstract class ExceptionHandler {

    public static void handle(Exception e, ClientView view){
        view.setPage(new CLIErrorPage());
        view.pageStateDisplay(new CLIErrorPage.Default(), new String[]{">> Error closing sockets <<", e.getMessage()});
    }

    public static void handle(UnknownHostException e){

    }

    public static void handle(IOException e, ClientView view){
        view.setPage(new CLIErrorPage());
        view.pageStateDisplay(new CLIErrorPage.Default(), new String[] {">> Error closing sockets <<", e.getMessage()});
    }
}
