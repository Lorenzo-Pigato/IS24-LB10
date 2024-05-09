package it.polimi.ingsw.lb10.client.view;

import it.polimi.ingsw.lb10.client.cli.clipages.CLIState;
import org.jetbrains.annotations.NotNull;

/**
 * This interface is used to generalize the concept of a page in the client view.
 * Page interface is empty, but implemented by CLIPage and GUIPage.
 * Page is used to represent a container for State objects, which are the actual content to display.
 */
public interface Page {
    void print(Object[] args);
}
