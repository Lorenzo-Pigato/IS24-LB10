package it.polimi.ingsw.lb10.client.controller;

import it.polimi.ingsw.lb10.client.Client;
import it.polimi.ingsw.lb10.client.exception.ConnectionErrorException;
import it.polimi.ingsw.lb10.network.requests.Request;

import java.net.Socket;

public interface ClientViewController {

    public void setSocket(Socket socket);

    public void setClient(Client client);


    /**This method closes all Socket streams used to communicate
     *
     */
    public void close();


    /**
     * This method is used to set up the communication streams with the Server.
     */
    public abstract void setUp();



    /**
     *  This asynchronous method provides a thread to send asynchronous requests to the network layer through
     *  serialization
     * @param message the request
     * @return the thread
     */
    public Thread asyncWriteToSocket(Request message);



    /**This method is used to set the view output changes
     * @param o a general parameter to show the output on the UI
     */
    public void showUserOutput(Object o);



    /**
     * This method provides the asynchronous functions to get the input in the UI
     */
    public void getUserInput();



    /** This method pro
     * @return the thread to be run
     */
    public Thread asyncReadFromSocket();


    Socket initializeConnection() throws ConnectionErrorException;

    public void errorPage();
}






