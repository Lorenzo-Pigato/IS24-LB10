package it.polimi.ingsw.lb10;

import it.polimi.ingsw.lb10.client.cli.CLIBanner;
import it.polimi.ingsw.lb10.client.cli.CLICommand;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiFormat;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiString;
import it.polimi.ingsw.lb10.server.Server;

import java.net.ServerSocket;

public class ServerLauncher {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[1]));
            Thread serverThread = new Thread(new Server(serverSocket, Integer.parseInt(args[1])));
            serverThread.start();
            serverThread.join();
        } catch (Exception e) {
            CLICommand.initialize(80, 50);
            CLIBanner.displayError(17, 2);

            CLICommand.setPosition(2, 14);

            AnsiString.print(">> Server running on port: ", AnsiColor.CYAN);
            AnsiString.print(args[1] + "\t", AnsiColor.YELLOW, AnsiFormat.BOLD);

            AnsiString.print("Status: ", AnsiColor.CYAN);
            AnsiString.print("OFFLINE", AnsiColor.RED, AnsiFormat.BOLD);

            CLICommand.setPosition(2, 17);
            AnsiString.print(">> Error: " + e.getMessage() + "\n\n", AnsiColor.RED);
        }
    }
}
