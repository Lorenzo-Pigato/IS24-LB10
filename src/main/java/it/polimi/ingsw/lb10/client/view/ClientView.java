package it.polimi.ingsw.lb10.client.view;

public interface ClientView {
    /**
     * Pages are a container for states, which are the actual views
     * Setting pages allows to access pages' states
     * @param page to set
     */
    void setPage(Page page);

    /**
     * Method to set a state for the page
     * Setting a state is necessary to display the page
     * @param state to display
     * @param args to update a state if needed
     */
    void pageStateDisplay(State state, String[] args);
}
