package it.polimi.ingsw.lb10.client.controller;

public interface ClientViewController {

    /**
     * This method provides logic to build the structure of UI and communication between Server and Client (creates communication threads)
     */
    public void launch();

    /**
     * This method is used to set up the communication streams with the Server
     */
    public void setUp();

}






