package it.polimi.ingsw.lb10.server.view;

import it.polimi.ingsw.lb10.network.ClientConnection;
import it.polimi.ingsw.lb10.network.Request;
import it.polimi.ingsw.lb10.server.model.Player;
import it.polimi.ingsw.lb10.util.Observable;
import it.polimi.ingsw.lb10.util.Observer;

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
