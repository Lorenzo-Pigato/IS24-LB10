package it.polimi.ingsw.lb10;

import it.polimi.ingsw.lb10.client.Client;
import it.polimi.ingsw.lb10.client.cli.CLICommand;
import it.polimi.ingsw.lb10.client.controller.CLIClientViewController;
import it.polimi.ingsw.lb10.client.exception.CLIExceptionHandler;
import it.polimi.ingsw.lb10.client.view.CLIClientView;
import it.polimi.ingsw.lb10.client.view.GUIClientView;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.CLIResponseHandler;
import javafx.application.Application;
import org.jetbrains.annotations.NotNull;

public class ClientLauncher {
    /**
     * Main method launches the Client thread after parsing args passed by Launcher to determine
     * if cli or gui should be used.
     *
     * @param args client:ui - ui can be "cli" or "gui"
     */

    public static void main(String @NotNull [] args) {

        if (args[1].equals("cli")) {
            Client client = Client.instance();
            CLIClientViewController controller = CLIClientViewController.instance();
            controller.setCliClientView(new CLIClientView());
            controller.setExceptionHandler(new CLIExceptionHandler(controller.getView()));
            client.setController(controller);
            client.setExceptionHandler(new CLIExceptionHandler(controller.getView()));
            controller.setResponseHandler(CLIResponseHandler.instance());
            final Thread clientThread = new Thread(Client.instance());
            clientThread.start();

        } else {
            CLICommand.setInvisibleInput();
            Application.launch(GUIClientView.class);
        }


    }
}
