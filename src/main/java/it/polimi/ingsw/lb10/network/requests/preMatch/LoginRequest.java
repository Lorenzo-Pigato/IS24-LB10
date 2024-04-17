package it.polimi.ingsw.lb10.network.requests.preMatch;
import it.polimi.ingsw.lb10.network.requests.Request;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.LobbyRequestVisitable;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.LobbyRequestVisitor;

public class LoginRequest extends LobbyRequest {
    private final String username;
    private static final long serialVersionUID = 1L;

    public LoginRequest(String username) {
        this.username = username;
    }


    @Override
    public void accept(LobbyRequestVisitor handler) {
        handler.visit(this);
    }

    public String getUsername() {
        return username;
    }
}
