package it.polimi.ingsw.lb10.client.view;

public interface ClientView {
    /**
     * Pages are a container for states, which are the actual views
     * Setting pages allows to access pages' states
     * @param page to set
     */
    void setPage(Page page);

    /**
     * This method updates the state of the page
     * @param state to be displayed
     */
    public void updatePageState(State state);

    /**
     * This method applies the state of the page, effectively printing the page or the updated elements
     * @param args to provide to the state, when needed
     */
    public void displayPage(String[] args);

    public Page getPage();
}
