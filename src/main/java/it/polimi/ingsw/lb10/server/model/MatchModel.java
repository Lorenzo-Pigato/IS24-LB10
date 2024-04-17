package it.polimi.ingsw.lb10.server.model;

import it.polimi.ingsw.lb10.network.requests.Request;
import it.polimi.ingsw.lb10.util.Observable;

import java.util.ArrayList;
import java.util.List;

public class MatchModel extends Observable<Request> {

    private List<Player> players = new ArrayList<>();
    private final int numberOfPlayers;

    public MatchModel(int numberOfPlayers ) {
        this.numberOfPlayers = numberOfPlayers;
    }


    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    @Override
    public void notify(Request request) {
        super.notify(request);
    }

}
