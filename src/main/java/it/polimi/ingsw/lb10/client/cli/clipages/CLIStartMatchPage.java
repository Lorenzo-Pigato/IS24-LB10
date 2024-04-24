package it.polimi.ingsw.lb10.client.cli.clipages;

import it.polimi.ingsw.lb10.client.cli.CLIBanner;
import it.polimi.ingsw.lb10.client.cli.CLIBox;
import it.polimi.ingsw.lb10.client.cli.CLICommand;
import it.polimi.ingsw.lb10.client.cli.CLIString;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiFormat;
import org.jetbrains.annotations.NotNull;

public class CLIStartMatchPage implements CLIPage {
    private static final CLIString notFound = new CLIString(">> Choose [1] or [2] <<", AnsiColor.RED, AnsiFormat.BOLD, 1, 38);
    private static final CLIString chooseQuest = new CLIString(">> Choose a quest to start the match <<", AnsiColor.CYAN, AnsiFormat.BOLD, 1, 38);

    private CLIState state = new CLIStartMatchPage.Default();

    @Override
    public void changeState(@NotNull CLIState state) {
        this.state = state;
    }

    @Override
    public void print(Object[] args) {
        state.apply(args);
    }

    public static class Default implements CLIState {
        @Override
        public void apply(Object[] args) {
            CLICommand.initialize();
            CLIBanner.displayChooseQuest();

            CLIBox.draw(30,21,40,15,AnsiColor.WHITE);
            CLIBox.draw(90,21,40,15,AnsiColor.WHITE);


        }

    }
}
