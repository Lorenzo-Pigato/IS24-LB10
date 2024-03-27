package it.polimi.ingsw.lb10.client.clidesign.clipages;

import it.polimi.ingsw.lb10.client.clidesign.CLIBanner;
import it.polimi.ingsw.lb10.client.clidesign.CLICommand;
import it.polimi.ingsw.lb10.client.clidesign.CLIString;
import it.polimi.ingsw.lb10.client.clidesign.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.clidesign.ansi.AnsiFormat;
import it.polimi.ingsw.lb10.client.clidesign.ansi.AnsiString;

public abstract class CLI404Page {
    private static final CLIString notFound = new CLIString(">> Server couldn't be reached <<", AnsiColor.CYAN, AnsiFormat.BOLD, 1, 33);
    private static final CLIString reconnect = new CLIString(">> [RECONNECT] or [QUIT]\n>> ", AnsiColor.CYAN, 1, 35);
    public static void display(){
        CLICommand.initialize();
        CLIBanner.display404();
        notFound.centerPrint();
        reconnect.centerPrint();

        CLICommand.saveCursorPosition();
        AnsiString.print("QUIT", AnsiColor.GREY);
        CLICommand.restoreCursorPosition();
    }

}
