package it.polimi.ingsw.lb10.network;

import it.polimi.ingsw.lb10.network.requests.Request;
import it.polimi.ingsw.lb10.server.controller.RequestHandler;
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
    private RequestHandler requestHandler = RequestHandler.instance();

    public ClientConnection(Socket socket, Server server){
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
        System.out.println(">>Server : new Client connected...\n");
        while(isActive()){
            try{
                Request request = (Request) (input.readObject());
                //requestHandler.handle(request); use Visitor Patter or Map<Class<? extends Request>, Consumer>

            }catch(Exception e){
                System.out.println(e.toString() + "occurred");
                setActive(false);
            }
            finally {
                close();
            }
        }
    }

    public void send(Response r){
        try{
            output.reset();
            output.writeObject(r);
            output.flush();
        }catch(IOException e){
            System.out.println(">>>Server : error sending Response " + r.toString());
        }
    }

    public void asyncSend(Response r){
        new Thread(new Runnable() {
            @Override
            public void run() {
                send(r);
            }
        }).start();
    }

}