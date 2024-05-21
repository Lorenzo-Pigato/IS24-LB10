package it.polimi.ingsw.lb10.client.cli.clipages;

import it.polimi.ingsw.lb10.client.cli.CLIBanner;
import it.polimi.ingsw.lb10.client.cli.CLICommand;
import it.polimi.ingsw.lb10.client.cli.CLIString;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiFormat;
import org.jetbrains.annotations.NotNull;

public class CLIWaitingPage implements CLIPage {

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
            CLIBanner.displayWaitingRoom();

            new CLIString(">> Waiting for other players to join <<", AnsiColor.CYAN, AnsiFormat.BOLD, 1, 33).centerPrint();
            new CLIString(">> Match ID: " + args[0] + " <<", AnsiColor.CYAN, 1, 35).centerPrint();

            CLICommand.setInvisibleInput();
        }
    }
}
