package it.polimi.ingsw.lb10.server.visitors.responseDespatch;

public interface ResponseVisitable {

    void accept(CLIResponseHandler handler);
}
