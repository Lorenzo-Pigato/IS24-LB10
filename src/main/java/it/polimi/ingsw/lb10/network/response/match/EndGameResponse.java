package it.polimi.ingsw.lb10.network.response.match;

import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.model.Player;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.CLIResponseHandler;

import java.util.Optional;

public class EndGameResponse extends Response {
    private final String winnerUsername;

    public EndGameResponse(Optional<Player> winner) {
        this.winnerUsername = winner.map(Player::getUsername).orElse(null);
    }

    @Override
    public void accept(CLIResponseHandler handler) {
        handler.visit(this);
    }

    public String getWinnerUsername() {return winnerUsername;}
}
