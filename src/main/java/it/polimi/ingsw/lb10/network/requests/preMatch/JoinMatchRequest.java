package it.polimi.ingsw.lb10.network.requests.preMatch;
import it.polimi.ingsw.lb10.network.requests.match.MatchRequest;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.LobbyRequestVisitor;

import java.net.Socket;

public class JoinMatchRequest extends MatchRequest {
    private String username;
    private Socket userSocket;
    public JoinMatchRequest(int hashCode, int matchId) {
        super(hashCode , matchId);
    }
    public Socket getUserSocket() {return userSocket;}
    public void setUserSocket(Socket userSocket) {this.userSocket = userSocket;}
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    @Override
    public void accept(LobbyRequestVisitor handler) {
        handler.visit(this);
    }
}
