package it.polimi.ingsw.lb10.client.clidesign.clipages;

import it.polimi.ingsw.lb10.client.clidesign.CLIBanner;
import it.polimi.ingsw.lb10.client.clidesign.CLICommand;
import it.polimi.ingsw.lb10.client.clidesign.CLIString;
import it.polimi.ingsw.lb10.client.clidesign.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.clidesign.ansi.AnsiFormat;

public  class CLI404Page implements CLIPage {
    private static final CLIString notFound = new CLIString(">> Server couldn't be reached <<", AnsiColor.CYAN, AnsiFormat.BOLD, 1, 33);
    private static final CLIString reconnect = new CLIString(">> Quitting <<", AnsiColor.CYAN, 1, 35);

    @Override
    public void display(){
        CLICommand.initialize();
        CLIBanner.display404();
        notFound.centerPrint();
        reconnect.centerPrint();

        CLICommand.setPosition(1, 50);
    }

    @Override
    public void update(CLIState state) {

    }

}
