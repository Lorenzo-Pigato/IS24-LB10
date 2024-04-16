package it.polimi.ingsw.lb10.client.cli.clipages;

import it.polimi.ingsw.lb10.client.cli.CLIBox;
import it.polimi.ingsw.lb10.client.cli.CLICommand;
import it.polimi.ingsw.lb10.client.cli.CLILine;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiFormat;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;
import org.jetbrains.annotations.NotNull;

public class CLIMatchPage implements CLIPage{

    private CLIState state = new Default();
    private static final int[] HandUpLeftCornersPosition = {};
    @Override
    public void changeState(@NotNull CLIState state) {
        this.state = state;
    }

    @Override
    public void print(Object[] args) {
        this.state.apply(args);
    }

    public static class Default implements CLIState{
        @Override
        public void apply(Object[] args) {
            CLICommand.initialize();
            // Draw the board
            CLIBox.draw(2,2,113,30, AnsiColor.CYAN);
            CLIBox.draw(2,2, "Player Board: " + (args != null ? args[0] : "Unknown") ,
                    AnsiColor.CYAN,
                    AnsiColor.WHITE,
                    AnsiFormat.BOLD);

            // Draw the chat
            CLIBox.draw(118,2, 40, 30, AnsiColor.PURPLE);
            CLIBox.draw(118,2, 40,3, "Chat", AnsiColor.PURPLE, AnsiColor.WHITE, AnsiFormat.BOLD);

            // Draw the hand
            CLIBox.draw(2,32, 70, 12, AnsiColor.WHITE);
            CLIBox.draw(2,32, "Hand", AnsiColor.WHITE);

            // Draw available resources
            CLIBox.draw(75,32, 40, 12, AnsiColor.WHITE);
            CLIBox.draw(75,32, "Resources", AnsiColor.WHITE);

            // Draw available objectives
            CLIBox.draw(118,32, 40, 12, AnsiColor.WHITE);
            CLIBox.draw(118,32, "Objectives", AnsiColor.WHITE);
            CLILine.drawVertical(138, 33, 44);

            CLILine.drawHorizontal(2, 44,158);

            CLIBox.draw(10,10,6,3, AnsiColor.RED);
        }
    }

    // ------------- TEST ---------------- //
    public static void main(Object[] args) {
        new CLIMatchPage().print(null);
    }

    private static void drawCorner(Corner corner, int col, int row){
        CLICommand.setPosition(col, row);
        // ------ NOT IMPLEMENTED YET ------ //

    }
}


