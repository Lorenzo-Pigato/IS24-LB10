package it.polimi.ingsw.lb10.network.response.match;

import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.model.Player;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.CLIResponseHandler;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.ResponseVisitor;

import java.util.ArrayList;
import java.util.Optional;

public class EndGameResponse extends Response {

    private Player player;
    private ArrayList<Player> players;

    public EndGameResponse(Player player, ArrayList<Player> players) {
        this.player = player;
        this.players = players;
    }

    @Override
    public void accept(ResponseVisitor handler) {
        handler.visit(this);
    }

    public Player getPlayer() {return player;}
    public ArrayList<Player> getPlayers() {return players;}
}