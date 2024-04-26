package it.polimi.ingsw.lb10.client.cli.clipages;

import it.polimi.ingsw.lb10.client.cli.*;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiFormat;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiSpecial;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiString;
import it.polimi.ingsw.lb10.server.model.Player;
import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.Color;
import it.polimi.ingsw.lb10.server.model.cards.GoldenCard;
import it.polimi.ingsw.lb10.server.model.cards.PlaceableCard;
import it.polimi.ingsw.lb10.server.model.cards.ResourceCard;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;
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
    private static final int handCardWidth = 21;

    // -------------- CHAT DATA ---------------- //
    private static final int maxChatLength = 26;
    private static final int maxMessageLength = 38;
    private static final ArrayDeque<CLIString[]> messages = new ArrayDeque<>();     //This queue will contain player's name [0] and message sent [1]
    private static final int[] currentChatPosition = new int[2];

    // ------------ RESOURCE DATA -------------- //
    private static final Map<Resource, int[]> resources = new HashMap<>(){
        {
            put(Resource.ANIMAL, new int[]{78, 35});
            put(Resource.MUSHROOM, new int[]{78, 37});
            put(Resource.PLANT, new int[]{78, 39});
            put(Resource.INSECT, new int[]{78, 41});
            put(Resource.FEATHER, new int[]{86, 35});
            put(Resource.PERGAMENA, new int[]{86, 37});
            put(Resource.POTION, new int[]{86, 39});
        }
    };

    private static final int resourceCounterOffset = 3; //Used to print #resource into resource table

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

    public static class StartingRound implements CLIState {
        @Override
        public void apply(Object[] args) {

        }
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
            CLIBox.draw(2,32, 71, 12, AnsiColor.WHITE);
            CLIBox.draw(2,32, "Hand", AnsiColor.WHITE);
            for (int i = 1; i < 4; i++){
                CLICommand.setPosition(13 + 23*(i-1), 33);
                System.out.println("[" + i + "]");
            }

            // Draw resources board
            CLIBox.draw(74,32, 20, 12, AnsiColor.WHITE);
            CLIBox.draw(74,32, "Resources", AnsiColor.WHITE);
            drawResourceTable();

            // Draw ranking and points
            CLIBox.draw(95,32, 20, 12, AnsiColor.WHITE);
            CLIBox.draw(95,32, "Score board", AnsiColor.WHITE);


            // Draw available objectives
            CLIBox.draw(118,32, 40, 12, AnsiColor.WHITE);
            CLIBox.draw(118,32, "Objectives", AnsiColor.WHITE);
            CLILine.drawVertical(138, 33, 44, AnsiColor.WHITE);

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

    private static void addCardToHand(@NotNull PlaceableCard card, int inHandPosition){
        int col = handUpLeftCornersPosition[inHandPosition][0];
        int row = handUpLeftCornersPosition[inHandPosition][1];

        CLICard.printPlaceableCard(card, col, row);
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
            if(resource.getLetter() == null)
                AnsiString.print(AnsiSpecial.BLOCK.getCode() + ": ", resource.getColor());
            else
                AnsiString.print(resource.getLetter() + ": ", resource.getColor());

            System.out.println("0");
        }

        CLICommand.restoreCursorPosition();
    }

    /**
     * This method is used to update a resource counter on the resource table
     * @param resource Resource to update
     * @param counter New counter value
     */
    public static void updateResourceCounter(Resource resource, int counter){
        CLICommand.setPosition(resources.get(resource)[0] + resourceCounterOffset, resources.get(resource)[1]);
        System.out.println(" ".repeat(3 - String.valueOf(counter).length()));

        CLICommand.setPosition(resources.get(resource)[0] + resourceCounterOffset, resources.get(resource)[1]);
        System.out.println(counter);

        CLICommand.restoreCursorPosition();
    }

    // ------------ SCORE BOARD ---------------- //
    private static void updateScoreBoard(ArrayList<Player> players){

        players.sort(Comparator.comparingInt(Player::getPoints).reversed());  // Lambda for sorting players to make scoreboard

        for(Player player : players){
            CLICommand.setPosition(97, 35 + players.indexOf(player)*2);
            System.out.print(players.indexOf(player) + 1 + "- ");
            new CLIString (
                    player.getUsername(),
                    player.getColor().getAnsi(),
                    AnsiFormat.BOLD,
                    100, 35 + (players.indexOf(player)*2),
                    9
            ).print();

            System.out.print(": " + player.getPoints());
        }

        CLICommand.restoreCursorPosition();
    }

    // ---------------- CHAT ------------------- //
    public static void chatLog(@NotNull Player player, String message) {
        messages.addLast(new CLIString[]{
                new CLIString(player.getUsername() + ": ",
                        player.getColor().getAnsi(),
                        AnsiFormat.BOLD,
                        currentChatPosition[0], currentChatPosition[1], maxMessageLength),

                new CLIString(message.split("\n")[0],
                        AnsiColor.WHITE,
                        AnsiFormat.DEFAULT,
                        currentChatPosition[0] + player.getUsername().length() + 2,
                        currentChatPosition[1],
                        maxMessageLength - (player.getUsername().length() + 2))}
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

        ArrayList<Player> players = new ArrayList<>(Arrays.asList(
                new Player(1, "GUI"),
                new Player(2, "KE_MUSH"),
                new Player(3, "Piggy"),
                new Player(4, "SIMON-LEBOT")
        ));

        for (Player pl : players){
            pl.setColor(Color.values()[players.indexOf(pl)]);
            pl.setPoints(10 * (players.indexOf(pl) + 1));
        }



        ArrayList<Corner> corners1= new ArrayList<>(List.of(new Corner(1,true, Position.BOTTOMLEFT, Resource.FEATHER, Color.BLUE)));
        ArrayList<Corner> corners2= new ArrayList<>(Arrays.asList(
                new Corner(2,true, Position.BOTTOMLEFT, Resource.FEATHER, Color.RED),
                new Corner(2, true, Position.BOTTOMRIGHT, Resource.MUSHROOM, Color.RED),
                new Corner(2, true, Position.TOPLEFT, Resource.ANIMAL, Color.RED)
        ));
        ArrayList<Corner> corners3= new ArrayList<>(Arrays.asList(new Corner(3, true, Position.BOTTOMLEFT, Resource.POTION, Color.GREEN),
                new Corner(3, true, Position.BOTTOMRIGHT, Resource.ANIMAL, Color.GREEN),
                new Corner(3, true, Position.TOPLEFT, Resource.PLANT, Color.GREEN),
                new Corner(3, false, Position.TOPRIGHT, Resource.MUSHROOM, Color.GREEN)
        ));

        addCardToHand(new ResourceCard(1, Color.BLUE, corners1, 0, Resource.ANIMAL, null), 0);
        addCardToHand(new ResourceCard(2, Color.RED, corners2, 3, Resource.MUSHROOM, null), 1);
        addCardToHand(new GoldenCard(3, Color.GREEN, corners3,  3, Resource.PLANT, Resource.FEATHER , new HashMap<>(
                Map.of(Resource.ANIMAL, 3, Resource.MUSHROOM, 2)
        )), 2);


        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            removeCardFromHand(i);
        }

        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        addCardToHand(new ResourceCard(1, Color.PURPLE, corners1, 0, Resource.INSECT, null), 0);
        addCardToHand(new ResourceCard(2, Color.GREEN, corners2, 3, Resource.MUSHROOM, null), 1);
        addCardToHand(new GoldenCard(3, Color.BLUE, corners3,  3, Resource.PLANT, Resource.FEATHER , new HashMap<>(
                Map.of(Resource.ANIMAL, 3, Resource.MUSHROOM, 2)
        )), 2);

        updateResourceCounter(Resource.ANIMAL, 3);
        updateResourceCounter(Resource.MUSHROOM, 2);
        updateResourceCounter(Resource.PLANT, 1);
        updateResourceCounter(Resource.INSECT, 5);
        updateResourceCounter(Resource.FEATHER, 0);

        for (int i = 0; i < 50; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            chatLog(players.get(i % 4), "Lorem ipsum sit amet ".repeat(i % 7));
        }


        updateScoreBoard(players);
    }
}



