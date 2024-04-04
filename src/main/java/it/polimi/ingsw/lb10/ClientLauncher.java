package it.polimi.ingsw.lb10;

import it.polimi.ingsw.lb10.client.Client;
import it.polimi.ingsw.lb10.client.controller.CLIClientViewController;
import it.polimi.ingsw.lb10.client.controller.GUIClientViewController;
import it.polimi.ingsw.lb10.client.view.CLIClientView;
import it.polimi.ingsw.lb10.client.view.GUIClientView;
import org.jetbrains.annotations.NotNull;

public class ClientLauncher {
    /**
     * Main method launches the Client thread after parsing args passed by Launcher to determine
     * if cli or gui should be used.
     * @param args client:ui - ui can be "cli" or "gui"
     */

    public static void main( String @NotNull [] args )
    {
        Client client = Client.instance();
        if(args[1].equals("cli")){
            CLIClientViewController controller = CLIClientViewController.instance();
            controller.setCliClientView(new CLIClientView());
            client.setController(controller);
        }else{
            GUIClientViewController controller = GUIClientViewController.instance();
            controller.setGuiClientView(new GUIClientView());
            client.setController(controller);
        }

        final Thread clientThread = new Thread(Client.instance());
        clientThread.start();
    }
}
