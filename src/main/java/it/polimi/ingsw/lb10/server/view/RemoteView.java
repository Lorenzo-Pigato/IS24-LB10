package it.polimi.ingsw.lb10.server.view;

import it.polimi.ingsw.lb10.network.response.Response;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This class is used to update the client-side view on controller's update
 * has the OutputStream to send out responses to the client , is a ResponseSendingvisitor !!!!
 */
public class RemoteView  {

    private final Socket socket;
    private ObjectOutputStream outputStream;

    public RemoteView(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void send(Response r){
        try{
            outputStream.reset();
            outputStream.writeObject(r);
            outputStream.flush();
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

    public void setUp() throws IOException{
        outputStream = new ObjectOutputStream(socket.getOutputStream());
       // outputStream.flush();
    }


}
