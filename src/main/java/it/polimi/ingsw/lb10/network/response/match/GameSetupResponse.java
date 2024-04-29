package it.polimi.ingsw.lb10.network.response.match;

import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.model.Player;
import it.polimi.ingsw.lb10.server.model.quest.Quest;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.CLIResponseHandler;

import java.util.ArrayList;

public class GameSetupResponse extends Response {

    private final Player player;
    private final ArrayList<Quest> publicQuests;

    public GameSetupResponse(Player player, ArrayList<Quest> publicQuest) {this.player = player;  this.publicQuests = publicQuest;}

    @Override
    public void accept(CLIResponseHandler handler) {
        handler.visit(this);
    }

    public Player getPlayer() {return player;}
    public ArrayList<Quest> getPublicQuests() {return publicQuests;}
}
