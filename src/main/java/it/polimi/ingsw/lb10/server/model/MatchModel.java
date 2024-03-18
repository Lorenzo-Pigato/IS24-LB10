package it.polimi.ingsw.lb10.server.model;

import it.polimi.ingsw.lb10.network.Request;
import it.polimi.ingsw.lb10.util.Observable;

import java.util.ArrayList;
import java.util.List;

public class MatchModel extends Observable<Request>{

    private final String id;
    private List<Player> players = new ArrayList<>();
    private final int numberOfPlayers;

    public MatchModel(String id, int numberOfPlayers) {
        this.id = id;
        this.numberOfPlayers = numberOfPlayers;
    }

    @Override
    public void notify(Request request) {
        super.notify(request);
    }

}
