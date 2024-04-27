package it.polimi.ingsw.lb10.client.controller;

import it.polimi.ingsw.lb10.client.Client;
import it.polimi.ingsw.lb10.client.exception.ConnectionErrorException;
import it.polimi.ingsw.lb10.client.view.CLIClientView;
import it.polimi.ingsw.lb10.network.requests.Request;
import it.polimi.ingsw.lb10.network.response.match.PrivateQuestsResponse;

import java.net.Socket;

public interface ClientViewController {

    void setSocket(Socket socket);

    void setClient(Client client);

    /**This method closes all Socket streams used to communicate
     *
     */
    void close();


    /**
     * This method is used to set up the communication streams with the Server.
     */
    void setUp();

    /**
     *  This asynchronous method provides a thread to send asynchronous requests to the network layer through
     *  serialization
     * @param message the request
     * @return the thread
     */
    Thread asyncWriteToSocket(Request message);



    /**This method is used to set the view output changes
     * @param o a general parameter to show the output on the UI
     */
    void showUserOutput(Object o);

    /** This method provides a thread to read asynchronously from the Socket
     * @return the thread to be run
     */
    Thread asyncReadFromSocket();


    public void initializeConnection() throws ConnectionErrorException;

    /**
     * Used to provide an input scanner for login requests, checking the string for the username
     * and sending it to the Server.
     * A Boolean Response is waited and handled , setting "logged" flag to true is response is positive
     */
    void login();

    void joinMatch();

    /**
     * Used to set the layout of the waiting room, state in which the player has already joined the match but the match
     * hasn't started yet.
     */
    void waitingRoom();

    /**
     * Used to get unique hashCode from server, this hashCode will be wrapped inside every request, so the Server controllers
     * can easily handle multiple requests from multiple clients.
     */
    void setHash();

    int getMatchId();

    void setMatchId(int id);

    void privateQuestSelection(PrivateQuestsResponse response);

    void gameStart();

    void game();

    boolean resourceDeckIsAvailable();
    boolean goldenDeckIsAvailable();
}







