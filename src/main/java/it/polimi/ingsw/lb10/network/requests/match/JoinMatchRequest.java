package it.polimi.ingsw.lb10.network.requests.match;
import it.polimi.ingsw.lb10.server.model.Player;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.MatchRequestVisitor;

public class JoinMatchRequest extends MatchRequest {

    private Player player;

    public JoinMatchRequest(int hashCode, int matchId, Player player) {
        super(hashCode , matchId);
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
