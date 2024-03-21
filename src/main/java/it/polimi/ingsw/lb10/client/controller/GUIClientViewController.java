package it.polimi.ingsw.lb10.client.controller;

import it.polimi.ingsw.lb10.client.view.GUIClientView;
import it.polimi.ingsw.lb10.client.Client;
import it.polimi.ingsw.lb10.network.requests.Request;

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

    @Override
    public void close() {

    }

    public void setUp(){}

    @Override
    public Thread asyncWriteToSocket(Request message) {
        return null;
    }

    @Override
    public void showUserOutput(Object o) {

    }

    @Override
    public void getUserInput() {

    }

    @Override
    public Thread asyncReadFromSocket() {
        return null;
    }

}
