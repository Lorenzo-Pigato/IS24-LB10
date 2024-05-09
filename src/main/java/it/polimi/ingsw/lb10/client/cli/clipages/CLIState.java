package it.polimi.ingsw.lb10.client.cli.clipages;

/**
 * This interface extends State and must be implemented by all the classes that represent elements of the CLI which can
 * be displayed on screen, optionally updated with args from the view controller.
 */
public interface CLIState {
    void apply(Object[] args);
}
