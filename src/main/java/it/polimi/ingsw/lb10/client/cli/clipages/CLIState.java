package it.polimi.ingsw.lb10.client.cli.clipages;

/**
 * This interface must be implemented by all the classes that represent elements of a page shown by the CLI,
 * optionally this states can need args passed from the view controller.
 */
public interface CLIState {
    void apply(Object[] args);
}
