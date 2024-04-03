package it.polimi.ingsw.lb10.network;

import it.polimi.ingsw.lb10.network.requests.Request;
import it.polimi.ingsw.lb10.network.response.HashResponse;
import it.polimi.ingsw.lb10.server.controller.LobbyController;
import it.polimi.ingsw.lb10.server.view.RemoteView;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.RequestVisitor;
import it.polimi.ingsw.lb10.util.Observable;
import it.polimi.ingsw.lb10.server.Server;
import it.polimi.ingsw.lb10.server.controller.MatchController;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientConnection extends Observable<Request> implements Runnable {

    private Socket socket;
    private ObjectInputStream input;
    private Boolean active = true;
    private Server server;
    private MatchController matchController;
    private final RemoteView remoteView;
    private final RequestVisitor requestHandler;


    public ClientConnection(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
        this.remoteView = new RemoteView(socket);
        this.requestHandler = LobbyController.instance();
        LobbyController.addRemoteView(remoteView);
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
        }catch(IOException e){
            close();
        }
    }

    public void run(){

        setUp();//creates input Stream
        try{
            remoteView.setUp();  //sets up remote view opening output streams
        }catch(IOException e){
            close(); //closes the socket, client will handle an IOException
        }

        remoteView.send(new HashResponse(socket.hashCode()));
        Server.log(">> New client, assigned hashcode: " + socket.hashCode());

        while(isActive()){
            try{
                Request request = (Request) (input.readObject());
                Server.log(">> " + request.getHashCode() + ": sent new request");
                request.accept(requestHandler);

            }catch(Exception e){
                Server.log(">> Exception: "+ e.toString() + " occurred");
                setActive(false);
                close();
            }
        }
    }

    /**
     * Sets up the streams to communicate with client
     */
    public void setUp(){
        try{
            input = new ObjectInputStream(socket.getInputStream());
        }catch(IOException e){
            close();
        }
    }

}