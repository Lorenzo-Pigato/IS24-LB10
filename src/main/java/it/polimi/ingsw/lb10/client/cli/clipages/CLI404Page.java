package it.polimi.ingsw.lb10.client.cli.clipages;

import it.polimi.ingsw.lb10.client.cli.CLIBanner;
import it.polimi.ingsw.lb10.client.cli.CLICommand;
import it.polimi.ingsw.lb10.client.cli.CLIString;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiFormat;
import org.jetbrains.annotations.NotNull;

/**
 * This class is displayed to the client when the server can't be reached.
 */
public class CLI404Page implements CLIPage {
    private static final CLIString notFound = new CLIString(">> Server couldn't be reached <<", AnsiColor.CYAN, AnsiFormat.BOLD, 1, 33);
    private static final CLIString reconnect = new CLIString(">> Quitting <<", AnsiColor.CYAN, 1, 35);
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
        public void apply(Object[] args) {
            CLICommand.initialize();
            CLIBanner.display404();
            notFound.centerPrint();
            reconnect.centerPrint();

            CLICommand.setPosition(1, 50);
        }

    }

}
