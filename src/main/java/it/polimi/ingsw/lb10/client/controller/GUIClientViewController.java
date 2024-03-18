package it.polimi.ingsw.lb10.client.controller;

import it.polimi.ingsw.lb10.client.Client;
import it.polimi.ingsw.lb10.client.view.GUIClientView;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class GUIClientViewController implements ClientViewController {

    private GUIClientView guiClientView;
    private Socket socket;
    private Client client;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;

    public GUIClientViewController(GUIClientView cliClientView, Socket socket, Client client) {
        this.guiClientView = cliClientView;
        this.socket = socket;
    }

    public void launch(){

    }

    public void setUp(){}
}
