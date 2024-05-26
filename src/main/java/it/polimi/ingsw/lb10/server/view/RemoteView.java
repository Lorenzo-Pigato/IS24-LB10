package it.polimi.ingsw.lb10.server.view;

import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.controller.LobbyController;
import it.polimi.ingsw.lb10.util.Observer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;


/**
 * This class is used to update the client-side view on controller's update
 * has the OutputStream to send out responses to the client.
 */
public class RemoteView implements Observer {

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

    public synchronized void send(Response r) {
        try {
            outputStream.reset();
            outputStream.writeObject(r);
            outputStream.flush();
        } catch (IOException e) {
            LobbyController.disconnectClient(hashCode);
        }
    }

    public void setUp() throws IOException {
        outputStream = new ObjectOutputStream(socket.getOutputStream());
    }


    /**
     * @param response server response to send via remote view
     */
    @Override
    public void update(Response response) {
        send(response);
    }

    /**
     * @param response server response to send via remote view
     * @param userHash userHash of the receiving client
     */
    @Override
    public void updateConditional(Response response, int userHash) {
        if (hashCode == userHash) send(response);

    }
}
