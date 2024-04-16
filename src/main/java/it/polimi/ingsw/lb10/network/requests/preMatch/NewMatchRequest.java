package it.polimi.ingsw.lb10.network.requests.preMatch;

import it.polimi.ingsw.lb10.server.visitors.requestDispatch.LobbyRequestVisitor;

public class NewMatchRequest extends LobbyRequest {
    private int numberOfPlayers;

    public NewMatchRequest(int hashCode, int numberOfPlayers) {
        super(hashCode);
        this.numberOfPlayers = numberOfPlayers;
    }
    public int getNumberOfPlayers() {return numberOfPlayers;}

    @Override
    public void accept(LobbyRequestVisitor handler) {
        handler.visit(this);
    }
}
