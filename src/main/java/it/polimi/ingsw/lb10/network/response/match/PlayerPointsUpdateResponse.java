package it.polimi.ingsw.lb10.network.response.match;

import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.CLIResponseHandler;

public class PlayerPointsUpdateResponse extends Response {

    private final String username;
    private final int points;

    public PlayerPointsUpdateResponse(String username, int points) {
        this.username = username;
        this.points = points;
    }

    @Override
    public void accept(CLIResponseHandler handler) {
        handler.visit(this);
    }

    public String getUsername() {return username;}
    public int getPoints() {return points;}
}
