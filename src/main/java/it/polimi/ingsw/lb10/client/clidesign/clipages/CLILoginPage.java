package it.polimi.ingsw.lb10.client.clidesign.clipages;

import it.polimi.ingsw.lb10.client.clidesign.CLIBanner;
import it.polimi.ingsw.lb10.client.clidesign.CLICommand;
import it.polimi.ingsw.lb10.client.clidesign.CLIString;
import it.polimi.ingsw.lb10.client.clidesign.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.clidesign.ansi.AnsiString;

public class CLILoginPage implements CLIPage {
    private static final CLIString insertUsername = new CLIString(">> Insert your username <<\n>> ", AnsiColor.GREEN, 1, 34);
    private static final CLIString invalidLength = new CLIString(">> Username must be between 2 and 15 characters <<", AnsiColor.RED, 1, 33);
    private static final CLIString alreadyTaken = new CLIString(">> Username already taken <<", AnsiColor.RED, 1, 33);
    private CLIState state = new Default();
    @Override
    public void display() { state.update(); }

    @Override
    public void update(CLIState state) { this.state = state; }

    public static class Default implements CLIState {
        @Override
        public void update() {
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
        public void update() {
            if(alreadyTaken.isVisible()) CLIString.replace(alreadyTaken, invalidLength);
            else invalidLength.centerPrint();

            CLICommand.restoreCursorPosition();
            CLICommand.clearLineAfterCursor();
        }
    }

    public static class alreadyTaken implements CLIState {
        @Override
        public void update() {
            if(invalidLength.isVisible()) CLIString.replace(invalidLength, alreadyTaken);
            else alreadyTaken.centerPrint();

            CLICommand.restoreCursorPosition();
            CLICommand.clearLineAfterCursor();
        }
    }
}
