package it.polimi.ingsw.lb10.client.controller;

import it.polimi.ingsw.lb10.client.exception.ConnectionErrorException;
import it.polimi.ingsw.lb10.client.view.GUIClientView;
import it.polimi.ingsw.lb10.client.Client;
import it.polimi.ingsw.lb10.network.requests.Request;
import it.polimi.ingsw.lb10.network.response.match.PrivateQuestsResponse;

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
    private int matchId;

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

    /**
     * @return 
     */
    @Override
    public int getMatchId() {
        return matchId;
    }

    /**
     * @param id 
     */
    @Override
    public void setMatchId(int id) {
        this.matchId = matchId;
    }

    /**
     * @param response 
     */
    @Override
    public void privateQuestSelection(PrivateQuestsResponse response) {

    }

    /**
     *
     */
    @Override
    public void gameStart() {

    }

    /**
     *
     */
    @Override
    public void game() {

    }

    /**
     * @return 
     */
    @Override
    public boolean resourceDeckIsAvailable() {
        return false;
    }

    /**
     * @return 
     */
    @Override
    public boolean goldenDeckIsAvailable() {
        return false;
    }

    @Override
    public void joinMatch() {

    }

    @Override
    public void waitingRoom(){}

    public void setGuiClientView(GUIClientView guiClientView) {
        this.guiClientView = guiClientView;
    }
}
