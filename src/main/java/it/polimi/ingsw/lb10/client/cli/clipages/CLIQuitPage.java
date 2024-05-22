package it.polimi.ingsw.lb10.client.cli.clipages;

import it.polimi.ingsw.lb10.client.cli.CLIBanner;
import it.polimi.ingsw.lb10.client.cli.CLICommand;
import it.polimi.ingsw.lb10.client.cli.CLIString;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiColor;

import java.util.Arrays;

public class CLIQuitPage implements CLIPage {
    private static final CLIString goodbye = new CLIString(">> You are leaving Codex, thanks for playing! <<", AnsiColor.PURPLE, 0, 36);

    private CLIState state = new Default();

    @Override
    public void changeState(CLIState state) {
        this.state = state;
    }

    @Override
    public void print(Object[] args) {
        this.state.apply(args);
    }

    public static class Default implements CLIState {
        @Override
        public void apply(Object[] args) {
            CLICommand.initialize();
            CLIBanner.displayCodex(15, AnsiColor.PURPLE);

            goodbye.centerPrint();

            CLICommand.setPosition(0, 48);
        }
    }
}
