package it.polimi.ingsw.lb10.client.cli.clipages;

import it.polimi.ingsw.lb10.client.cli.CLIBanner;
import it.polimi.ingsw.lb10.client.cli.CLIBox;
import it.polimi.ingsw.lb10.client.cli.CLICommand;
import it.polimi.ingsw.lb10.client.cli.CLIString;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiFormat;
import org.jetbrains.annotations.NotNull;

/**
 * This page is used to create a new game or to join an existing one.
 */
public class CLILobbyPage implements CLIPage {
    private static final CLIString enterChoice = new CLIString(">> Enter your choice <<\n>> ", AnsiColor.CYAN, AnsiFormat.BOLD, 1, 39);
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
            CLIBanner.displayCodex(5);

            CLIBox.draw(54, 31, 56, 6,
                    ">> Create new game: new [players number (2 to 4)]\n" +
                            ">> Join a match: join [match ID]\n" +
                            ">> Quit Codex: quit\n",
                    AnsiColor.DEFAULT, AnsiColor.DEFAULT, AnsiFormat.BOLD);


            CLIBox.draw(54, 29, 56, 3, "MENU",
                    AnsiColor.CYAN, AnsiColor.CYAN, AnsiFormat.BOLD);

            enterChoice.centerPrint();
            CLICommand.saveCursorPosition();
        }
    }

    public static class InvalidInput implements CLIState {
        /**
         * @param args user invalid input as args[0]
         */
        @Override
        public void apply(Object @NotNull [] args) {
            CLICommand.setPosition(1, 38);
            CLICommand.clearLine();

            if (((String) args[0]).split(" ").length == 2 && ((String) args[0]).split(" ")[0].equals("join"))
                new CLIString(">> Match ID: " + ((String) args[0]).split(" ")[1] + " doesn't exist <<",
                        AnsiColor.RED, AnsiFormat.BOLD, 1, 38).centerPrint();
            else
                new CLIString(">> Invalid input <<", AnsiColor.RED, AnsiFormat.BOLD, 1, 38).centerPrint();

            CLICommand.restoreCursorPosition();
            CLICommand.clearUserInput((String) args[0]);
        }
    }
}