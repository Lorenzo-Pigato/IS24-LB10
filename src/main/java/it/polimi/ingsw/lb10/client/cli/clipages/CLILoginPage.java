package it.polimi.ingsw.lb10.client.cli.clipages;

import it.polimi.ingsw.lb10.client.cli.CLIBanner;
import it.polimi.ingsw.lb10.client.cli.CLICommand;
import it.polimi.ingsw.lb10.client.cli.CLIString;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiColor;
import org.jetbrains.annotations.NotNull;

public class CLILoginPage implements CLIPage {
    private static final CLIString insertUsername = new CLIString(">> Insert your username <<\n>> ", AnsiColor.GREEN, 1, 34);
    private static final CLIString invalidLength = new CLIString(">> Username must be between 2 and 15 characters <<", AnsiColor.RED, 1, 33);
    private static final CLIString alreadyTaken = new CLIString(">> Username already taken <<", AnsiColor.RED, 1, 33);

    public static class Default implements CLIState {
        @Override
        public void apply(String[] args) {
            CLICommand.initialize();

            CLIBanner.displayWolf(1,1);
            CLIBanner.displayMushroom(137,1);
            CLIBanner.displayButterfly(1, 37);
            CLIBanner.displayLeaf(137, 37);

            CLIBanner.displayLogin();

            insertUsername.centerPrint();
            CLICommand.saveCursorPosition();
        }
    }

    public static class invalidLength implements CLIState {
        @Override
        public void apply(String @NotNull [] args) {
            if(alreadyTaken.isVisible()) CLIString.replace(alreadyTaken, invalidLength);
            else invalidLength.centerPrint();

            CLICommand.restoreCursorPosition();
            CLICommand.clearUserInput(args[0]);
        }
    }

    public static class alreadyTaken implements CLIState {
        @Override
        public void apply(String @NotNull [] args) {
            if(args[0] == null) throw new IllegalArgumentException();

            if(invalidLength.isVisible()) CLIString.replace(invalidLength, alreadyTaken);
            else alreadyTaken.centerPrint();

            CLICommand.restoreCursorPosition();
            CLICommand.clearUserInput(args[0]);
        }
    }
}
