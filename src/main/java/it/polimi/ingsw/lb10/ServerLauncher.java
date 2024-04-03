package it.polimi.ingsw.lb10;

import it.polimi.ingsw.lb10.server.Server;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerLauncher {
    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[1]));
            Thread serverThread = new Thread(new Server(serverSocket, Integer.parseInt(args[1])));
            serverThread.start();
            serverThread.join();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
