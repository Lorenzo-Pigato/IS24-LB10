package it.polimi.ingsw.lb10.client.controller;

import it.polimi.ingsw.lb10.client.Client;
import it.polimi.ingsw.lb10.client.exception.ConnectionErrorException;
import it.polimi.ingsw.lb10.client.view.CLIClientView;
import it.polimi.ingsw.lb10.network.requests.Request;

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



    /**
     * This method provides the asynchronous functions to get the input in the UI
     */
    void getUserInput();



    /** This method provides a thread to read asynchronously from the Socket
     * @return the thread to be run
     */
    Thread asyncReadFromSocket();


    public void initializeConnection() throws ConnectionErrorException;

    void login();

    /**
     * Used to get unique hashCode from server, this hashCode will be wrapped inside every request, so the Server controllers
     * can easily handle multiple requests from multiple clients.
     */
    void setHash();
}






