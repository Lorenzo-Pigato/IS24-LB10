package it.polimi.ingsw.lb10.client.view;

import it.polimi.ingsw.lb10.client.clidesign.CLIBanner;
import it.polimi.ingsw.lb10.client.clidesign.CLIPage;
import it.polimi.ingsw.lb10.client.clidesign.CLIString;
import it.polimi.ingsw.lb10.client.clidesign.ansi.AnsiColor;

import java.util.Scanner;

public abstract class LauncherView {
    public static String runLauncherPage() {
        CLIPage.initialize();
        CLIBanner.displayCodex();


        CLIString welcome = new CLIString(">> Welcome to Codex! <<", AnsiColor.YELLOW, 0, 26);
        CLIString inputError = new CLIString(">> Invalid input <<", AnsiColor.RED, 0, 26);
        CLIString opts = new CLIString(">> Type <SERVER> or <CLIENT> to launch application\n>> ",
                AnsiColor.YELLOW, 0, 27);

        String input;
        Scanner in = new Scanner(System.in);

        welcome.centerPrint();
        opts.centerPrint();

        boolean exit;
        do {
            input = in.nextLine();
            exit = (input.equalsIgnoreCase("CLIENT") || input.equalsIgnoreCase("SERVER"));
            if(!exit){
                welcome.deleteString();
                inputError.centerPrint();
                opts.centerPrint();
                CLIPage.clearLineAfterCursor();
            }
        }while(!exit);

        return input.toLowerCase();

    }
}


