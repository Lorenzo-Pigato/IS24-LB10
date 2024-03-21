package it.polimi.ingsw.lb10.server.model;

import it.polimi.ingsw.lb10.network.ClientConnection;

public class Player {
    private String username;
    private ClientConnection clientConnection;

    public Player(String username, ClientConnection clientConnection) {
        this.username = username;
        this.clientConnection = clientConnection;
    }

    public String getUsername() {
        return username;
    }
}
