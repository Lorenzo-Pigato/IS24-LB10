package it.polimi.ingsw.lb10.server;
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

        while(true){
            try {
                System.out.println("Server listening on port: " + port);
                Socket clientSocket = serverSocket.accept();
                welcomeExecutor.submit(new ClientConnection(clientSocket, this));
            }catch(IOException e){
                System.out.println(">>>Server: Couldn't connect to Client...");
            }
        }
    }

}
