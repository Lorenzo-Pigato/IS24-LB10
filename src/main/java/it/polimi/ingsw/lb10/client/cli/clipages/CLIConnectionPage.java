package it.polimi.ingsw.lb10.client.cli.clipages;

import it.polimi.ingsw.lb10.client.cli.CLIBanner;
import it.polimi.ingsw.lb10.client.cli.CLICommand;
import it.polimi.ingsw.lb10.client.cli.CLILine;
import it.polimi.ingsw.lb10.client.cli.CLIString;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiFormat;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiString;
import org.jetbrains.annotations.NotNull;

public class CLIConnectionPage implements CLIPage{
    private static final CLIString welcome = new CLIString(">> Welcome to Codex, new Player! <<", AnsiColor.YELLOW, 0, 36);
    private  static final CLIString invalidIp = new CLIString(">> Invalid server ip <<", AnsiColor.RED, 0, 36);
    private static final CLIString invalidPort = new CLIString(">> Invalid port number <<", AnsiColor.RED,0,36);
    private static final CLIString invalidInput = new CLIString(">> Invalid input <<", AnsiColor.RED, 0, 36);
    private static final CLIString options = new CLIString(">> Insert your server IP and PORT as IP:PORT\n>> ", AnsiColor.YELLOW, 0, 37);


    public static class Default implements CLIState {
        @Override
        public void apply(String[] args) {
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
            AnsiString.print("127.0.0.1:3773", AnsiColor.GREY, AnsiFormat.LIGHT);
            CLICommand.restoreCursorPosition();
        }
    }

    public static class InvalidInput implements CLIState {
        /**
         * @param args user invalid input as args[0]
         */
        @Override
        public void apply(String @NotNull [] args) {
            CLICommand.restoreCursorPosition();
            CLICommand.clearUserInput(args[0]);
            CLIString.replace(welcome, invalidInput);

            CLICommand.restoreCursorPosition();
        }
    }

    public static class InvalidIP implements CLIState {
        /**
         * @param args user invalid input as args[0]
         */
        @Override
        public void apply(String @NotNull [] args) {
            CLICommand.restoreCursorPosition();
            CLICommand.clearUserInput(args[0]);
            CLIString.replace(welcome, invalidIp);

            CLICommand.restoreCursorPosition();
        }
    }

    public static class InvalidPort implements CLIState {
        /**
         * @param args user invalid input as args[0]
         */
        @Override
        public void apply(String[] args) {
            CLICommand.restoreCursorPosition();
            CLICommand.clearUserInput(args[0]);
            CLIString.replace(welcome, invalidPort);

            CLICommand.restoreCursorPosition();
        }
    }
}


