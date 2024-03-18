package org.example.server.view;

import org.example.network.ClientConnection;
import org.example.network.Request;
import org.example.server.model.Player;
import org.example.util.Observable;
import org.example.util.Observer;

/**
 * This class is used to update the client-side on controller's update
 */
public class RemoteView extends Observable<Request> implements Observer<Request> {

    private Player player;
    private ClientConnection clientConnection;

    @Override
    //Gets updated by the Model
    public void update(Request request) {

    }

    @Override
    //Notifies ClientView
    public void notify(Request request) {

    }
}
