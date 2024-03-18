package it.polimi.ingsw.lb10.client.util;

import it.polimi.ingsw.lb10.client.Client;
import it.polimi.ingsw.lb10.client.controller.CLIClientViewController;
import it.polimi.ingsw.lb10.client.controller.ClientViewController;
import it.polimi.ingsw.lb10.client.controller.GUIClientViewController;
import it.polimi.ingsw.lb10.client.view.CLIClientView;
import it.polimi.ingsw.lb10.client.view.ClientView;
import it.polimi.ingsw.lb10.client.view.GUIClientView;

import java.net.Socket;

/**
 * this class implements the Factory design pattern to instantiate a ClientViewController based on the type of view required by the client
 * in the ClientApp (GUI/TUI)
 */

public class ClientViewControllerFactory {

    public static ClientViewController getClientViewController(ClientView view, Client client, Socket socket){
        if(view instanceof CLIClientView) return new CLIClientViewController((CLIClientView) view, socket, client);
        else return new GUIClientViewController((GUIClientView) view, socket, client);

    }

}
