package it.polimi.ingsw.lb10.network.requests.match;

import it.polimi.ingsw.lb10.network.requests.Request;
import it.polimi.ingsw.lb10.server.model.quest.Quest;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.LobbyRequestVisitor;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.MatchRequestVisitor;

public class PrivateQuestSelectedRequest extends MatchRequest{
    private Quest selectedQuest;

    public PrivateQuestSelectedRequest(int matchId, Quest selectedQuest) {
        super(matchId);
        this.selectedQuest = selectedQuest;
    }

    /**
     * @param handler
     */
    @Override
    public void accept(LobbyRequestVisitor handler) {
        handler.visit(this);
    }

    /**
     * @param visitor
     */
    @Override
    public void accept(MatchRequestVisitor visitor) {
        visitor.visit(this);
    }

    public Quest getSelectedQuest(){return selectedQuest;}
}
