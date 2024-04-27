package it.polimi.ingsw.lb10.client.cli.clipages;

import it.polimi.ingsw.lb10.client.cli.*;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiFormat;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiSpecial;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiString;
import it.polimi.ingsw.lb10.server.model.Matrix;
import it.polimi.ingsw.lb10.server.model.Player;
import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.*;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.Diagonal.BottomLeftDiagonal;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.LJ.TopRight;
import it.polimi.ingsw.lb10.server.model.quest.Quest;
import it.polimi.ingsw.lb10.server.model.quest.QuestCounter;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class CLIMatchPage implements CLIPage{

    private CLIState state = new StartingTurn();

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

    // -------------- BOARD DATA -------------- //
    private static final int boardStartCol = 3;
    private static final int boardStartRow = 3;

    private Matrix board;

    public void setBoard(Matrix board) {
        this.board = board;
    }

    // -------------- CONSTRUCTOR -------------- //

    public CLIMatchPage(){
        currentChatPosition[0] = 119;
        currentChatPosition[1] = 5;
        this.board = null;
    }

    @Override
    public void changeState(@NotNull CLIState state) {
        this.state = state;
    }

    @Override
    public void print(Object[] args) {
        this.state.apply(args);
    }

    // -------------- STATES -------------- //

    /**
     * This state is used to display the game interface during the starting turn, initializing the board,
     * player's hand and quest and displaying the frames for the chat and the starting card
     */
    public static class StartingTurn implements CLIState {
        /**
         * @param args Player object,  Starting Card, Private Quest, Public Quest 1, Public Quest 2
         */
        @Override
        public void apply(Object[] args) {
            CLICommand.initialize();
            // Draw the board
            CLIBox.draw(2,2,113,30, AnsiColor.CYAN);
            CLIBox.draw(2,2, "Player Board: " + (args != null ? ((Player)args[0]).getUsername() : "Unknown") ,
                    AnsiColor.CYAN,
                    AnsiColor.WHITE,
                    AnsiFormat.BOLD);

            // Draw chat table
            CLIBox.draw(118,2, 40, 30, AnsiColor.PURPLE);
            CLIBox.draw(118,2, 40,3, "Chat", AnsiColor.PURPLE, AnsiColor.WHITE, AnsiFormat.BOLD);

            // Draw hand
            CLIBox.draw(2,32, 71, 12, AnsiColor.WHITE);
            CLIBox.draw(2,32, "Hand", AnsiColor.WHITE);
            for (int i = 0; i < 3; i++){
                CLICommand.setPosition(13 + 23*i, 33);
                System.out.println("[" + (i+1) + "]");
            }

            // Draw available objectives
            CLIBox.draw(118,32, 40, 12, AnsiColor.WHITE);
            CLIBox.draw(118,32, "Objectives", AnsiColor.WHITE);
            CLILine.drawVertical(138, 33, 44, AnsiColor.WHITE);

            assert args != null;
            CLICard.displayQuestCard((Quest) args[2], 121, 36);
            CLICard.displayQuestCard((Quest) args[3], 140, 33);
            CLICard.displayQuestCard((Quest) args[4],140,38);


            // Draw Starting Card
            CLIBox.draw(74, 32, 41, 12, AnsiColor.WHITE);
            CLIBox.draw(74, 32, "Starting Card", AnsiColor.WHITE);
            CLICard.displayStartingCard((StartingCard) args[1], 84, 35);


            // User inp0ut region
            CLILine.drawHorizontal(2, 44,158, AnsiColor.WHITE);

            CLICommand.setPosition(2,47);
            AnsiString.print(">> ", AnsiColor.CYAN, AnsiFormat.BOLD);

            CLICommand.saveCursorPosition();
        }

        public static void flipStartingCard(StartingCard card) {
            CLICard.displayStartingCard(card, 84, 35);
        }
    }

    /**
     * This state is used to display the game interface during normal match turns
     * It modifies the StartingTurn state by adding the resources table and the score board
     */
    public static class Default implements CLIState{
        @Override
        public void apply(Object[] args) {

            // Clear starting card
            clearRegion(74, 32, 41, 12);
            // Draw resources board
            CLIBox.draw(74,32, 20, 12, AnsiColor.WHITE);
            CLIBox.draw(74,32, "Resources", AnsiColor.WHITE);
            drawResourceTable();

            // Draw ranking and points
            CLIBox.draw(95,32, 20, 12, AnsiColor.WHITE);
            CLIBox.draw(95,32, "Score board", AnsiColor.WHITE);

        }
    }

    // -------------- UTIL ----------------- //

    private static void clearRegion (int col, int row, int width, int height){
        for (int i = 0; i < height; i++){
            CLICommand.setPosition(col, row+i);
            System.out.println(" ".repeat(width));
        }

        CLICommand.restoreCursorPosition();
    }

    // ----------- SERVER REPLY------------- //

    public static void serverReply(String message){
        CLICommand.setPosition(2, 45);
        CLICommand.clearLine();

        AnsiString.print(">> " + message, AnsiColor.CYAN, AnsiFormat.BOLD);
    }

    // -------------- BOARD ---------------- //

    private static void drawBoardCorner(Corner corner, int col, int row){
        CLICommand.setPosition(col, row);
        // ------ NOT IMPLEMENTED YET ------ //

    }

    // ---------------- HAND ---------------- //

    public static void addCardToHand(@NotNull PlaceableCard card, int inHandPosition){
        int col = handUpLeftCornersPosition[inHandPosition][0];
        int row = handUpLeftCornersPosition[inHandPosition][1];

        CLICard.printPlaceableCard(card, col, row);
        CLICommand.restoreCursorPosition();
    }

    public static void removeCardFromHand(int inHandPosition){
        clearRegion(
                handUpLeftCornersPosition[inHandPosition][0],
                handUpLeftCornersPosition[inHandPosition][1],
                handCardWidth,
                handCardHeight
        );
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
        ArrayList<Player> players = new ArrayList<>(Arrays.asList(
                new Player(1, "GUI"),
                new Player(2, "KE_MUSH"),
                new Player(3, "Piggy"),
                new Player(4, "SIMON-LEBOT")
        ));

        StartingCard starter =  new StartingCard(1, Color.STARTER
                , new ArrayList<>(
                List.of(new Corner(1,true, Position.BOTTOMLEFT, Resource.FEATHER, Color.BLUE)))
                , new ArrayList<>(
                List.of(new Corner(1,true, Position.TOPRIGHT, Resource.ANIMAL, Color.BLUE))), new ArrayList<>(List.of(Resource.ANIMAL, Resource.MUSHROOM, Resource.INSECT)));

        CLIMatchPage match = new CLIMatchPage();
        match.print(new Object[]{
                players.get(3),
                starter,
                new BottomLeftDiagonal(1, 3, Color.RED),
                new TopRight(2, 3, Color.GREEN, Color.RED),
                new QuestCounter(94, 3, new HashMap<>(
                        Map.of(Resource.FEATHER, 3, Resource.POTION, 2, Resource.PERGAMENA, 2)
                ))
        });


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


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        starter.setFlippedState();
        StartingTurn.flipStartingCard(starter);

        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            removeCardFromHand(i);
            starter.setNotFlippedState();
            StartingTurn.flipStartingCard(starter);
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


        match.changeState(new Default());
        match.print(null);

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

        serverReply("AJO GUIDO C'È LA NEVE");

        updateScoreBoard(players);
    }
}



