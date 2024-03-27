package it.polimi.ingsw.lb10.client.view;

import it.polimi.ingsw.lb10.client.Client;
import it.polimi.ingsw.lb10.client.clidesign.CLIBanner;
import it.polimi.ingsw.lb10.client.clidesign.CLICommand;
import it.polimi.ingsw.lb10.client.clidesign.CLIString;
import it.polimi.ingsw.lb10.client.clidesign.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.clidesign.clipages.CLIConnectionPage;
import it.polimi.ingsw.lb10.client.clidesign.clipages.CLIPage;
import it.polimi.ingsw.lb10.client.controller.CLIClientViewController;
import it.polimi.ingsw.lb10.client.controller.ClientViewController;

/**
 * This class provides the implementation of the ClientView interface (for the Command Line version of UI)
 */
public class CLIClientView implements ClientView {

    private CLIPage page;


    @Override
    public void displayPage() {
        page.display();
    }

    public CLIPage getPage() {
        return page;
    }

    public void setPage(CLIPage page) {
        this.page = page;
    }

}








