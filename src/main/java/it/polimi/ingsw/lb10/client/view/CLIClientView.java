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
     * This method updates the state of the page
     * @param state to be displayed
     */
    @Override
    public void updatePageState(State state) {
        page.changeState((CLIState) state);
    }

    /**
     * This method applies the state of the page, effectively printing the page or the updated elements
     * @param args to provide to the state, when needed
     */
    @Override
    public void displayPage(String[] args) {
        page.print(args);
    }

    public CLIPage getPage() {
        return this.page;
    }
}