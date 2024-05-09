package it.polimi.ingsw.lb10.client.cli.clipages;

import org.jetbrains.annotations.NotNull;

/**
 * This interface is a specialization of Page for CLI.
 * By default, it applies a CLIState to the page.
 */
public interface CLIPage {
     void print(Object[] args);
     void changeState(@NotNull CLIState state);
}
