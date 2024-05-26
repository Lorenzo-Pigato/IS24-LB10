package it.polimi.ingsw.lb10.network.response.match;

import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.model.Player;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.ResponseVisitor;

import java.util.ArrayList;

public class EndGameResponse extends Response {

    private final Player player;
    private final ArrayList<Player> players;
    private final boolean matchStarted;

    public EndGameResponse(Player player, ArrayList<Player> players, boolean matchStarted) {
        this.player = player;
        this.players = players;
        this.matchStarted = matchStarted;
    }

    @Override
    public void accept(ResponseVisitor handler) {
        handler.visit(this);
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public boolean isMatchStarted() { return matchStarted;}
}
