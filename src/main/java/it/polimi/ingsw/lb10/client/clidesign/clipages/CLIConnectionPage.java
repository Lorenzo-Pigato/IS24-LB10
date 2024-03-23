package it.polimi.ingsw.lb10.client.clidesign.clipages;

import it.polimi.ingsw.lb10.client.clidesign.CLIBanner;
import it.polimi.ingsw.lb10.client.clidesign.CLICommand;
import it.polimi.ingsw.lb10.client.clidesign.CLILine;
import it.polimi.ingsw.lb10.client.clidesign.CLIString;
import it.polimi.ingsw.lb10.client.clidesign.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.clidesign.ansi.AnsiString;

public abstract class CLIConnectionPage {
    private static final CLIString welcome = new CLIString(">> Welcome to Codex, new Player! <<", AnsiColor.YELLOW, 0, 36);
    private  static final CLIString invalidIp = new CLIString(">> Invalid server ip <<", AnsiColor.RED, 0, 36);
    private static final CLIString invalidPort = new CLIString(">> Invalid port number <<", AnsiColor.RED,0,36);
    private static final CLIString invalidInput = new CLIString(">> Invalid input <<", AnsiColor.RED, 0, 36);
    private static final CLIString options = new CLIString(">> Insert your server IP and PORT as IP:PORT\n>> ", AnsiColor.YELLOW, 0, 37);
    public static void display() {
        CLICommand.clearScreen();
        CLICommand.home();

        CLIBanner.displayWolf(20,15);
        CLIBanner.displayMushroom(50, 15);
        CLIBanner.displayButterfly(80, 15);
        CLIBanner.displayLeaf(110,15);

        CLILine.drawHorizontal(20,33, 160-20, AnsiColor.YELLOW);

        welcome.centerPrint();
        options.centerPrint();

        CLICommand.saveCursorPosition();
        AnsiString.print("127.0.0.0:3773", AnsiColor.GREY);
        CLICommand.restoreCursorPosition();

    }

    public static void invalidIp(){
        CLICommand.restoreCursorPosition();
        CLICommand.clearScreenAfterCursor();
        CLIString.replace(welcome, invalidIp);
    }

    public static void invalidPort(){
        CLICommand.restoreCursorPosition();
        CLICommand.clearScreenAfterCursor();
        CLIString.replace(welcome, invalidPort);
    }

    public static void invalidInput(){
        CLICommand.restoreCursorPosition();
        CLICommand.clearScreenAfterCursor();
        CLIString.replace(welcome, invalidInput);
    }

}
