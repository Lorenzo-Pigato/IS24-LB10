package it.polimi.ingsw.lb10.client.cli.clipages;

import it.polimi.ingsw.lb10.client.cli.CLIBanner;
import it.polimi.ingsw.lb10.client.cli.CLICommand;
import it.polimi.ingsw.lb10.client.cli.CLIString;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiFormat;
import org.jetbrains.annotations.NotNull;

public class CLILoginPage implements CLIPage {
    private static final CLIString insertUsername = new CLIString(">> Insert your username <<\n>> ",
            AnsiColor.GREEN,
            AnsiFormat.BOLD,
            1, 34);
    private static final CLIString invalidLength = new CLIString(">> Username must be between 2 and 15 characters <<",
            AnsiColor.RED,
            1, 33);
    private static final CLIString alreadyTaken = new CLIString(">> Username already taken <<", AnsiColor.RED, 1, 33);
    private CLIState state = new Default();

    @Override
    public void changeState(@NotNull CLIState state) {
        this.state = state;
    }

    @Override
    public void print(Object[] args){
        state.apply(args);
    }

    public static class Default implements CLIState {
        @Override
        public void apply(Object[] args) {
            CLICommand.initialize();

            CLIBanner.displayLogin();

            insertUsername.centerPrint();
            CLICommand.saveCursorPosition();
        }
    }

    public static class invalidLength implements CLIState {
        @Override
        public void apply(Object @NotNull [] args) {
            if(alreadyTaken.isVisible()) CLIString.replace(alreadyTaken, invalidLength);
            else invalidLength.centerPrint();

            CLICommand.restoreCursorPosition();
            CLICommand.clearUserInput((String) args[0]);
        }
    }

    public static class alreadyTaken implements CLIState {
        @Override
        public void apply(Object @NotNull [] args) {
            if(invalidLength.isVisible()) CLIString.replace(invalidLength, alreadyTaken);
            else alreadyTaken.centerPrint();

            CLICommand.restoreCursorPosition();
            CLICommand.clearUserInput((String) args[0]);
        }
    }
}
