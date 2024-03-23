package it.polimi.ingsw.lb10.client.view;

import it.polimi.ingsw.lb10.client.clidesign.CLIBanner;
import it.polimi.ingsw.lb10.client.clidesign.CLICommand;
import it.polimi.ingsw.lb10.client.clidesign.CLIString;
import it.polimi.ingsw.lb10.client.clidesign.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.util.InputVerifier;

import java.util.Scanner;

public abstract class LauncherView {
    private static final CLIString welcome = new CLIString(">> Welcome to Codex! <<", AnsiColor.YELLOW, 0, 26);
    private static final CLIString welcomePlayer = new CLIString(">> Welcome to Codex, Player! <<", AnsiColor.YELLOW, 0, 26);
    private static final CLIString inputError = new CLIString(">> Invalid input <<", AnsiColor.RED, 0, 26);
    private static final CLIString chooseApplication = new CLIString(
            ">> Choose [SERVER] or [CLIENT] to launch application\n>> ",
            AnsiColor.YELLOW, 0, 27
    );
    private static final CLIString choseInterface = new CLIString(
            ">> Do you want to launch the Graphic Interface? [Y/N]\n>> "
            , AnsiColor.YELLOW, 0, 27
    );

    public static String runLauncherPage() {
        CLICommand.initialize();
        CLIBanner.displayCodex();
        chooseApplication.centerPrint();

        String choice = InputVerifier.verify(new String[]{"server", "client"}, welcome, inputError, true);

        if(choice.equalsIgnoreCase(("client"))) {
            CLIString.replace(welcome, welcomePlayer);
            CLIString.replace(chooseApplication, choseInterface);

            choice += InputVerifier.verify(new String[]{"Y", "N"}, welcomePlayer, inputError, true)
                    .equalsIgnoreCase("Y") ? ":gui" : ":cli";
        }

        return choice.toLowerCase();
    }
}


