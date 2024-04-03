package it.polimi.ingsw.lb10.server;
import it.polimi.ingsw.lb10.client.cli.CLIBanner;
import it.polimi.ingsw.lb10.client.cli.CLIBox;
import it.polimi.ingsw.lb10.client.cli.CLICommand;
import it.polimi.ingsw.lb10.client.cli.CLIString;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiFormat;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiString;
import it.polimi.ingsw.lb10.network.ClientConnection;
import it.polimi.ingsw.lb10.server.controller.LobbyController;
import it.polimi.ingsw.lb10.server.model.MatchModel;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable{
    private final ServerSocket serverSocket;
    private final int port;
    private final ExecutorService welcomeExecutor;


    public Server(ServerSocket serverSocket, int port){
        this.serverSocket = serverSocket;
        this.port = port;
        this.welcomeExecutor = Executors.newCachedThreadPool();
    }

    public int getPort() {
        return port;
    }

    //starts the Server thread listening to all client connections, then sends socket client to connection handlers
    public void run(){
        setPage();
        while(true){
            try {
                Socket clientSocket = serverSocket.accept();
                welcomeExecutor.submit(new ClientConnection(clientSocket, this));
            }catch(IOException e){
                displayError();
                CLICommand.setPosition(2, 49);
                AnsiString.print(">> Error: " + e.getMessage(), AnsiColor.RED);
            }
        }
    }

    private void setPage() {
        CLICommand.initialize(80, 50);
        CLIBanner.displayServer();
        CLIBox.draw(1,15,80,30, AnsiColor.CYAN);

        CLICommand.setPosition(2,14);
        AnsiString.print(">> Server running on port: ", AnsiColor.CYAN);
        AnsiString.print(this.getPort() + "\t", AnsiColor.YELLOW, AnsiFormat.BOLD);

        AnsiString.print("Status: ", AnsiColor.CYAN);
        AnsiString.print("ONLINE", AnsiColor.GREEN, AnsiFormat.BOLD);

        CLICommand.setInvisibleInput();
    }

    private void displayError(){
        CLICommand.home();
        CLICommand.clearNextLines(14);
        CLIBanner.displayError(17,2);

        CLICommand.setPosition(2, 14);

        AnsiString.print(">> Server running on port: ", AnsiColor.CYAN);
        AnsiString.print(this.getPort() + "\t", AnsiColor.YELLOW, AnsiFormat.BOLD);

        AnsiString.print("Status: ", AnsiColor.CYAN);
        AnsiString.print("OFFLINE", AnsiColor.RED, AnsiFormat.BOLD);

        CLICommand.resetInvisibleInput();
    }

}
