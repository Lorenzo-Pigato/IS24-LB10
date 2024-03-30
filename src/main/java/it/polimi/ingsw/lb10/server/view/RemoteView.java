package it.polimi.ingsw.lb10.server.view;

import it.polimi.ingsw.lb10.network.ClientConnection;
import it.polimi.ingsw.lb10.network.requests.Request;
import it.polimi.ingsw.lb10.network.response.BooleanResponse;
import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.model.Player;
import it.polimi.ingsw.lb10.server.visitors.responseSender.ResponseSendingVisitor;
import it.polimi.ingsw.lb10.util.Observable;
import it.polimi.ingsw.lb10.util.Observer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This class is used to update the client-side view on controller's update
 * has the OutputStream to send out responses to the client , is a ResponseSendingvisitor !!!!
 */
public class RemoteView implements ResponseSendingVisitor {

    private final Socket socket;
    private ObjectOutputStream outputStream;

    public RemoteView(Socket socket) {
        this.socket = socket;
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

    @Override
    public void visit(BooleanResponse br) {

    }


}
