package it.polimi.ingsw.lb10.server.visitors.requestDispatch;

import it.polimi.ingsw.lb10.network.requests.preMatch.LoginRequest;
import it.polimi.ingsw.lb10.server.controller.LobbyController;

public class RequestHandler implements RequestVisitor{

    private static final LobbyController lobbyController = LobbyController.instance();

    /**
     * @param lr the request to be handled
     *  this method handles the LoginRequest sent by the client over the ClientConnection by
     *  checking if another player has the same username (sends a negative response in this case), then adds the player to
     *  the SignedPlayers list and sends a positive response to the Client.
     */
    @Override
    public void visit(LoginRequest lr) {
        if(lobbyController.validateUsername(lr.getUsername())){
            lobbyController.addSignedPlayer(lr.getUsername());
            //sends the response via CLIENTCONNECTION
        }
    }

}
