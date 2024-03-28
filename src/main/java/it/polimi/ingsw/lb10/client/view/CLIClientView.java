package it.polimi.ingsw.lb10.client.view;

import it.polimi.ingsw.lb10.client.cli.clipages.CLIPage;
import it.polimi.ingsw.lb10.client.cli.clipages.CLIState;

/**
 * This class provides the implementation of the ClientView interface (for the Command Line version of UI)
 */
public class CLIClientView implements ClientView {
    private CLIPage page;

    /**
     * This method sets the page of which states will be displayed
     * @param page to be set
     */
    @Override
    public void setPage(Page page) {
        this.page = (CLIPage) page;
    }

    /**
     * This method displays the actual state of the page
     * @param state to be displayed
     * @param args to be passed to update a view, if needed
     */
    @Override
    public void pageStateDisplay(State state, String[] args) {
        page.display((CLIState) state, args);
    }

    public CLIPage getPage() {
        return this.page;
    }
}








