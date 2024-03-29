package it.polimi.ingsw.lb10.client;

import it.polimi.ingsw.lb10.client.controller.ClientViewController;
import it.polimi.ingsw.lb10.client.exception.ConnectionErrorException;
import it.polimi.ingsw.lb10.client.exception.ExceptionHandler;
import it.polimi.ingsw.lb10.client.view.CLIClientView;

import java.net.Socket;

public class Client implements Runnable{
    //Client can choose from command line which type of interface he wants(TUI/GUI), in the ClientApp launcher
    //a command will be provided to choose the interface he wants and then pass it directly to the constructor, which will be
    //unaware of it

    private Socket socket;
    private final ClientViewController controller;
    private boolean active = true;

    public Client(ClientViewController controller) {
        this.controller = controller;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    /**
     * @param active sets the client state, which will be evaluated by communication threads
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    public void run(){
        //we have instantiated both view and view controller
        controller.setClient(this);

        //server connection
        try{
            Socket socket = controller.initializeConnection();
        }catch(ConnectionErrorException e){
            ExceptionHandler.handle(e, new CLIClientView());
            return;
        }

        setSocket(socket);

        //--------------- setup ----------------//
        controller.setUp();

        //-------------- login ----------------//
        controller.login();




    }

    /**
     * @return the client state
     */
    public synchronized boolean isActive() {
        return active;
    }
}
