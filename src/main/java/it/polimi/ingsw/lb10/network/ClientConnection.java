package it.polimi.ingsw.lb10.network;

import it.polimi.ingsw.lb10.network.requests.Request;
import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.RequestHandler;
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
    private static final RequestHandler visitor = new RequestHandler();

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

    public void run(){
        while(isActive()){
            try{
                Request request = (Request) (input.readObject());
                request.accept(visitor); /***/
                //IDEA PER MANDARE BENE LA RESPONSE: IL METODO ACCEPT RITORNA UNA RESPONSE GENERICA !!!!!!!!

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
            //System.out.println(">>>Server : error sending Response " + r.toString());
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

    /**
     * Sets up the streams to communicate with client
     */
    public void setUp(){
        try{
            input = new ObjectInputStream(socket.getInputStream());
            output = new ObjectOutputStream(socket.getOutputStream());
            output.flush();
        }catch(IOException e){
            close();
        }


    }
}