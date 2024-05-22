package it.polimi.ingsw.lb10.client;

import it.polimi.ingsw.lb10.client.controller.ClientViewController;
import it.polimi.ingsw.lb10.client.exception.ConnectionErrorException;
import it.polimi.ingsw.lb10.client.exception.ExceptionHandler;
import it.polimi.ingsw.lb10.network.heartbeat.ClientHeartBeatHandler;

import java.util.Scanner;


//Singleton?!

public class Client implements Runnable {
    //Client can choose from command line which type of interface he wants(TUI/GUI), in the ClientLauncher
    //a command will be provided to choose the interface he wants and then instantiating the right type of view and view controller

    private static Client instance;
    private ClientViewController controller;
    private boolean active = true;
    private boolean logged = false;
    private boolean inMatch = false;
    private boolean startedMatch = false;
    private String username;

    private ExceptionHandler exceptionHandler;

    public static Client instance() {
        if (instance == null) instance = new Client();
        return instance;
    }

    /**
     * this is the main running thread on client. It is divided in different parts, the first one is used to reference the client object
     * to the controller once the client has been built,
     * then we get to instantiate the socket by connecting to the server
     * once the socket is "online", the controller must set up all of his streams and thread to communicate.
     * We then send, via controller, the login request to the server, and then we can proceed to the lobby.
     * all of these processes are procedural.
     */
    public void run() {
        //instantiated both view and view controller
        //Set client reference to the controller
        controller.setClient(this);
        //server connection

        try {
            controller.initializeConnection();
        } catch (ConnectionErrorException e) {
            exceptionHandler.handle(e);
            return;
        }

        //--------------- setup ----------------//
        controller.setUp();
        //---------------hash------------------//
        controller.setHash();
        Thread socketReader = controller.asyncReadFromSocket();
        socketReader.start();
        heartBeatSetUp();

        //-------------- login ----------------//
        controller.login();

        //--------------match join -----------//
        controller.joinMatch();

        //--------------wait start------------//
        controller.waitingRoom();

        if (active) {
            controller.game();
        }

    }

    private void heartBeatSetUp() {
        ClientHeartBeatHandler.setController(controller);
        ClientHeartBeatHandler.start();
    }


    //-------------- GETTERS -------------- //

    /**
     * @return the client state
     */
    public synchronized boolean isActive() {
        return active;
    }

    public synchronized boolean isNotLogged() {
        return !logged;
    }

    public synchronized boolean isNotInMatch() {
        return !inMatch;
    }

    public synchronized String getUsername(){return username;}

    public boolean matchIsStarted() { return startedMatch; }


    //-------------- SETTERS -------------- //

    public synchronized void setLogged(Boolean state) {
        logged = state;
    }

    public synchronized void setInMatch(Boolean state) {
        inMatch = state;
    }

    public synchronized void setUsername(String username){ this.username = username;}

    /**
     * @param active sets the client state, which will be evaluated by communication threads
     */
    public synchronized void setActive(boolean active) {
        this.active = active;
    }

    public void setController(ClientViewController controller) {
        this.controller = controller;
    }

    public void setExceptionHandler(ExceptionHandler exceptionHandler) {this.exceptionHandler = exceptionHandler;}

    public void setStartedMatch(boolean startedMatch) { this.startedMatch = startedMatch; }

}

