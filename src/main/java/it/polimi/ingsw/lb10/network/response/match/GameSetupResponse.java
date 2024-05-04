package it.polimi.ingsw.lb10.network.response.match;

import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.model.Player;
import it.polimi.ingsw.lb10.server.model.quest.Quest;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.ResponseVisitor;

import java.io.Serializable;
import java.util.ArrayList;

public class GameSetupResponse extends Response implements Serializable {
    private static final long serialVersionUID = 4L;
    private final ArrayList<Player> players;
    private final ArrayList<Quest> publicQuests;

    public GameSetupResponse(ArrayList<Player> players, ArrayList<Quest> publicQuest) {
        this.players = players;
        this.publicQuests = publicQuest;
    }

    @Override
    public void accept(ResponseVisitor handler) {
        handler.visit(this);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Quest> getPublicQuests() {
        return publicQuests;
    }
}
