package it.polimi.ingsw.lb10.client.view;

import it.polimi.ingsw.lb10.client.cli.CLIBanner;
import it.polimi.ingsw.lb10.client.cli.CLICommand;
import it.polimi.ingsw.lb10.client.cli.CLIString;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiFormat;
import it.polimi.ingsw.lb10.client.util.InputVerifier;
import org.jetbrains.annotations.NotNull;

import java.util.Scanner;

public abstract class LauncherView {
    private static final CLIString welcome = new CLIString(">> Welcome to Codex! <<", AnsiColor.YELLOW, 0, 36);

    // ------------- CLIENT STRINGS ------------------//
    private static final CLIString welcomePlayer = new CLIString(">> Welcome to Codex, Player! <<", AnsiColor.YELLOW, 0, 36);
    private static final CLIString inputError = new CLIString(">> Invalid input <<", AnsiColor.RED, AnsiFormat.BOLD, 0, 36);
    private static final CLIString chooseApplication = new CLIString(
            ">> Choose [SERVER] or [CLIENT] to launch application\n>> ",
            AnsiColor.YELLOW, 0, 37
    );
    private static final CLIString chooseInterface = new CLIString(
            ">> Do you want to launch the Graphic Interface? [Y/N]\n>> "
            , AnsiColor.YELLOW, 0, 37
    );

    // ------------- SERVER STRINGS ----------- //
    private static final CLIString startingServer = new CLIString(">> Starting Server Application <<", AnsiColor.YELLOW, 0, 36);
    private static final CLIString choosePort = new CLIString(
            ">> Insert server port\n>> ",
            AnsiColor.YELLOW, 0, 37
    );
    private static final CLIString invalidPort = new CLIString(">> Invalid port number <<", AnsiColor.RED, AnsiFormat.BOLD, 0, 36);

    public static @NotNull String runLauncherPage() {
        CLICommand.initialize();
        CLIBanner.displayCodex();
        chooseApplication.centerPrint();

        String choice = InputVerifier.verify(new String[]{"server", "client"}, welcome, inputError, true);

        if (choice.equalsIgnoreCase(("client"))) {
            CLIString.replace(welcome, welcomePlayer);
            CLIString.replace(chooseApplication, chooseInterface);
            CLICommand.saveCursorPosition();

            choice += InputVerifier.verify(new String[]{"Y", "N"}, welcomePlayer, inputError, true)
                    .equalsIgnoreCase("Y") ? ":gui" : ":cli";
        } else {
            CLIString.replace(welcome, startingServer);
            CLIString.replace(chooseApplication, choosePort);
            CLICommand.saveCursorPosition();

            Scanner in = new Scanner(System.in);
            String input;
            do {
                input = in.nextLine();
                if (InputVerifier.isNotValidPort(input)) {
                    CLIString.replace(startingServer, invalidPort);
                    CLICommand.clearUserInput(input);
                }
            } while (InputVerifier.isNotValidPort(input));

            choice += ":" + input;
        }

        return choice.toLowerCase();
    }
}


