package it.polimi.ingsw.lb10.network.requests.preMatch;

import it.polimi.ingsw.lb10.network.requests.preMatch.PreMatchRequest;
import org.jetbrains.annotations.NotNull;

public class LoginRequest extends PreMatchRequest {
    private final String username;

    public LoginRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
