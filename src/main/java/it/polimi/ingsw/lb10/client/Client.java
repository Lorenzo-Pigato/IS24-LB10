package it.polimi.ingsw.lb10.client;

import it.polimi.ingsw.lb10.client.controller.CLIClientViewController;
import it.polimi.ingsw.lb10.client.controller.ClientViewController;
import it.polimi.ingsw.lb10.client.exception.ConnectionErrorException;
import it.polimi.ingsw.lb10.client.exception.ExceptionHandler;
import it.polimi.ingsw.lb10.client.view.CLIClientView;


//Singleton?!

public class Client implements Runnable{
    //Client can choose from command line which type of interface he wants(TUI/GUI), in the ClientLauncher
    //a command will be provided to choose the interface he wants and then instantiating the right type of view and view controller

    private static Client instance;
    private  ClientViewController controller = CLIClientViewController.instance();
    private  boolean active = true;
    private  boolean logged = false;
    private  boolean inMatch = false;

    public static Client instance(){
        if(instance == null) instance =  new Client();
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
    public void run(){
        //we have instantiated both view and view controller
        //we set the client reference to our controller
        controller.setClient(this);
        //server connection
        try{
            controller.initializeConnection();
        }catch(ConnectionErrorException e){
            ExceptionHandler.handle(e, new CLIClientView());
            return;
        }

        //--------------- setup ----------------//
        controller.setUp();

        //---------------hash------------------//

        controller.setHash();

        //-------------- login ----------------//
        controller.login();

        //--------------match join -----------//
        controller.joinMatch();


    }

    /**
     * @return the client state
     */
    public  synchronized boolean isActive() {
        return active;
    }

    public  boolean isLogged(){
        return logged;
    }

    public boolean isInMatch(){
        return inMatch;
    }

    public void setLogged(Boolean state){
        logged = state;
    }
    public void setInMatch(Boolean state){
        System.out.println("gianluigi");inMatch = state; }

    /**
     * @param active sets the client state, which will be evaluated by communication threads
     */
    public  void setActive(boolean active) {
        this.active = active;
    }

    public void setController(ClientViewController controller) {
        this.controller = controller;
    }



}

