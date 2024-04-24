package it.polimi.ingsw.lb10.network.requests.preMatch;

import it.polimi.ingsw.lb10.server.visitors.requestDispatch.LobbyRequestVisitor;

public class NewMatchRequest extends LobbyRequest {
    private int numberOfPlayers;

    public NewMatchRequest(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }
    public int getNumberOfPlayers() {return numberOfPlayers;}

    @Override
    public void accept(LobbyRequestVisitor handler) {
        synchronized (handler){
            handler.visit(this);
        }
    }
}
