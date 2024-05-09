package it.polimi.ingsw.lb10.client.cli.clipages;

import it.polimi.ingsw.lb10.client.view.Page;
import it.polimi.ingsw.lb10.client.view.State;
import org.jetbrains.annotations.NotNull;

/**
 * This interface is a specialization of Page for CLI.
 * By default, it applies a CLIState to the page.
 */
public interface CLIPage extends Page {
     void changeState(@NotNull CLIState state);

}
