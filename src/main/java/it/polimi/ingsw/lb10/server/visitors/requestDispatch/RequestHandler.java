package it.polimi.ingsw.lb10.server.visitors.requestDispatch;

import it.polimi.ingsw.lb10.network.requests.preMatch.LoginRequest;
import it.polimi.ingsw.lb10.server.controller.LobbyController;
import it.polimi.ingsw.lb10.server.view.RemoteView;
import org.jetbrains.annotations.NotNull;


/**
 * This class is a sperimental brisge between ClientConnection , which receives the client Requests object
 * and the controllers.
 * Every single request must be mapped into this class to be handled.
 * There are two ways of handling requests
 * 1. the procedural way : this is for LoginRequests and JoinMatchRequests, which are built in the client code as procedural requests sent by the ViewController
 * these requests are directly handled  by the LobbyController which is instantiated inside this class
 *  2. the asynchronous way : this way is for in-game requests, they can be various (chat, show other players view, place card, exc...)
 *  and are sent by client threads under the inputs, these request MUST be sent over to the MatchController by the "requests.put()" method
 *  Controller will handle these requests and update the RemoteView sending back responses
 */
public class RequestHandler implements RequestVisitor{

    private static final LobbyController lobbyController = LobbyController.instance();
    private final RemoteView remoteView;

    public RequestHandler(RemoteView remoteView) {
        this.remoteView = remoteView ;
    }

    /**
     * @param lr the request to be handled
     *  this method handles the LoginRequest sent by the client over the ClientConnection by
     *  checking if another player has the same username (sends a negative response in this case), then adds the player to
     *  the SignedPlayers list and sends a positive response to the Client (procedural).
     */
    @Override
    public void visit(@NotNull LoginRequest lr) {
        if(lobbyController.validateUsername(lr.getUsername())){
            lobbyController.addSignedPlayer(lr.getUsername());
            //remote view
        }
    }

}
