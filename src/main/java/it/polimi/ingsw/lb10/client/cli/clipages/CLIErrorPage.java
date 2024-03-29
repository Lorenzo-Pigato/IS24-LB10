package it.polimi.ingsw.lb10.client.cli.clipages;

import it.polimi.ingsw.lb10.client.cli.CLIBanner;
import it.polimi.ingsw.lb10.client.cli.CLICommand;
import it.polimi.ingsw.lb10.client.cli.CLIString;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiFormat;
import org.jetbrains.annotations.NotNull;

public class CLIErrorPage implements CLIPage {

    public static class Default implements CLIState {
        @Override
        public void apply(String @NotNull [] args){
            CLICommand.initialize();
            CLIBanner.display404();
            new CLIString(args[0], AnsiColor.RED, AnsiFormat.BOLD, 1, 33).centerPrint();
            new CLIString(args[1] + "\n>> Quitting", AnsiColor.RED, 1, 35).centerPrint();

            CLICommand.setPosition(1, 50);
        }
    }
}

