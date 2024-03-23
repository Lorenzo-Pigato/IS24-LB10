package it.polimi.ingsw.lb10.client.clidesign.clipages;

import it.polimi.ingsw.lb10.client.clidesign.CLIBanner;
import it.polimi.ingsw.lb10.client.clidesign.CLICommand;
import it.polimi.ingsw.lb10.client.clidesign.CLIString;
import it.polimi.ingsw.lb10.client.clidesign.ansi.AnsiColor;

public abstract class CLIConnectionPage {
    private static final CLIString welcome = new CLIString(">> Welcome to Codex, new Player! <<", AnsiColor.YELLOW, 0, 26);
    private  static final CLIString invalidIp = new CLIString(">> Invalid server ip <<", AnsiColor.RED, 0, 26);
    private static final CLIString invalidPort = new CLIString(">> Invalid port number <<", AnsiColor.RED,0,26);
    private static final CLIString invalidInput = new CLIString(">> Invalid input <<", AnsiColor.RED, 0, 26);
    private static final CLIString options = new CLIString(">> Insert your server IP and PORT as IP:PORT\n>> ", AnsiColor.YELLOW, 0, 27);
    public static void display() {
        CLICommand.clearScreen();
        CLICommand.home();

        CLIBanner.displayWolf(20,10);
        CLIBanner.displayMushroom(50, 10);
        CLIBanner.displayButterfly(80, 10);
        CLIBanner.displayLeaf(110,10);

        welcome.centerPrint();
        options.centerPrint();
        CLICommand.saveCursorPosition();

    }

    public static void invalidIp(){
        CLICommand.restoreCursorPosition();
        CLICommand.clearLineAfterCursor();
        CLIString.replace(welcome, invalidIp);
    }

    public static void invalidPort(){
        CLICommand.restoreCursorPosition();
        CLICommand.clearLineAfterCursor();
        CLIString.replace(welcome, invalidPort);
    }

    public static void invalidInput(){
        CLICommand.restoreCursorPosition();
        CLICommand.clearLineAfterCursor();
        CLIString.replace(welcome, invalidInput);
    }

}
