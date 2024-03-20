package it.polimi.ingsw.lb10.client.view;

import it.polimi.ingsw.lb10.client.clidesign.CLIBanner;
import it.polimi.ingsw.lb10.client.clidesign.CLIBox;
import it.polimi.ingsw.lb10.client.clidesign.CLIPage;
import it.polimi.ingsw.lb10.client.clidesign.CLIString;
import it.polimi.ingsw.lb10.client.clidesign.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.clidesign.ansi.AnsiFormat;
import it.polimi.ingsw.lb10.client.clidesign.ansi.AnsiString;

import java.util.Scanner;

public abstract class PageViewer {
    public static void printLauncherPage() {
        CLIPage.initialize();
        CLIBanner.displayCodexBlocks(AnsiColor.GREEN);

        parseInput();

    }

     private static void parseInput(){
        String input;
        new CLIString("\n   >> Welcome to Codex!\n   >> Type <SERVER> or <CLIENT> to launch application\n   >> ",
                AnsiColor.YELLOW, 0, 20).print();

        Scanner in = new Scanner(System.in);
        boolean exit;

        do {
            input = in.nextLine();
            exit = (input.equalsIgnoreCase("CLIENT") || input.equalsIgnoreCase("SERVER"));
            if(!exit){
                CLIPage.initialize();
                CLIBanner.displayCodexBlocks(AnsiColor.GREEN);

                new CLIString("\n   >> Invalid input\n   >> Type <SERVER> or <CLIENT> to launch application\n   >> ",
                        AnsiColor.RED, 0, 20).print();

            }
        }while(!exit);

        CLIPage.initialize();


    }
}


