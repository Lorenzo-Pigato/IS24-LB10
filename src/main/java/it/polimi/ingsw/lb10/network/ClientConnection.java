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

        remoteView.send(new HashResponse(socket.hashCode()));

        while(isActive()){
            try{
                Request request = (Request) (input.readObject());
                request.accept(requestHandler);

            }catch(Exception e){
                System.out.println(e.toString() + "occurred");
                setActive(false);
            }
            finally {
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