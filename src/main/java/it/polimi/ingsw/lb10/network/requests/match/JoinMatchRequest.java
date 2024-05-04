package it.polimi.ingsw.lb10.network.requests.match;

import it.polimi.ingsw.lb10.server.model.Player;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.MatchRequestVisitor;

import java.io.Serial;

public class JoinMatchRequest extends MatchRequest {
    @Serial
    private static final long serialVersionUID = 22L;
    private final Player player;

    public JoinMatchRequest(int matchId, Player player) {
        super(matchId);
        this.player = player;
    }

    @Override
    public void accept(MatchRequestVisitor visitor) {
        visitor.visit(this);
    }

    public Player getPlayer() {
        return player;
    }
}
