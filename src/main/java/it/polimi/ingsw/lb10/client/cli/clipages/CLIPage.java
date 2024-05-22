package it.polimi.ingsw.lb10.client.cli.clipages;

import org.jetbrains.annotations.NotNull;

/**
 * This interface is implemented by all the pages displayed by the CLI.
 * By default, it applies a CLIState to the Page.
 */
public interface CLIPage {
     void print(Object[] args);
     void changeState(@NotNull CLIState state);
}
