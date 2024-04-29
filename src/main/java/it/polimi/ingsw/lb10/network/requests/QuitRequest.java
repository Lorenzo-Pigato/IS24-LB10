package it.polimi.ingsw.lb10.network.requests;

import it.polimi.ingsw.lb10.server.visitors.requestDispatch.LobbyRequestVisitor;

public class QuitRequest extends Request{
    private static final long serialVersionUID = 30L;
    @Override
    public void accept(LobbyRequestVisitor handler) {
        synchronized (handler){
            handler.visit(this);
        }
    }
}
