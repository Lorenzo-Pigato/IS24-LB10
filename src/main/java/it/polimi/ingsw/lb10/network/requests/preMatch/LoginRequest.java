package it.polimi.ingsw.lb10.network.requests.preMatch;

import it.polimi.ingsw.lb10.server.visitors.requestDispatch.LobbyRequestVisitor;

import java.io.Serial;

public class LoginRequest extends LobbyRequest {
    private final String username;
    @Serial
    private static final long serialVersionUID = 1L;

    public LoginRequest(String username) {
        this.username = username;
    }


    @Override
    public void accept(LobbyRequestVisitor handler) {
        synchronized (handler) {
            handler.visit(this);
        }
    }

    public String getUsername() {
        return username;
    }
}
