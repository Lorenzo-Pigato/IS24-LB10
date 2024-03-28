package it.polimi.ingsw.lb10.client.view;

import it.polimi.ingsw.lb10.client.clidesign.clipages.CLIPage;

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








