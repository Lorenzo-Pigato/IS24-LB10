package it.polimi.ingsw.lb10.client.cli.clipages;

import it.polimi.ingsw.lb10.client.view.State;

public interface CLIState extends State {
    void apply(String[] args);
}
