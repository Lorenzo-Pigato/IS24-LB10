package it.polimi.ingsw.lb10.client.cli.clipages;

import it.polimi.ingsw.lb10.client.cli.*;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiFormat;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiSpecial;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiString;
import it.polimi.ingsw.lb10.client.controller.CLIClientViewController;
import it.polimi.ingsw.lb10.server.model.Matrix;
import it.polimi.ingsw.lb10.server.model.Player;
import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.*;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;
import it.polimi.ingsw.lb10.server.model.quest.Quest;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class CLIMatchPage implements CLIPage {

    private CLIState state = new StartingTurn();

    // -------------- CARDS DATA ---------------- //
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
    private static final Map<Resource, int[]> onBoardResourcesPositions = new HashMap<>() {
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

    //-----------------PLAYERS------------------//
    private static ArrayList<Player> allPlayers;
    private static Player clientPlayer;

    public static void setPlayers(ArrayList<Player> players) {
        allPlayers = players;
    }

    public static void setPlayer(Player clientPlayer1) {
        clientPlayer = clientPlayer1;
    }

    public static void removePlayer(String username) {
        if(allPlayers == null) return;

        allPlayers.remove(allPlayers.stream().filter(player -> player.getUsername().equals(username)).findFirst().orElse(null));
        if(!CLIClientViewController.instance().startingCardHasNotBeenPlaced()) updateScoreBoard();
    }


    // -------------- BOARD DATA -------------- //
    private static final int boardStartCol = 4;
    private static final int boardStartRow = 6;

    /**
     * This set of variables is used to define the board focus area,
     * which is the portion of the board which can be viewed by the player
     */
    private static final int defaultOnFocusCol = 23;
    private static final int defaultOnFocusRow = 35;

    private static final int onFocusWidth = 36;
    private static final int onFocusHeight = 12;

    private static int onFocusCol = defaultOnFocusCol;
    private static int onFocusRow = defaultOnFocusRow;

    public static void printBoard(Matrix board) {
        clientPlayer.setMatrix(board);

        clearRegion(boardStartCol, boardStartRow - 1, onFocusWidth * 3 + 2, onFocusHeight * 2 + 1);
        for (int col = onFocusCol; col < onFocusCol + onFocusWidth; col++)
            for (int row = onFocusRow; row < onFocusRow + onFocusHeight; row++)
                if (!board.getNode(row, col).getCorners().isEmpty())
                    for (Corner corner : board.getNode(row, col).getCorners())
                        CLICard.displayCorner(
                                corner,
                                (col - onFocusCol) * 3 + boardStartCol,
                                (row - onFocusRow) * 2 + boardStartRow
                        );

        moveToInputField();
    }

    public static void moveBoard(Matrix board, int colOffset, int rowOffset) {
        clearRegion(boardStartCol, boardStartRow - 1, onFocusWidth * 3 + 2, onFocusHeight * 2 + 1);

        onFocusCol += colOffset;
        onFocusRow += rowOffset;
        if (onFocusCol < 0 || onFocusRow < 0) {
            onFocusCol = 0;
            onFocusRow = 0;
            CLIMatchPage.serverReply("Invalid <Move Board Coordinates>, positioning at coordinates [0 ; 0]");
        }
        if (onFocusCol > 83 - onFocusWidth)
            onFocusCol = 83 - onFocusWidth;
        if (onFocusRow > 83 - onFocusHeight)
            onFocusRow = 83 - onFocusHeight;

        printBoard(board);
    }

    public void resetBoardView() {
        clearRegion(boardStartCol, boardStartRow - 1, onFocusWidth * 3 + 2, onFocusHeight * 2 + 1);

        onFocusCol = defaultOnFocusCol;
        onFocusRow = defaultOnFocusRow;

        printBoard(clientPlayer.getMatrix());
    }

    /**
     * @param card           the card to be placed on the board
     * @param col            the column OF THE MATRIX where the card will be placed
     * @param row            the row OF THE MATRIX where the card will be placed
     * @param inHandPosition the position of the card in the player's hand, if the card in not the starting card
     */
    public static void placeCard(@NotNull BaseCard card, int col, int row, Integer inHandPosition) {

        if (inHandPosition != null) removeCardFromHand(inHandPosition);

        for (Corner corner : card.getStateCardCorners())
            CLICard.displayCorner(corner,
                    (col - onFocusCol) * 3 + boardStartCol +
                            (corner.getPosition().getCliColOffset() > 0 ? 3 : 0),
                    (row - onFocusRow) * 2 + boardStartRow +
                            (corner.getPosition().getCliRowOffset() > 0 ? 2 : 0));

        if(card instanceof PlaceableCard) clientPlayer.getMatrix().setCard((PlaceableCard) card, row, col);
        else clientPlayer.getMatrix().setCard((StartingCard) card);

        CLICommand.restoreCursorPosition();
    }


    // -------------- CONSTRUCTOR -------------- //

    public CLIMatchPage() {
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

    // -------------- STATES -------------- //

    /**
     * This state is used to display the game interface during the starting turn, initializing the board,
     * player's hand and quest and displaying the frames for the chat and the starting card
     */
    public static class StartingTurn implements CLIState {
        /**
         * @param args Player, StartingCard, Quest privateQuest, ArrayList<Quest> publicQuests, ArrayList<PlaceableCard> hand
         */
        @Override
        public void apply(Object[] args) {
            CLICommand.initialize();

            Player player = (Player) args[0];
            StartingCard startingCard = (StartingCard) args[1];
            Quest privateQuest = (Quest) args[2];
            ArrayList<Quest> publicQuests = new ArrayList<>((ArrayList<Quest>) args[3]);
            ArrayList<PlaceableCard> hand = (ArrayList<PlaceableCard>) args[4];

            // Draw the board
            CLIBox.draw(2, 2, 113, 30, AnsiColor.CYAN);
            CLIBox.draw(2, 2, "Player Board: " + (player != null ? player.getUsername() : "Unknown"),
                    AnsiColor.CYAN,
                    AnsiColor.WHITE,
                    AnsiFormat.BOLD);

            // Draw chat table
            CLIBox.draw(118, 2, 40, 30, AnsiColor.PURPLE);
            CLIBox.draw(118, 2, 40, 3, "Chat", AnsiColor.PURPLE, AnsiColor.WHITE, AnsiFormat.BOLD);

            // Draw hand
            CLIBox.draw(2, 32, 71, 12, AnsiColor.WHITE);
            CLIBox.draw(2, 32, "Hand", AnsiColor.WHITE);

            for (PlaceableCard card : hand) {
                CLICommand.setPosition(13 + 23 * hand.indexOf(card), 33);
                System.out.println("[" + (hand.indexOf(card) + 1) + "]");

                addCardToHand(card, hand.indexOf(card));
            }


            // Draw available objectives
            CLIBox.draw(118, 32, 40, 12, AnsiColor.WHITE);
            CLIBox.draw(118, 32, "Objectives", AnsiColor.WHITE);
            CLILine.drawVertical(138, 33, 44, AnsiColor.WHITE);

            CLICard.displayQuestCard(privateQuest, 121, 36);
            CLICard.displayQuestCard(publicQuests.get(0), 140, 33);
            CLICard.displayQuestCard(publicQuests.get(1), 140, 38);

            // Draw Starting Card
            CLIBox.draw(74, 32, 41, 12, AnsiColor.WHITE);
            CLIBox.draw(74, 32, "Starting Card", AnsiColor.WHITE);
            CLICard.displayStartingCard(startingCard, 84, 35);


            // User input region
            CLILine.drawHorizontal(2, 44, 158, AnsiColor.WHITE);

            CLICommand.setPosition(2, 47);

            moveToInputField();

            serverReply("You can flip your cards by typing 'flip [card id]', type 'place S' to start playing");
        }

        public static void flipStartingCard(StartingCard startingCard) {
            CLICard.displayStartingCard(startingCard, 84, 35);
            moveToInputField();
        }
    }

    public static class InterfaceSetup implements CLIState {
        /**
         * This state is used to set up the cli after the "special" configuration used in "StartingTurn".
         * This page must be applied before the "Default" state or to reset the interface
         *
         * @param args null
         */
        @Override
        public void apply(Object[] args) {
            // Clear starting card
            clearRegion(74, 32, 41, 12);

            // Draw resources board
            CLIBox.draw(74, 32, 20, 12, AnsiColor.WHITE);
            CLIBox.draw(74, 32, "Resources", AnsiColor.WHITE);

            // Draw ranking and points
            CLIBox.draw(95, 32, 20, 12, AnsiColor.WHITE);
            CLIBox.draw(95, 32, "Score board", AnsiColor.WHITE);
            updateScoreBoard();

            moveToInputField();
        }
    }

    /**
     * This state is used to display the game interface during normal match turns
     * It modifies the StartingTurn state by adding the resources table and the score board
     */
    public static class Default implements CLIState {
        /**
         * @param args player's matrix (args[0])
         */
        @Override
        public void apply(Object[] args) {
            // Clear board
            CLIBox.draw(2, 2, 113, 30, AnsiColor.CYAN);
            CLIBox.draw(2, 2, "Player Board: " + (clientPlayer != null ? clientPlayer.getUsername() : "Unknown"),
                    AnsiColor.CYAN,
                    AnsiColor.WHITE,
                    AnsiFormat.BOLD);

            moveToInputField();
        }
    }

    public static class PickCard implements CLIState {
        /**
         * @param args PlaceableCard[6] - uncovered cards on table,
         *             GoldenDeck first card (to be flipped later)
         *             ResourceDeck first card (to be flipped later)
         *             last positions are null when decks are empty
         */
        @Override
        public void apply(Object[] args) {
            clearRegion(3, 5, 108, 26); //1, 2 ->golden, 3, 4-> res , 5 -> goldenDeck, 6 -> resourceDeck

            for (int i = 0; i < 4; i++) {
                CLICommand.setPosition(26 + 31 * (i % 2), 7 + 12 * (i / 2));
                AnsiString.print("[" + (i + 1) + "]", AnsiColor.WHITE, AnsiFormat.BOLD);
                if (args[i] != null)
                    CLICard.printPlaceableCard(
                            (PlaceableCard) args[i],
                            17 + 31 * (i % 2),
                            9 + 12 * (i / 2));
            }

            if (args[4] != null) {
                CLICommand.setPosition(88, 7);
                AnsiString.print("[" + "5" + "]", AnsiColor.WHITE, AnsiFormat.BOLD);
                ((GoldenCard) args[4]).setFlippedState();
                CLICard.printGoldenDeck((GoldenCard) args[4], 79, 9);
            }

            if (args[5] != null) {
                CLICommand.setPosition(88, 19);
                AnsiString.print("[" + "6" + "]", AnsiColor.WHITE, AnsiFormat.BOLD);
                ((ResourceCard) args[5]).setFlippedState();
                CLICard.printResourceDeck((ResourceCard) args[5], 79, 21);
            }

            moveToInputField();
        }
    }

    public static class Help implements CLIState {

        @Override
        public void apply(Object[] args) {

            clearRegion(2, 32, 71, 12);

            CLIBox.draw(2, 32, 71, 12, AnsiColor.WHITE);
            CLIBox.draw(2, 32, "Help", AnsiColor.WHITE);

            new CLIString(
                    """
                            1. <help> - prints out all possible commands
                            2. <flip> <hand card id> - flips requested card
                            3. <place> [id] [hand card's corner] [matrix card id]
                            4. <move> [col] [row] - moves the board focus area
                            5. <chat> <...> - sends a message to other players
                            6. <quit> - quits match
                            7. <home> resets the board position
                            press 'q' to quit help page
                            """,
                    AnsiColor.WHITE, AnsiFormat.DEFAULT, 3, 35
            ).print();
            Scanner in = new Scanner(System.in);
            String input;
            do {
                CLICommand.restoreCursorPosition();
                CLICommand.clearLineAfterCursor();
                input = in.nextLine();
            } while (!input.equalsIgnoreCase("q"));

            // Draw hand
            clearRegion(2, 32, 71, 12);
            CLIBox.draw(2, 32, 71, 12, AnsiColor.WHITE);
            CLIBox.draw(2, 32, "Hand", AnsiColor.WHITE);

            for (int i = 0; i < 3; i++) {
                CLICommand.setPosition(13 + 23 * i, 33);
                System.out.println("[" + (i + 1) + "]");

            }
        }
    }
    // -------------- UTIL ----------------- //

    private static void clearRegion(int col, int row, int width, int height) {
        CLICommand.saveCursorPosition();

        for (int i = 0; i < height; i++) {
            CLICommand.setPosition(col, row + i);
            System.out.println(" ".repeat(width));
        }

        CLICommand.restoreCursorPosition();
    }

    public static void moveToInputField() {
        CLICommand.setPosition(2, 47);
        CLICommand.clearScreenAfterCursor();
        AnsiString.print(">> ", AnsiColor.CYAN, AnsiFormat.BOLD);
        CLICommand.saveCursorPosition();
    }

    // ----------- SERVER REPLY------------- //

    public static void serverReply(String message) {
        CLICommand.saveCursorPosition();

        CLICommand.setPosition(2, 45);
        CLICommand.clearLine();

        AnsiString.print(">> " + message, AnsiColor.CYAN, AnsiFormat.BOLD);

        CLICommand.restoreCursorPosition();
    }

    public static void deleteServerReply() {
        CLICommand.saveCursorPosition();

        CLICommand.setPosition(2, 45);
        CLICommand.clearLine();

        CLICommand.restoreCursorPosition();
    }

    // ---------------- HAND ---------------- //
    public static void displayHand(ArrayList<PlaceableCard> hand) {
        for (PlaceableCard card : hand)
            addCardToHand(card, hand.indexOf(card));
    }

    private static void addCardToHand(@NotNull PlaceableCard card, int inHandPosition) {
        int col = handUpLeftCornersPosition[inHandPosition][0];
        int row = handUpLeftCornersPosition[inHandPosition][1];

        CLICard.printPlaceableCard(card, col, row);
        moveToInputField();
    }

    private static void removeCardFromHand(int inHandPosition) {
        clearRegion(
                handUpLeftCornersPosition[inHandPosition][0],
                handUpLeftCornersPosition[inHandPosition][1],
                handCardWidth,
                handCardHeight
        );
    }

    public static void flipCard(int inHandPosition, PlaceableCard card) {
        addCardToHand(card, inHandPosition);
    }

    // ------------ RESOURCES -------------- //

    /**
     * This method is used to update a resource counter on the resource table
     *
     * @param resources HashMap<Resource, Integer> containing the resources to be updated
     */
    public static void updateResourceCounter(HashMap<Resource, Integer> resources) {
        CLICommand.saveCursorPosition();

        clearRegion(75, 35, 17, 7);

        for (Resource resource : onBoardResourcesPositions.keySet()) {
            CLICommand.setPosition(onBoardResourcesPositions.get(resource)[0], onBoardResourcesPositions.get(resource)[1]);
            if (resource.getLetter() == null)
                AnsiString.print(AnsiSpecial.BLOCK.getCode() + ": ", resource.getColor());
            else
                AnsiString.print(resource.getLetter() + ": ", resource.getColor());

            System.out.println(resources.get(resource) == null ? 0 : resources.get(resource));
        }

        CLICommand.restoreCursorPosition();
    }

    // ------------ SCORE BOARD ---------------- //


    private static Player findPlayer(String username) {
        if(allPlayers == null) return null;
        return allPlayers.stream().filter(player -> player.getUsername().equals(username)).findAny().orElse(null);
    }

    private static void updateScoreBoard(){
        CLICommand.saveCursorPosition();

        allPlayers.sort(Comparator.comparingInt(Player::getPoints).reversed());  // Lambda for sorting players to make scoreboard
        clearRegion(97, 35, 17, 7);

        for (Player player : allPlayers) {
            CLICommand.setPosition(97, 35 + allPlayers.indexOf(player) * 2);
            System.out.print(allPlayers.indexOf(player) + 1 + "- ");
            new CLIString(
                    player.getUsername(),
                    player.getColor().getAnsi(),
                    AnsiFormat.BOLD,
                    100, 35 + (allPlayers.indexOf(player) * 2),
                    9
            ).print();

            System.out.print(": " + player.getPoints());
        }

        CLICommand.restoreCursorPosition();
    }

    public static void updatePlayerScore(String username, int points) {
        Objects.requireNonNull(findPlayer(username)).setPoints(points);
        updateScoreBoard();
    }

    // ---------------- CHAT ------------------- //
    public static void chatLog(@NotNull String sender, String message) {
        CLICommand.saveCursorPosition();

        Player senderPlayer;

        try {
            senderPlayer = allPlayers.stream().filter(p -> p.getUsername().equals(sender)).findFirst().orElse(new Player(0, "Server"));
        } catch (NullPointerException e) {
            senderPlayer = new Player(0, "Server");
        }

        if (senderPlayer.getUserHash() == 0) senderPlayer.setColor(Color.GREEN);
        messages.addLast(new CLIString[]{

                new CLIString(senderPlayer.getUsername() + (":"),
                        senderPlayer.getColor().getAnsi(),
                        AnsiFormat.BOLD,
                        currentChatPosition[0], currentChatPosition[1], maxMessageLength),

                new CLIString(message.split("\n")[0],
                        AnsiColor.WHITE,
                        AnsiFormat.DEFAULT,
                        currentChatPosition[0] + senderPlayer.getUsername().length() + 2,
                        currentChatPosition[1],
                        maxMessageLength - (senderPlayer.getUsername().length() + 2))}
        );

        if (messages.size() > maxChatLength) {
            messages.getFirst()[0].deleteString();
            messages.getFirst()[1].deleteString();

            messages.removeFirst();

            messages.forEach(m -> {
                m[0].reposition(m[0].getPosition()[0], m[0].getPosition()[1] - 1);
                m[1].reposition(m[1].getPosition()[0], m[1].getPosition()[1] - 1);
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


}