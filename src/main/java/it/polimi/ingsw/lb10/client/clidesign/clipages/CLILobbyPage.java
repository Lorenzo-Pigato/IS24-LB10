package it.polimi.ingsw.lb10.client.clidesign.clipages;

import it.polimi.ingsw.lb10.client.clidesign.*;
import it.polimi.ingsw.lb10.client.clidesign.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.clidesign.ansi.AnsiFormat;
import it.polimi.ingsw.lb10.client.clidesign.ansi.AnsiString;

public abstract class CLILobbyPage {
    private static final CLIString enterChoice = new CLIString(">> Enter your choice <<\n>> ", AnsiColor.CYAN, AnsiFormat.BOLD, 1, 38);
    public static void displayLobbyPage(){
        CLICommand.initialize();

        CLIBanner.displayCodex(5);

        CLIBox.draw(62,31, 35, 6,
                ">> Create [NEW] game\n"+
                ">> [JOIN] an existing match\n"+
                ">> [QUIT] Codex\n",
                AnsiColor.DEFAULT, AnsiColor.DEFAULT, AnsiFormat.BOLD);


        CLIBox.draw(62,29,35,3, "MENU",
                AnsiColor.CYAN, AnsiColor.CYAN, AnsiFormat.BOLD);

        enterChoice.centerPrint();
        CLICommand.saveCursorPosition();
    }

    public static void main(String[] args) {
        displayLobbyPage();
    }

    public static void updateOnInvalidInput() {
        CLIString.replace(enterChoice, new CLIString(">> Invalid input <<", AnsiColor.RED, AnsiFormat.BOLD, 1, 35));
        CLICommand.restoreCursorPosition();
        CLICommand.clearScreenAfterCursor();
    }
}
