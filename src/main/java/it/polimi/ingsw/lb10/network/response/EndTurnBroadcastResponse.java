package it.polimi.ingsw.lb10.network.response;

import it.polimi.ingsw.lb10.server.model.Player;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.CLIResponseHandler;

public class EndTurnBroadcastResponse extends Response {
    private static final long serialVersionUID = 15L;
    private final Player onTurn;

    public EndTurnBroadcastResponse(Player onTurn) {this.onTurn = onTurn;}

    @Override
    public void accept(CLIResponseHandler handler) {
        handler.visit(this);
    }

    public Player getOnTurn() {return onTurn;}
}
