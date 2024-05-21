package it.polimi.ingsw.lb10.client.cli.clipages;

import it.polimi.ingsw.lb10.client.cli.CLIBanner;
import it.polimi.ingsw.lb10.client.cli.CLICommand;
import it.polimi.ingsw.lb10.client.cli.CLIString;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiFormat;
import org.jetbrains.annotations.NotNull;

/**
 * This class is displayed to the client when an error occurs.
 * The error message is usually set by an ExceptionHandler implementation
 */
public class CLIErrorPage implements CLIPage {
    private CLIState state = new Default();

    @Override
    public void changeState(@NotNull CLIState state) {
        this.state = state;
    }

    @Override
    public void print(Object[] args) {
        state.apply(args);
    }

    public static class Default implements CLIState {
        @Override
        public void apply(Object @NotNull [] args) {
            CLICommand.initialize();
            CLIBanner.displayError();
            new CLIString(args[0] != null ? (String) args[0] : "", AnsiColor.RED, AnsiFormat.BOLD, 1, 33).centerPrint();
            new CLIString(args[1] != null ? (String) args[1] : "", AnsiColor.RED, 1, 35).centerPrint();

            CLICommand.setPosition(1, 50);
        }
    }
}

