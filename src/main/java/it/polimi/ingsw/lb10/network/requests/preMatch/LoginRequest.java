package it.polimi.ingsw.lb10.network.requests.preMatch;
import it.polimi.ingsw.lb10.network.requests.Request;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.RequestHandler;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.RequestVisitor;

public class LoginRequest extends Request {
    private String username;
    private static final long serialVersionUID = 1L;

    @Override
    public void accept(RequestVisitor handler) {
        handler.visit(this);
    }

    public String getUsername() {
        return username;
    }
}
