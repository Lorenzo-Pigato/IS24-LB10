package it.polimi.ingsw.lb10.client.clidesign.clipages;

import it.polimi.ingsw.lb10.client.clidesign.*;
import it.polimi.ingsw.lb10.client.clidesign.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.clidesign.ansi.AnsiFormat;

public class CLILobbyPage implements CLIPage {
    private static final CLIString enterChoice = new CLIString(">> Enter your choice <<\n>> ", AnsiColor.CYAN, AnsiFormat.BOLD, 1, 38);

    private CLIState state = new Default();
    @Override
    public void display(){ state.update(); }

    @Override
    public void update(CLIState state) { this.state = state; }

    public static class Default implements CLIState {
        @Override
        public void update() {
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
        public void update() {
            CLIString.replace(enterChoice, new CLIString(">> Invalid input <<", AnsiColor.RED, AnsiFormat.BOLD, 1, 35));
            CLICommand.restoreCursorPosition();
            CLICommand.clearScreenAfterCursor();
        }
    }
}
