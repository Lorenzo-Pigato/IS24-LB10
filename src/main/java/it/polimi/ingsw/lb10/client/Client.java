package it.polimi.ingsw.lb10.client;

import it.polimi.ingsw.lb10.client.controller.ClientViewController;
import it.polimi.ingsw.lb10.client.util.ClientViewControllerFactory;
import it.polimi.ingsw.lb10.client.view.ClientView;
import java.net.Socket;

public class Client implements Runnable{
    //Client can choose from command line which type of interface he wants(TUI/GUI), in the ClientApp launcher
    //a command will be provided to choose the interface he wants and then pass it directly to the constructor, which will be
    //unaware of it

    private int port;
    private String ip;
    private ClientView view;
    private boolean active = true;

    public Client(int port, String ip, ClientView view) {
        this.port = port;
        this.ip = ip;
        this.view = view;
    }

    /**
     * @param active sets the client state, which will be evaluated by communication threads
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    public void run(){
        try{
            Socket socket = new Socket(ip, port);
            ClientViewController clientViewController = ClientViewControllerFactory.getClientViewController(view, this, socket);
            clientViewController.launch();
        }catch(Exception e){
            //handle error (close)
        }
    }

    /**
     * @return the client state
     */
    public synchronized boolean isActive() {
        return active;
    }
}
