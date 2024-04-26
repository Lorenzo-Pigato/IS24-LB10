package it.polimi.ingsw.lb10.network.requests.match;

import it.polimi.ingsw.lb10.server.visitors.requestDispatch.MatchRequestVisitor;

public class ShowPlayerRequest extends MatchRequest {

    private final String username;

    public ShowPlayerRequest(int matchId, String username) {
        super(matchId);
        this.username = username;
    }

    /**
     * @param visitor
     */
    @Override
    public void accept(MatchRequestVisitor visitor) {
     visitor.visit(this);
    }
}
