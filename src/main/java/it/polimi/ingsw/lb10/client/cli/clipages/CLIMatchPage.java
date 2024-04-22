package it.polimi.ingsw.lb10.client.cli.clipages;

import it.polimi.ingsw.lb10.client.cli.CLIBox;
import it.polimi.ingsw.lb10.client.cli.CLICommand;
import it.polimi.ingsw.lb10.client.cli.CLILine;
import it.polimi.ingsw.lb10.client.cli.CLIString;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiFormat;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiSpecial;
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

import java.util.*;

public class CLIMatchPage implements CLIPage{

    private CLIState state = new Default();

    // -------------- HAND DATA ---------------- //
    private static final int[][] handUpLeftCornersPosition = {
            {4, 35},
            {27, 35},
            {50, 35}
    };

    private static final int handCardHeight = 8;
    private static final int handCardWidth = 20;

    // -------------- CHAT DATA ---------------- //
    private static final int maxChatLength = 26;
    private static final int maxMessageLength = 38;
    private static final ArrayDeque<CLIString[]> messages = new ArrayDeque<>();     //This queue will contain player's name [0] and message sent [1]
    private static final int[] currentChatPosition = new int[2];

    // ------------ RESOURCE DATA -------------- //
    private static final Map<Resource, int[]> resources = new HashMap<>(){
        {
            put(Resource.ANIMAL, new int[]{80, 35});
            put(Resource.FEATHER, new int[]{80, 38});
            put(Resource.MUSHROOM, new int[]{80, 41});
            put(Resource.PLANT, new int[]{80, 44});
            put(Resource.POTION, new int[]{80, 47});
        }
    };

    public CLIMatchPage(){
        currentChatPosition[0] = 119;
        currentChatPosition[1] = 5;
    }

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

            // Draw chat table
            CLIBox.draw(118,2, 40, 30, AnsiColor.PURPLE);
            CLIBox.draw(118,2, 40,3, "Chat", AnsiColor.PURPLE, AnsiColor.WHITE, AnsiFormat.BOLD);

            // Draw hand
            CLIBox.draw(2,32, 70, 12, AnsiColor.WHITE);
            CLIBox.draw(2,32, "Hand", AnsiColor.WHITE);

            // Draw resources board
            CLIBox.draw(75,32, 40, 12, AnsiColor.WHITE);
            CLIBox.draw(75,32, "Resources", AnsiColor.WHITE);

            // Draw available objectives
            CLIBox.draw(118,32, 40, 12, AnsiColor.WHITE);
            CLIBox.draw(118,32, "Objectives", AnsiColor.WHITE);
            CLILine.drawVertical(138, 33, 44, AnsiColor.WHITE);
            drawResourceTable();

            CLILine.drawHorizontal(2, 44,158, AnsiColor.WHITE);

            CLICommand.setPosition(3,47);
            CLICommand.saveCursorPosition();
        }
    }

    // -------------- BOARD ---------------- //

    private static void drawBoardCorner(Corner corner, int col, int row){
        CLICommand.setPosition(col, row);
        // ------ NOT IMPLEMENTED YET ------ //

    }

    // ---------------- HAND ---------------- //

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

    private static void addCardToHand(@NotNull Card card, int inHandPosition){
        int col = handUpLeftCornersPosition[inHandPosition][0];
        int row = handUpLeftCornersPosition[inHandPosition][1];

        CLIBox.draw(col, row, handCardWidth, handCardHeight, card.getColor().getAnsi());

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

    public static void removeCardFromHand(int inHandPosition){
        int col = handUpLeftCornersPosition[inHandPosition][0];
        int row = handUpLeftCornersPosition[inHandPosition][1];

        for(int i = 0; i < handCardHeight; i++){
            CLICommand.setPosition(col, row+i);
            System.out.print(" ".repeat(handCardWidth));
        }

        CLICommand.restoreCursorPosition();
    }

    // ------------ RESOURCES -------------- //

    private static void drawResourceTable(){
        for (Resource resource : resources.keySet()){
            CLICommand.setPosition(resources.get(resource)[0], resources.get(resource)[1]);
            AnsiString.print(AnsiSpecial.BLOCK.getCode(), resource.getColor());
        }
    }

    public static void drawResource(Resource resource, int col, int row){
        CLIBox.draw(col, row, 5, 3, resource.getLetter(), resource.getColor(), resource.getColor(), AnsiFormat.BOLD);
    }

    // ---------------- CHAT ------------------- //
    public static void chatLog(@NotNull String username, String message, AnsiColor playerColor){
        messages.addLast(new CLIString[]{
                new CLIString(username + ": ",
                        playerColor,
                        AnsiFormat.BOLD,
                        currentChatPosition[0], currentChatPosition[1], maxMessageLength),

                new CLIString(message.split("\n")[0],
                        AnsiColor.WHITE,
                        AnsiFormat.DEFAULT,
                        currentChatPosition[0] + username.length() + 2,
                        currentChatPosition[1],
                        maxMessageLength - (username.length() + 2))}
        );

        if (messages.size() > maxChatLength){
            messages.removeFirst();

            messages.forEach(m -> {
                m[0].reposition(m[0].getPosition()[0], m[0].getPosition()[1]-1);
                m[1].reposition(m[1].getPosition()[0], m[1].getPosition()[1]-1);
                m[0].print();
                m[1].print();
            });
        } else {
            messages.getLast()[0].print();
            messages.getLast()[1].print();
            currentChatPosition[1]++;
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


        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

            removeCardFromHand(i);
        }

        for (int i = 0; i < 60; i++) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

            chatLog("Player" + i, "lorem ipsum sit amet consectetur adipiscing elit", AnsiColor.CYAN);
        }

    }
}



