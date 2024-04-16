package it.polimi.ingsw.lb10.server.model;

import it.polimi.ingsw.lb10.network.requests.Request;
import it.polimi.ingsw.lb10.util.Observable;

import java.util.ArrayList;
import java.util.List;

public class MatchModel extends Observable<Request> {

    private final int id;
    private List<Player> players = new ArrayList<>();
    private final int numberOfPlayers;

    public MatchModel(int id, int numberOfPlayers) {
        this.id = id;
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getId() {
        return id;
    }

    @Override
    public void notify(Request request) {
        super.notify(request);
    }

}
