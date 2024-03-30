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
        Client client;
        if(args[1].equals("cli")){
            client = new Client(new CLIClientViewController(new CLIClientView()));
        }else{
            client = new Client(new GUIClientViewController(new GUIClientView()));
        }

        final Thread clientThread = new Thread(client);
        clientThread.start();
    }
}
