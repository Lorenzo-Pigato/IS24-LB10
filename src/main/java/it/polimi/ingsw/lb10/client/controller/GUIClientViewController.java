package it.polimi.ingsw.lb10.client.controller;

import it.polimi.ingsw.lb10.client.exception.ConnectionErrorException;
import it.polimi.ingsw.lb10.client.view.GUIClientView;
import it.polimi.ingsw.lb10.client.Client;
import it.polimi.ingsw.lb10.network.requests.Request;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class GUIClientViewController implements ClientViewController {

    private static GUIClientViewController instance;
    private GUIClientView guiClientView;
    private Socket socket;
    private Client client;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;

    public static GUIClientViewController instance (){
        if(instance == null) return new GUIClientViewController();
        return instance;
    }

    @Override
    public void setSocket(Socket socket) {

    }

    @Override
    public void setClient(Client client) {

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


    @Override
    public void initializeConnection() throws ConnectionErrorException {

    }

    @Override
    public void login() {

    }

    @Override
    public void setHash() {

    }

    @Override
    public void joinMatch() {

    }

    public void setGuiClientView(GUIClientView guiClientView) {
        this.guiClientView = guiClientView;
    }
}
