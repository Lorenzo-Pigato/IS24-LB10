package it.polimi.ingsw.lb10.server.view;
import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.Server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;


/**
 * This class is used to update the client-side view on controller's update
 * has the OutputStream to send out responses to the client, it is a ResponseSendingVisitor !!!!
 */
public class RemoteView {
    private final int hashCode;
    private final Socket socket;
    private ObjectOutputStream outputStream;

    public RemoteView(Socket socket) {
        this.socket = socket;
        this.hashCode = socket.hashCode();
    }

    public Socket getSocket() {
        return socket;
    }

    public synchronized void send(Response r){
        System.out.println("SENDING " + r.getClass());
        try{
            outputStream.reset();
            outputStream.writeObject(r);
            outputStream.flush();
        }catch(IOException e){
            Server.log("Socket exception: " + e.getMessage());
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

    public void setUp() throws IOException{
        outputStream = new ObjectOutputStream(socket.getOutputStream());
       // outputStream.flush();
    }


}
