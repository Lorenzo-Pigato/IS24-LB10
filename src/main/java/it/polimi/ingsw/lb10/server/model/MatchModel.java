package it.polimi.ingsw.lb10.server.model;

import it.polimi.ingsw.lb10.network.response.match.*;
import it.polimi.ingsw.lb10.server.Server;
import it.polimi.ingsw.lb10.server.model.cards.*;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;
import it.polimi.ingsw.lb10.server.model.cards.decks.GoldenDeck;
import it.polimi.ingsw.lb10.server.model.cards.decks.QuestDeck;
import it.polimi.ingsw.lb10.server.model.cards.decks.ResourceDeck;
import it.polimi.ingsw.lb10.server.model.cards.decks.StartingDeck;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.Diagonal.TypeDiagonal;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.LJ.LJPattern;
import it.polimi.ingsw.lb10.server.model.quest.Quest;
import it.polimi.ingsw.lb10.server.model.quest.QuestCounter;
import it.polimi.ingsw.lb10.util.Observable;

import java.util.*;

public class MatchModel extends Observable {

    private final int numberOfPlayers;
    private int id;
    private final ArrayList<Player> players;
    private final ResourceDeck resourceDeck;
    private final GoldenDeck goldenDeck;
    private final QuestDeck questDeck;
    private final StartingDeck startingDeck;
    private Player onTurnPlayer;
    private boolean finalTurnStarted = false;
    private boolean finalTurnPlayed = false;
    private Player gameStarter;

    private boolean resourceDeckIsEmpty = false;
    private boolean goldenDeckIsEmpty = false;
    private boolean runOutOfCards = false;

    private final ArrayList<Quest> commonQuests = new ArrayList<>();

    private final ArrayList<GoldenCard> goldenUncovered = new ArrayList<>();
    private final ArrayList<ResourceCard> resourceUncovered = new ArrayList<>();

    private boolean terminated = false;


    public MatchModel(int numberOfPlayers, ArrayList<Player> players) {
        this.resourceDeck = new ResourceDeck();
        this.goldenDeck = new GoldenDeck();
        this.questDeck = new QuestDeck();
        this.startingDeck = new StartingDeck();
        this.players = players;
        this.numberOfPlayers = numberOfPlayers;
    }

    public ArrayList<GoldenCard> getGoldenUncovered() {return goldenUncovered;}

    public ArrayList<ResourceCard> getResourceUncovered() {return resourceUncovered;}

    public QuestDeck getQuestDeck() {return questDeck;}

    public StartingDeck getStartingDeck() {return startingDeck;}

    public ResourceDeck getResourceDeck() {return resourceDeck;}

    public GoldenDeck getGoldenDeck() {return goldenDeck;}

    public boolean isTerminated() {return terminated;}

    public boolean isFinalTurnStarted() { return finalTurnStarted;}

    public boolean hasRunOutOfCards() { return runOutOfCards;}

    /**
     * this method sets terminated state to true and sets all player in-match state to false.
     */
    public void terminate() {
        Server.log(">> [" + id + "] match terminated");
        terminated = true;
        players.forEach(p -> p.setInMatch(false));
    }


    /**
     * @return the player playing his turn.
     */
    public Player getOnTurnPlayer() {
        return onTurnPlayer;
    }

    /**
     * This method sets up the game by initializing both table status and players
     * Initializes decks [golden, resource, quest]
     * Initializes players [private quests to be chosen, pin color, first cards deal]
     */
    public void gameSetup() {

        initializeDecks();

        goldenUncovered.add(goldenDeck.drawCard());
        goldenUncovered.add(goldenDeck.drawCard());

        resourceUncovered.add(resourceDeck.drawCard());
        resourceUncovered.add(resourceDeck.drawCard());

        commonQuests.add(questDeck.drawCard());
        commonQuests.add(questDeck.drawCard());

        playersSetup();
    }

    /**
     * Initializes players [private quests to be chosen, pin color, first cards deal]
     */
    private void playersSetup() {
        ArrayList<Color> colors = new ArrayList<>();
        colors.add(Color.BLUE);
        colors.add(Color.GREEN);
        colors.add(Color.RED);
        colors.add(Color.PURPLE);
        Color pickedColor;

        for (Player p : players) {
            p.setMatrix(new Matrix());
            firstDeal(p);
            pickedColor = colors.getLast();
            colors.remove(pickedColor);
            p.setColor(pickedColor);
        }
        onTurnPlayer = null;
        notifyAll(new StartedMatchResponse(id));
    }

    /**
     * this method fills every game deck by loading json cards data and shuffles them.
     */
    public void initializeDecks() {

        resourceDeck.fillDeck();
        resourceDeck.shuffle();

        goldenDeck.fillDeck();
        goldenDeck.shuffle();

        questDeck.fillDeck();
        questDeck.shuffle();

        startingDeck.fillDeck();
        startingDeck.shuffle();
    }

    /**
     * @param player player which will receive the deal
     */
    private void firstDeal(Player player) {
        player.addCardOnHand(resourceDeck.drawCard());
        player.addCardOnHand(resourceDeck.drawCard());
        player.addCardOnHand(goldenDeck.drawCard());
        player.setPrivateQuests(questDeck.drawCard(), questDeck.drawCard());
        player.setStartingCard(startingDeck.drawCard());
    }

    /**
     * this method sets the player's private quest field to the given quest and sets the player state to READY, once
     * all players are ready, game will start.
     * @param player the player the private quest is assigned to.
     * @param quest the private quest assigned to the player.
     */
    public void assignPrivateQuest(Player player, Quest quest) {
        player.setPrivateQuest(quest);
        player.setReady(true);
        if (players.stream().allMatch(Player::isReady)) {
            players.forEach(p -> notify(new GameSetupResponse(players, commonQuests), p.getUserHash()));
            notifyDecksUpdate();
        }

    }

    /**
     * this method places the starting card in the center of the given player's matrix.
     * @param player the player that has placed his starting card.
     */
    public void insertStartingCard(Player player) {
        player.getMatrix().setCard(player.getStartingCard());
        setCardResourceOnPlayer(player, player.getStartingCard());
        notify(new PlaceStartingCardResponse(player.getStartingCard(), player.getOnMapResources()), player.getUserHash());
    }

    /**
     * @param userHash player userHash
     * @param points   player points
     *                 this method provides logic to end a player's turn by selecting next "onTurnPlayer", checking if given player has reached 20 points,
     *                 then checks if game has to end by checking if it equals to "finalTurnPlayer"
     */
    public void endTurn(int userHash, int points) {
        onTurnPlayer = players.get((players.indexOf(onTurnPlayer) + 1) % players.size());
        checkFinalTurn(getPlayer(userHash));
        checkEndGame();
        if(!terminated) {
            notifyDecksUpdate();
            notifyAll(new PlayerPointsUpdateResponse(getPlayer(userHash).getUsername(), points));
            notifyAll(new ChatMessageResponse("Server", "it's " + onTurnPlayer.getUsername() + "'s turn", false));
            notify(new ServerNotification("It's your turn, place your card!", true), onTurnPlayer.getUserHash());
        }else terminate();
    }

    /**
     * this method notifies all players when picking options get updated.
     * Logic is provided to handle cases when a picking option is not available due to deck emptiness.
     */
    private void notifyDecksUpdate() {

        PlaceableCard[] decks = new PlaceableCard[]{
                goldenDeck.getCards().isEmpty() ? null : goldenDeck.getCards().getLast(),
                resourceDeck.getCards().isEmpty() ? null : resourceDeck.getCards().getLast(),

                goldenUncovered.isEmpty() ? null : (goldenUncovered.size() > 1 ? goldenUncovered.getFirst() : null),
                goldenUncovered.isEmpty() ? null : (goldenUncovered.size() > 1 ? goldenUncovered.get(1) : goldenUncovered.getFirst()),

                resourceUncovered.isEmpty() ? null : (resourceUncovered.size() > 1 ? resourceUncovered.getFirst() : null),
                resourceUncovered.isEmpty() ? null : (resourceUncovered.size() > 1 ? resourceUncovered.get(1) : resourceUncovered.getFirst()),
        };
        notifyAll(new DeckUpdateResponse(Arrays.asList(decks)));
    }

    /**
     * @param p player to check on
     *          this method provides logic to check if a given player p has got to 20 or more points: in this case each one of the other players has one last turn left
     *          before game termination.
     */
    private void checkFinalTurn(Player p) {
        Server.log(">> [" + id + "] checking final turn");
        if (!finalTurnStarted && p.getPoints() >= 20) {
            finalTurnStarted = true;
            Server.log(">> [" + id + "] final turn started [" + p.getUsername() + "] reached 20 pts");
            notifyAll(new ChatMessageResponse("Server", "Final turn has started!", false));
        }
    }

    /**
     * this method provides logic to check if the game has turned to his end by checking if "onTurnPlayer" equals to the "finalTurnPlayer"
     * the adds all quest points to each player setting his final score, notifies each player of each other player final scores, and notifies game end.
     */
    private void checkEndGame() {
        if (finalTurnPlayed && onTurnPlayer.equals(gameStarter)) {
            players.forEach(this::checkCounterQuestPoints);
            players.forEach(Player::setFinalScore);
            players.forEach(player -> notifyAll(new PlayerPointsUpdateResponse(player.getUsername(), player.getPoints())));
            endGame();
        }

        if(finalTurnStarted && !finalTurnPlayed && onTurnPlayer.equals(gameStarter)){
            finalTurnPlayed = true;
            Server.log(">> [ " + id + "] final turn played");
        }
    }

    /**
     * this method id used to pick a golden card from golden deck, provides simple logic to avoid NoSuchElementException inside deck drawing:
     * once deck is empty, a little notification is sent to the client, so that its simple logic prevents client from requesting golden deck picking again.
     */
    public void drawResourceFromDeck(Player player) {
        if (!resourceDeckIsEmpty) {
            ResourceCard picked = resourceDeck.drawCard();
            player.addCardOnHand(picked);
            notify(new PickedCardResponse(picked, true, null, player.getMatrix(), false), player.getUserHash());
            checkDeckEmptiness();
            endTurn(player.getUserHash(), player.getPoints());
        } else {
            notify(new PickedCardResponse(null, false, "Resource deck is empty", null, false), player.getUserHash());
        }
    }

    /**
     * this method id used to pick a golden card from golden deck, provides simple logic to avoid NoSuchElementException inside deck drawing:
     * once deck is empty, a little notification is sent to the client, so that his simple logic prevents client from requesting golden deck picking again.
     */
    public void drawGoldenFromDeck(Player player) {
        if (!goldenDeckIsEmpty) {
            GoldenCard picked = goldenDeck.drawCard();
            player.addCardOnHand(picked);
            notify(new PickedCardResponse(picked, true, null, player.getMatrix(), false), player.getUserHash());
            checkDeckEmptiness();
            endTurn(player.getUserHash(), player.getPoints());
        } else {
            notify(new PickedCardResponse(null, false, "Golden deck is empty!", null, false), player.getUserHash());
        }
    }

    /**
     * this method is used to pick a card from table, there are always two cards, except when resource deck is empty :
     * in this case, client controller is notified, so that client can't even request to pick from resource deck.
     * If both decks are empty, match status changes to last turn.
     *
     * @param player player who requested to pick a resource card from table
     * @param index  <1 or 2>
     */
    public void drawResourceFromTable(Player player, int index) {
        ResourceCard picked;
        try {
            picked = resourceUncovered.get(index);
            resourceUncovered.remove(picked);
            if (!resourceDeckIsEmpty) {
                resourceUncovered.add(index, resourceDeck.drawCard());
            }

            player.addCardOnHand(picked);
            notify(new PickedCardResponse(picked, true, null, player.getMatrix(), false), player.getUserHash());
            checkDeckEmptiness();
            endTurn(player.getUserHash(), player.getPoints());

        } catch (IndexOutOfBoundsException e) {
            notify(new PickedCardResponse(null, false, "Table position not available!", null, false), player.getUserHash());
        }

    }

    /**
     * this method is used to pick a card from table, there are always two cards, except when golden deck is empty.
     * If both decks are empty, match status changes to last turn.
     *
     * @param player player who requested to pick a resource card from table
     * @param index  <1 or 2>
     */
    public void drawGoldenFromTable(Player player, int index) {
        GoldenCard picked;
        try {
            picked = goldenUncovered.get(index);
            goldenUncovered.remove(picked);
            if (!goldenDeckIsEmpty) {
                goldenUncovered.add(index, goldenDeck.drawCard());
            }

            player.addCardOnHand(picked);
            notify(new PickedCardResponse(picked, true, null, player.getMatrix(), false), player.getUserHash());
            checkDeckEmptiness();
            endTurn(player.getUserHash(), player.getPoints());

        } catch (IndexOutOfBoundsException e) {
            notify(new PickedCardResponse(null, false, "Table position not available", null, false), player.getUserHash());
        }
    }

    public void checkDeckEmptiness() {
        if (resourceDeck.getCards().isEmpty() && !resourceDeckIsEmpty) {
            resourceDeckIsEmpty = true;
        }

        if (goldenDeck.getCards().isEmpty() && !goldenDeckIsEmpty) {
            goldenDeckIsEmpty = true;
        }

        if (resourceDeckIsEmpty && goldenDeckIsEmpty) {
            finalTurnStarted = true;
            Server.log(">> [" + id + "] final turn started, both decks are empty!");
            notifyAll(new ChatMessageResponse("Server", "Final turn has started!", false));
        }

        if(resourceDeckIsEmpty && goldenDeckIsEmpty && resourceUncovered.isEmpty() && goldenUncovered.isEmpty()){
            runOutOfCards = true;
        }
    }

    /**
     * this method adds all resources belonging to a new placed PLACEABLE card to a player state.
     * @param player the player receiving the card resources.
     * @param card the card from which resources are obtained.
     */
    public void setCardResourceOnPlayer(Player player, PlaceableCard card) {
        for (Corner corner : card.getStateCardCorners()) {
            player.addOnMapResources(corner.getResource());
        }
        player.addOnMapResources(card.getStateCardMiddleResource());
    }

    /**
     * this method adds all resources belonging to a new placed PLACEABLE card to a player state.
     * @param player the player receiving the card resources.
     * @param card the card from which resources are obtained.
     */
    public void setCardResourceOnPlayer(Player player, StartingCard card) {

        for (Corner corner : card.getStateCardCorners())
            player.addOnMapResources(corner.getResource());

        if (!card.getStateCardResources().isEmpty()) {
            for (Resource resource : card.getStateCardResources())
                player.addOnMapResources(resource);
        }
    }

    /**
     * This method removes a player from the match once the match is terminated or the clients gets disconnected due to quitting or connection fault.
     * This method updates onTurnPlayer and notifies all other players.
     * @param player the player to be removed.
     */
    public void removePlayer(Player player) {

        notifyAll(new PlayerLeftResponse(player.getUsername()));
        notifyAll(new ChatMessageResponse("Server", player.getUsername() + " left", false));
        if(player.equals(onTurnPlayer)){
            onTurnPlayer = players.get((players.indexOf(onTurnPlayer) + 1) % players.size());
            notifyAll(new ChatMessageResponse("Server", "it's " + onTurnPlayer.getUsername() + "'s turn", false));
            notify(new ServerNotification("It's your turn, place your card!", true), onTurnPlayer.getUserHash());
        }
        if(player.equals(gameStarter)){
            gameStarter = players.get((players.indexOf(gameStarter) + 1) % players.size());
        }

        players.remove(player);
    }

    // --------> GETTER <--------
    /**
     * @return game's public quests
     */
    public List<Quest> getPublicQuests() {
        return commonQuests;
    }

    public List<Player> getPlayers() {
        return players;
    }

    /**
     * @param userHash the player's user hash.
     * @return the player assigned to the given hash code.
     */
    public Player getPlayer(int userHash) {
        try {
            return players.stream().filter(player -> player.getUserHash() == userHash).findFirst().orElseThrow(() -> new Exception(">>player not found in match model"));

        } catch (Exception e) {
            Server.displayError();
            Server.log(">> ERROR [" + id + "]" + e.getMessage());
            return null;
        }
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setId(int id) {
        this.id = id;
    }

    // --------> MODEL <-------- //

    /**
     * This method is called to insert a card,
     * A boolean is returned to verify if the card is placeable
     * --> At the beginning the algorithm checks if the card is flipped, with a consequent update of the state of the card.
     * the card is placed inside the matrix if the activation cost is matched, then the method checkInsertion is called
     * to verify if the card is placeable inside the matrix in the requested position
     */
    public synchronized void insertCard(Player player, PlaceableCard card, int row, int column) {
        boolean responseStatus = checkActivationCost(player, card);
        if (responseStatus) {
            player.getMatrix().setCard(card, row, column);
            //if one of the two-parameter row and column is > 82 return false!
            checkInsertion(player, card, row, column);
        } else notify(new PlaceCardResponse(card, false, row, column, null, "The card you chose has an activation cost, check your resources before placing!"), player.getUserHash());
    }

    /**
     * @param targetId the id of the card to check
     * @param player requesting to place the card
     * @return true is the card is inside the matrix
     */
    public boolean checkValidMatrixCardId(int targetId, Player player) {
        return player.getMatrix().getMatrix().stream().flatMap(Collection::stream)
                .flatMap(node -> node.getCorners().stream()).anyMatch(corner -> corner.getId() == targetId);
    }

    /**
     * @param player calls the method
     * @param card   to add
     * @param row    row
     * @param column column
     *               The method that starts the Insertion rules
     *               sends new PlaceCardResponse with status true if the card passes the tests, at the end he is correctly positioned inside the matrix
     *               else sends new PlaceCardResponse with status false
     */
    public synchronized void checkInsertion(Player player, PlaceableCard card, int row, int column) {
        ArrayList<Node> visitedNodes = new ArrayList<>();
        if (verificationSetting(player, row, column, visitedNodes)) {
            setCardResourceOnPlayer(player, card);
            deleteCoveredResource(player, row, column);
            addCardPointsOnPlayer(player, card, visitedNodes);
            checkPatternQuest(player, row, column);
            player.removeCardOnHand(card);//the player chooses the next card, it's a request!
            notify(new PlaceCardResponse(card, true, row, column, player.getOnMapResources(), ""), player.getUserHash());
        } else {
            player.getMatrix().deleteCard(row, column);
            notify(new PlaceCardResponse(card, false, row, column, null, "Invalid card placement, retry!"), player.getUserHash());
        }
    }


    /**
     * @param row and the column is the top left corner of the card
     * @return true if the card passed all the requirements
     * it's important to remember that the card is already inserted!
     */
    public synchronized boolean verificationSetting(Player player, int row, int column, ArrayList<Node> visitedNodes) {
        //if one corner isn't available
        if (!checkNotAvailability(player, row, column))
            return false;

        Map<Position, int[]> setIncrement = player.getMatrix().parsingPositionCorners();
        int[] delta = new int[]{0, 0};

        for (Position position : getPossiblePosition()) {
            //turning to the starting position
            row -= delta[0];
            column -= delta[1];
            delta = setIncrement.get(position);
            row += delta[0];
            column += delta[1];

            //if in the matrix node there's only the corner of the card that I want to add, there's nothing to check
            if (player.getMatrix().getNode(row, column).getCorners().size() != 1) {
                //Can't be more than 2 cards on a corner!
                if (player.getMatrix().getNode(row, column).getCorners().size() == 3)
                    return false;
                // I added the node that I visited inside the arraylist, because it has 2 corners in the node
                visitedNodes.add(player.getMatrix().getNode(row, column));
                // If I visited more than 1 node with 2 corners
                if (visitedNodes.size() > 1) {
                    for (int x = 0; x < visitedNodes.size() - 1; x++) {
                        for (int y = x + 1; y < visitedNodes.size(); y++) {
                            if (visitedNodes.get(x).getCorners().getFirst().getId() == visitedNodes.get(y).getCorners().getFirst().getId())
                                return false;
                        }
                    }
                }
            }
        }

        //if the card doesn't cover at least one card, it's an error
        return !visitedNodes.isEmpty();
    }

    /**
     * @param player adding the card to the matrix
     * @param card to add
     * @return false, if the player didn't have enough resources to place the card, true otherwise
     */
    public synchronized boolean checkActivationCost(Player player, PlaceableCard card) {
        if (card.getStateCardActivationCost().isEmpty())
            return true;
        for (Map.Entry<Resource, Integer> entry : card.getStateCardActivationCost().entrySet())
            if (player.getResourceQuantity(entry.getKey()) < entry.getValue()) return false;
        return true;
    }

    /**
     * @param player adding the card to the matrix
     * @param row of the top-left corner of the card
     * @param column of the top-left corner of the card
     * @return true if the corner is available, else false
     */
    public synchronized boolean checkNotAvailability(Player player, int row, int column) {
        Map<Position, int[]> setIncrement = player.getMatrix().parsingPositionCorners();

        for (Position position : getPossiblePosition()) {
            int[] delta = setIncrement.get(position);
            if (player.getMatrix().getNode(row + delta[0], column + delta[1]).checkIsNotAvailable())
                return false;
        }
        return true;
    }


    /**
     * @param player adding the card to the
     * @param row of the top-left corner of the card
     * @param column of the top-left corner of the card
     * This method remove the resources (corners) covered by the new card.
     */
    public synchronized void deleteCoveredResource(Player player, int row, int column) {
        Map<Position, int[]> setIncrement = player.getMatrix().parsingPositionCorners();

        for (Position position : getPossiblePosition()) {
            int[] delta = setIncrement.get(position);
            if (player.getMatrix().getNode(row + delta[0], column + delta[1]).getCorners().size() == 2)
                player.deleteOnMapResources(player.getMatrix().getNode(row + delta[0], column + delta[1]).getCorners().getFirst().getResource());
        }
    }


    /**
     * @param card         it's the card to add
     * @param visitedNodes are the nodes that have been visited in the current iteration.
     *                     This method adds the card's points in the counter of the player.
     *                     It controls all the different cases.
     **/
    public void addCardPointsOnPlayer(Player player, PlaceableCard card, ArrayList<Node> visitedNodes) {
        Resource goldenResource = card.getStateCardGoldenBuffResource();
        if (goldenResource.equals(Resource.PATTERN))
            player.addPoints(card.getStateCardPoints() * visitedNodes.size());
        else if (goldenResource.equals(Resource.FEATHER) || goldenResource.equals(Resource.PERGAMENA) || goldenResource.equals(Resource.POTION))
            player.addPoints(card.getStateCardPoints() * player.getResourceQuantity(goldenResource));
        else
            player.addPoints(card.getStateCardPoints());
    }


    private Position[] getPossiblePosition() {
        return new Position[]{Position.TOPLEFT, Position.TOPRIGHT, Position.BOTTOMRIGHT, Position.BOTTOMLEFT};
    }


    /**
     * this method starts game turns once every player has placed his starting card.
     * this method notifies all players of the first turn starting.
     */
    public void startTurns() {
        onTurnPlayer = players.getFirst();
        gameStarter = players.getFirst();
        notifyAll(new ChatMessageResponse("Server", "it's " + onTurnPlayer.getUsername() + "'s turn, make your move!", false));
    }


    /**
     * this method is called once the game has terminated due to game rules.
     * this method notifies all players and updates state to terminated.
     */
    private void endGame() {
        players.forEach(player -> notify(new EndGameResponse(player, players, true), player.getUserHash()));
        terminated = true;
    }


    /**
     * @param player who has the turn
     *               This method is called at the end of the game!
     */
    public void checkCounterQuestPoints(Player player) {
        ArrayList<Quest> quests = new ArrayList<>(getPublicQuests());
        quests.add(player.getPrivateQuest());
        for (Quest quest : quests) {
            if (quest instanceof QuestCounter)
                player.addQuestPoints((quest).questAlgorithm(player.getOnMapResources()));
        }
    }

    /**
     * @param row and the column is the top left corner of the card,
     *            This method is called each turn to avoid implementing an algorithm of research inside the matrix.
     *            If there's a pattern, it will be return true;
     *            the corners of the cards that will be useful for the patterns are marked as visited.
     *            After the verification of the pattern are added the points.
     */
    public void checkPatternQuest(Player player, int row, int column) {
        ArrayList<Quest> quests = new ArrayList<>(getPublicQuests());
        quests.add(player.getPrivateQuest());
        for (Quest quest : quests)
            if (quest instanceof TypeDiagonal || quest instanceof LJPattern)
                if (quest.isPattern(player.getMatrix(), row, column))
                    player.addQuestPoints(quest.getPoints());
    }

}
