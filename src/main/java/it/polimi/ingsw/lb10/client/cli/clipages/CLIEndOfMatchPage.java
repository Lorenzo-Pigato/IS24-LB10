package it.polimi.ingsw.lb10.client.cli.clipages;

import it.polimi.ingsw.lb10.client.cli.CLIBanner;
import it.polimi.ingsw.lb10.client.cli.CLIBox;
import it.polimi.ingsw.lb10.client.cli.CLICommand;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiFormat;
import it.polimi.ingsw.lb10.client.controller.CLIClientViewController;
import it.polimi.ingsw.lb10.server.model.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;

public class CLIEndOfMatchPage implements CLIPage {

    private CLIState state = new Default();

    @Override
    public void changeState(@NotNull CLIState state) {
        this.state = state;
    }

    @Override
    public void print(Object[] args) {
        state.apply(args);
    }

    public static class Default implements CLIState {
        /**
         * @param args Player thisPlayer, ArrayList<Player> players
         */
        @Override
        public void apply(Object[] args) {
            CLICommand.initialize();

            if(((ArrayList<Player>)args[1]).size() > 1){
                Player thisPlayer = (Player) args[0];
                ArrayList<Player> allPlayers = (ArrayList<Player>) args[1];

                allPlayers.sort(Comparator.comparingInt(Player::getPoints).reversed());

                if (thisPlayer.getUsername().equals(allPlayers.getFirst().getUsername())) CLIBanner.displayWinner();
                else CLIBanner.displayLoser();

                StringBuilder scoreboard = new StringBuilder();
                for (Player player : allPlayers)
                    scoreboard.append((allPlayers.indexOf(player) + 1))
                            .append("- ")
                            .append(player.getUsername())
                            .append("\t POINTS: ")
                            .append(player.getPoints())
                            .append("\n\n");

                CLIBox.draw(60, 25, 40, 15, scoreboard.toString(), AnsiColor.CYAN, AnsiColor.WHITE, AnsiFormat.BOLD);
                CLIBox.draw(60, 25, 40, 3, "SCOREBOARD", AnsiColor.PURPLE, AnsiColor.WHITE, AnsiFormat.BOLD);
            }
            else{
                CLIBanner.displayAllPlayersLeft();
            }

            CLICommand.setPosition(1, 49);
        }
    }
}

