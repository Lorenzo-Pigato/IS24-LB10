package it.polimi.ingsw.lb10.client.cli.clipages;

import it.polimi.ingsw.lb10.client.view.Page;

/**
 * This interface is a specialization of Page for CLI.
 * By default, it applies a CLIState to the page.
 */
public interface CLIPage extends Page {
    default void display(CLIState state, String[] args){
        state.apply(args);
    }
}
