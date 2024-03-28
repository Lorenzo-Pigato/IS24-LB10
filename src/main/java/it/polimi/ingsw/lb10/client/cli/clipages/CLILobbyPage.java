package it.polimi.ingsw.lb10.client.cli.clipages;

import it.polimi.ingsw.lb10.client.cli.*;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiFormat;

public class CLILobbyPage implements CLIPage {
    private static final CLIString enterChoice = new CLIString(">> Enter your choice <<\n>> ", AnsiColor.CYAN, AnsiFormat.BOLD, 1, 38);

    public static class Default implements CLIState {
        @Override
        public void apply(String[] args) {
            CLICommand.initialize();
            CLIBanner.displayCodex(5);

            CLIBox.draw(62, 31, 35, 6,
                    ">> Create [NEW] game\n" +
                            ">> [JOIN] an existing match\n" +
                            ">> [QUIT] Codex\n",
                    AnsiColor.DEFAULT, AnsiColor.DEFAULT, AnsiFormat.BOLD);


            CLIBox.draw(62, 29, 35, 3, "MENU",
                    AnsiColor.CYAN, AnsiColor.CYAN, AnsiFormat.BOLD);

            enterChoice.centerPrint();
            CLICommand.saveCursorPosition();
        }
    }

    public static class InvalidInput implements CLIState {
        @Override
        public void apply(String[] args) {
            CLIString.replace(enterChoice, new CLIString(">> Invalid input <<", AnsiColor.RED, AnsiFormat.BOLD, 1, 35));
            CLICommand.restoreCursorPosition();
            CLICommand.clearScreenAfterCursor();
        }
    }
}


/////RIVEDERE UPDATE ARGS