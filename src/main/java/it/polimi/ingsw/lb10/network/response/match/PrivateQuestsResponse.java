package it.polimi.ingsw.lb10.network.response.match;

import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.model.quest.Quest;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.CLIResponseHandler;

import java.util.ArrayList;

public class PrivateQuestsResponse extends Response {
    private static final long serialVersionUID = 8L;
    private final ArrayList<Quest> privateQuests;

    public PrivateQuestsResponse(ArrayList<Quest> privateQuests) {this.privateQuests = privateQuests;}
    /**
     * @param handler
     */
    @Override
    public void accept(CLIResponseHandler handler) {
        handler.visit(this);

    }

    public ArrayList<Quest> getPrivateQuests() {return privateQuests;}
}
