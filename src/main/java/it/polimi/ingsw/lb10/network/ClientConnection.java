package it.polimi.ingsw.lb10.network;

import it.polimi.ingsw.lb10.network.requests.Request;
import it.polimi.ingsw.lb10.network.response.lobby.HashResponse;
import it.polimi.ingsw.lb10.server.controller.LobbyController;
import it.polimi.ingsw.lb10.server.view.RemoteView;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.LobbyRequestVisitor;
import it.polimi.ingsw.lb10.util.Observable;
import it.polimi.ingsw.lb10.server.Server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientConnection extends Observable implements Runnable {

    private final Socket socket;
    private ObjectInputStream input;
    private Boolean active = true;
    private Server server;
    private final RemoteView remoteView;
    private LobbyRequestVisitor requestHandler;
    private final int userHash;


    public ClientConnection(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
        this.remoteView = new RemoteView(socket);
        this.requestHandler = LobbyController.instance();
        LobbyController.addRemoteView(remoteView);
         userHash = socket.hashCode();
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
            e.printStackTrace();
        }
    }

    public void run(){
        setUp();//creates input Stream
        try{
            remoteView.setUp();  //sets up remote view opening output streams
            LobbyController.addRemoteView(remoteView);
        }catch(IOException e){
            close(); //closes the socket, client will handle an IOException
        }
        remoteView.send(new HashResponse(socket.hashCode()));
        Server.log(">>new client, assigned hashcode: " + socket.hashCode());
        while(isActive()){
            try{
                Request request = (Request) (input.readObject());
                request.accept(requestHandler);
            }catch(Exception e){
                Server.log(">>client " + userHash + " closed connection");
                LobbyController.disconnectClient(userHash); //disconnects client from lobby, which deletes player from match too!
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

    public int getUserHash() {
        return userHash;
    }

}