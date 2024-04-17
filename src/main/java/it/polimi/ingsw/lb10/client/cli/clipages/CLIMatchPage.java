package it.polimi.ingsw.lb10.client.cli.clipages;

import it.polimi.ingsw.lb10.client.cli.CLIBox;
import it.polimi.ingsw.lb10.client.cli.CLICommand;
import it.polimi.ingsw.lb10.client.cli.CLILine;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiFormat;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiString;
import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.Card;
import it.polimi.ingsw.lb10.server.model.cards.Color;
import it.polimi.ingsw.lb10.server.model.cards.GoldenCard;
import it.polimi.ingsw.lb10.server.model.cards.ResourceCard;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;
import it.polimi.ingsw.lb10.server.model.cards.corners.CornerAvailable;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CLIMatchPage implements CLIPage{

    private CLIState state = new Default();
    private static final int[][] handUpLeftCornersPosition = {
            {4, 35},
            {27, 35},
            {50, 35}
    };


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

            CLICommand.setPosition(3,47);
            CLICommand.saveCursorPosition();
        }
    }

    private static void drawHandCorner(Corner corner, int col, int row) {
        if(corner instanceof CornerAvailable) {
            CLIBox.draw(col + corner.getPosition().getCliColOffset(),
                    row + corner.getPosition().getCliRowOffset(),
                    5, 3,
                    corner.getResource().getLetter() != null ? corner.getResource().getLetter() : "",
                    corner.getResource().getColor(),
                    corner.getResource().getColor(),
                    AnsiFormat.BOLD
            );
        }
    }

    private static void drawBoardCorner(Corner corner, int col, int row){
        CLICommand.setPosition(col, row);
        // ------ NOT IMPLEMENTED YET ------ //

    }

    private static void addCardToHand(@NotNull Card card, int inHandPosition){
        int col = handUpLeftCornersPosition[inHandPosition][0];
        int row = handUpLeftCornersPosition[inHandPosition][1];

        CLIBox.draw(col, row, 20, 8, card.getColor().getAnsi());

        for (Corner corner : card.getStateCardCorners()){
            drawHandCorner(corner, col, row);
        }

        if (card.getPoints() > 0){
            if(card instanceof GoldenCard) {
                // ------ NOT IMPLEMENTED YET ------ //
            }
            else {
                CLICommand.setPosition(col + 9, row+1);
                AnsiString.print(
                        card.getStateCardPoints() + "",
                        AnsiColor.YELLOW,
                        AnsiFormat.BOLD);
            }
        }

        CLICommand.restoreCursorPosition();
    }


    // ------------- TEST ---------------- //
    public static void main(String[] args) {
        new CLIMatchPage().print(null);
        ArrayList<Corner> corners1= new ArrayList<>(List.of(new CornerAvailable(Position.BOTTOMLEFT, Resource.FEATHER)));
        ArrayList<Corner> corners2= new ArrayList<>(Arrays.asList(new CornerAvailable(Position.BOTTOMLEFT, Resource.FEATHER),
                new CornerAvailable(Position.BOTTOMRIGHT, Resource.MUSHROOM),
                new CornerAvailable(Position.TOPLEFT, Resource.ANIMAL)
        ));
        ArrayList<Corner> corners3= new ArrayList<>(Arrays.asList(new CornerAvailable(Position.BOTTOMLEFT, Resource.POTION),
                new CornerAvailable(Position.BOTTOMRIGHT, Resource.ANIMAL),
                new CornerAvailable(Position.TOPLEFT, Resource.PLANT)
        ));

        addCardToHand(new ResourceCard(1, false, 5, corners1, Resource.ANIMAL, Color.BLUE, null, null), 0);
        addCardToHand(new ResourceCard(2, false, 0, corners2, Resource.MUSHROOM, Color.RED, null, null), 1);
        addCardToHand(new ResourceCard(3, false, 1, corners3, Resource.PLANT, Color.GREEN, null, null), 2);
    }
}



