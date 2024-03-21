package it.polimi.ingsw.lb10.server.visitors.requestDispatch;

public interface RequestVisitable {

    public void accept(RequestVisitor visitor);

}
