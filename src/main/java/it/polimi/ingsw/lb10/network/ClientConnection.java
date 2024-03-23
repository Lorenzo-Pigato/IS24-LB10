package it.polimi.ingsw.lb10.network;

import it.polimi.ingsw.lb10.util.Observable;
import it.polimi.ingsw.lb10.server.Server;
import it.polimi.ingsw.lb10.server.controller.MatchController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnection extends Observable<Request> implements Runnable {

    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Boolean active = true;
    private Server server;
    private MatchController matchController;

    public ClientConnection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean isActive() {
        return active;
    }

    public void close(){
        try{
            input.close();
            output.close();
        }catch(IOException e){
            close();
        }
    }
    //the idea is: Client has an asynchronous stream of requests to send via his view, so server just waits for them
    //and handles them transparently via a handler, which reacts using CONTROLLER and MODEL just by evaluating
    // the object Request type

    public void run(){

        System.out.println(">>>Server : new Client connected...\n");

        while(isActive()){
            try{
                Request request = (Request) (input.readObject());
                //handle

            }catch(Exception e){
                System.out.println(e.toString() + "occurred");
                setActive(false);
            }
            finally {
                close();
            }
        }

    }

    public void send(Request r){
        try{
            output.reset();
            output.writeObject(r);
            output.flush();
        }catch(IOException e){
            System.out.println(">>>Server : error sending Response " + r.toString());
        }
    }

    public void asyncSend(Request r){
        new Thread(new Runnable() {
            @Override
            public void run() {
                send(r);
            }
        }).start();
    }

    public void login(){
        //String username = showLoginModel(); sends a response to the client which contains login form
        //Player player = new Player(username);
    }

    public void lobby(){

        //***this methods must access the Server object in SYNCHRONIZED mode to check match-IDs

        //String matchId = lobbyForm(); sends a response to the client containing
        //match-code lobby and returns the match controller to be joined

        //matchController = join(matchId);
        //if the match-id already exists, this method just returns it, if it doesn't, this method must generate a new
        //matchModel and a new matchController
    }

}