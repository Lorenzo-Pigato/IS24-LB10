package it.polimi.ingsw.lb10;

import it.polimi.ingsw.lb10.client.Client;
import it.polimi.ingsw.lb10.client.clidesign.clipages.CLI404Page;
import it.polimi.ingsw.lb10.client.clidesign.clipages.CLIConnectionPage;
import it.polimi.ingsw.lb10.client.controller.CLIClientViewController;
import it.polimi.ingsw.lb10.client.controller.GUIClientViewController;
import it.polimi.ingsw.lb10.client.view.CLIClientView;
import it.polimi.ingsw.lb10.client.view.ClientView;
import it.polimi.ingsw.lb10.client.view.GUIClientView;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientLauncher {
    /**
     * shows first output, asks for CLI/GUI, port, ip and builds the client(Socket socket, int port, String ip)
     * checks the IOException while creating socket, the client has to run independently
     * @param args cli/gui user choice
     */

    public static void main( String[] args )
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

    public static String showConfig(){
        return null;
    }

    public static boolean parseConfig(String config){

        return true;
    }




}
