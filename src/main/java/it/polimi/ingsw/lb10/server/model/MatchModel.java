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
import it.polimi.ingsw.lb10.server.model.quest.Quest;
import it.polimi.ingsw.lb10.server.model.quest.QuestCounter;
import it.polimi.ingsw.lb10.util.Observable;

import java.util.*;

public class MatchModel extends Observable{

    private final int numberOfPlayers;
    private int id;
    private final ArrayList<Player> players;
    private final ResourceDeck resourceDeck;
    private final GoldenDeck goldenDeck;
    private final QuestDeck questDeck;
    private final StartingDeck startingDeck;
    private Player onTurnPlayer;
    private boolean finalTurn = false;
    private Player finalTurnPlayer;

    private boolean resourceDeckIsEmpty = false;
    private boolean goldenDeckIsEmpty = false;

    private final   ArrayList<Quest> commonQuests = new ArrayList<>();

    private final   ArrayList<GoldenCard> goldenUncovered = new ArrayList<>();
    private final   ArrayList<ResourceCard> resourceUncovered= new ArrayList<>();



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
    public Player getOnTurnPlayer(){return onTurnPlayer;}

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
    private void playersSetup(){
        ArrayList<Color> colors = new ArrayList<>();
        colors.add(Color.BLUE);
        colors.add(Color.GREEN);
        colors.add(Color.RED);
        colors.add(Color.PURPLE);
        Color pickedColor;

        for(Player p : players){
            p.setMatrix(new Matrix());
            firstDeal(p);
            pickedColor = colors.getLast();
            colors.remove(pickedColor);
            p.setColor(pickedColor);
        }
        onTurnPlayer = players.get(0);
        notifyAll(new StartedMatchResponse(id));
    }


    /**
     * This method loads decks' json file and creates them by instantiating cards,
     * Shuffles decks then
     */
    private void initializeDecks() {
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
    private void firstDeal(Player player){
        player.addCardOnHand(resourceDeck.drawCard());
        player.addCardOnHand(resourceDeck.drawCard());
        player.addCardOnHand(goldenDeck.drawCard());
        player.setPrivateQuests(questDeck.drawCard(), questDeck.drawCard());
        player.setStartingCard(startingDeck.drawCard());
    }

    public void assignPrivateQuest(Player player, Quest quest){
        player.setPrivateQuest(quest);
        player.setInMatch(true);
        if(players.stream().allMatch(Player::isInMatch)){
            players.forEach(p-> notify(new GameSetupResponse(players, commonQuests),  p.getUserHash()));
        }
    }

    public void insertStartingCard(Player player){
        player.getMatrix().setCard(player.getStartingCard());
        setCardResourceOnPlayer(player, player.getStartingCard());
        notify(new PlaceStartingCardResponse(player.getStartingCard(), player.getOnMapResources(), player.getMatrix()), player.getUserHash());
    }

    private void endTurn(String username, int points){

        onTurnPlayer = players.get((players.indexOf(onTurnPlayer) + 1) % players.size());
        if(finalTurn && onTurnPlayer.equals(finalTurnPlayer)) endGame(players.stream().max(Comparator.comparingInt(Player::getPoints)));
        notifyAll(new PlayerPointsUpdateResponse(username, points));
        notifyAll(new ChatMessageResponse("Server", "it's " + onTurnPlayer.getUsername() + "'s turn, make your move !"));
    }

    /**
     * this method id used to pick a golden card from golden deck, provides simple logic to avoid NoSuchElementException inside deck drawing:
     * once deck is empty, a little notification is sent to the client, so that his simple logic prevents client from requesting golden deck picking again.
     * @return picked GOLDEN card
     */
    public void drawResourceFromDeck(Player player){
        if(!resourceDeckIsEmpty){
            ResourceCard picked = resourceDeck.drawCard();
            player.addCardOnHand(picked);
            notify(new PickedCardResponse(picked, true, null, player.getMatrix()), player.getUserHash());
            endTurn(player.getUsername(), player.getPoints());
            if(resourceDeck.getCards().isEmpty()){
                resourceDeckIsEmpty = true;
                if(resourceDeckIsEmpty && goldenDeckIsEmpty){
                    //notifyAll(LAST TURN);
                    //Could be done via chat very very cool!
                }
            }
        }else {
            notify(new PickedCardResponse(null, false, "Resource deck is empty!", null), player.getUserHash());
        }
    }

    /**
     * this method id used to pick a golden card from golden deck, provides simple logic to avoid NoSuchElementException inside deck drawing:
     * once deck is empty, a little notification is sent to the client, so that his simple logic prevents client from requesting golden deck picking again.
     * @return picked GOLDEN card
     */
    public void drawGoldenFromDeck(Player player){
        if(!goldenDeckIsEmpty){
            GoldenCard picked = goldenDeck.drawCard();
            player.addCardOnHand(picked);
            notify(new PickedCardResponse(picked, true, null, player.getMatrix()), player.getUserHash());
            endTurn(player.getUsername(), player.getPoints());
            checkDeckEmptiness();
        }else{
            notify(new PickedCardResponse(null, false, "Golden deck is empty!", null), player.getUserHash());
        }
    }
    /**this method is used to pick a card from table, there are always two cards, except when resource deck is empty :
     * in this case, client controller is notified, so that client can't even request to pick from resource deck.
     * If both decks are empty, match status changes to last turn.
     * @param player player who requested to pick a resource card from table
     * @param index <1 or 2>
     */
    public void drawResourceFromTable(Player player, int index){
        ResourceCard picked = null;

        try{
           picked = resourceUncovered.get(index);
           resourceUncovered.remove(picked);
           if(!resourceDeckIsEmpty){
               resourceUncovered.add(index, resourceDeck.drawCard());
           }
       }catch(NoSuchElementException e){
           notify(new PickedCardResponse(null, false, "Table position not avaliable!", null), player.getUserHash());
       }
        checkDeckEmptiness();
        player.addCardOnHand(picked);
        notify(new PickedCardResponse(picked, true, null, player.getMatrix()), player.getUserHash());
        endTurn(player.getUsername(), player.getPoints());
    }

    private void checkDeckEmptiness() {
        if(resourceDeck.getCards().isEmpty() && !resourceDeckIsEmpty) {
            resourceDeckIsEmpty = true;
            if (resourceDeckIsEmpty && goldenDeckIsEmpty) {
                finalTurn = true;
                finalTurnPlayer = onTurnPlayer;
            }
        }
    }

    /**this method is used to pick a card from table, there are always two cards, except when golden deck is empty :
     * in this case, client controller is notified, so that client can't even request to pick from golden deck.
     * If both decks are empty, match status changes to last turn.
     * @param player player who requested to pick a resource card from table
     * @param index <1 or 2>
     */
    public void drawGoldenFromTable(Player player, int index){
        GoldenCard picked = null;
        try{
            picked = goldenUncovered.get(index);
            resourceUncovered.remove(picked);
            if(!resourceDeckIsEmpty){
                resourceUncovered.add(index, resourceDeck.drawCard());
            }

        }catch(NoSuchElementException e){
            notify(new PickedCardResponse(null, false, "Table position not avaliable!", null), player.getUserHash());
        }
        checkDeckEmptiness();
        player.addCardOnHand(picked);
        notify(new PickedCardResponse(picked, true, null, player.getMatrix()), player.getUserHash());
        endTurn(player.getUsername(), player.getPoints());
    }

    public void setCardResourceOnPlayer(Player player, PlaceableCard card){
        for(Corner corner : card.getStateCardCorners()){
            player.addOnMapResources(corner.getResource());
        }
        player.addOnMapResources(card.getStateCardMiddleResource());
    }

    public void setCardResourceOnPlayer(Player player, StartingCard card){

        for(Corner corner : card.getStateCardCorners())
            player.addOnMapResources(corner.getResource());

        if(!card.getStateCardResources().isEmpty()){
            for(Resource resource : card.getStateCardResources())
                player.addOnMapResources(resource);
        }

    }

    public List<Quest> getCommonQuests() {
        return commonQuests;
    }

    /**
     * @return the Resource Deck
     */
    public ResourceDeck getResourceDeck() {
        return resourceDeck;
    }
    /**
     * @return the Golden Deck
     */
    public GoldenDeck getGoldenDeck() {
        return goldenDeck;
    }

    public void removePlayer(Player player){
        players.remove(player);
        notifyAll(new ChatMessageResponse("Server", "player" + player.getUserHash() + "left"));
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getPlayer(int userHash){
        try{
            return players.stream().filter(player -> player.getUserHash() == userHash).findFirst().orElseThrow(() -> new Exception(">>player not found in match model"));
        }catch(Exception e){
            Server.log(e.getMessage());
            return null;
        }
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setId(int id){
        this.id=id;
    }

    // --------> MODEL <-------- //
    /**
     *  This method is called to insert a Card
     *  A boolean is returned to verify if the card is placeable
     *  --> At the beginning the algorithm checks if the card is flipped, with a consequent update of the state of the card.
     *      the card is placed inside the matrix if the activation cost is matched
     */


    public synchronized void insertCard(Player player, PlaceableCard card, int row, int column){
        boolean responseStatus = checkActivationCost(player,card);
        if(responseStatus) {
            player.getMatrix().setCard(card, row, column);
            checkInsertion(player, card, row, column);

        }else notify(new PlaceCardResponse(card,false, row, column, null), player.getUserHash());
    }

    public boolean checkValidMatrixCardId(int targetId, Player player) {
        return player.getMatrix().getMatrix().stream().flatMap(col -> col.stream())
                .flatMap(node -> node.getCorners().stream()).anyMatch(corner -> corner.getId() == targetId);
    }

    /**
     * @param player calls the method
     * @param card to add
     * @param row row
     * @param column column
     * The method that starts the Insertion rules
     *  sends new PlaceCardResponse with status true if the card passes the tests, at the end he is correctly positioned inside the matrix
     *  else sends new PlaceCardResponse with status false
     */
    public synchronized void checkInsertion(Player player,PlaceableCard card,int row, int column){

        ArrayList<Node> visitedNodes = new ArrayList<>();

        if (verificationSetting(player, row, column, visitedNodes)) {
            setCardResourceOnPlayer(player, card);
            deleteCoveredResource(player, row, column);
            addCardPointsOnPlayer(player, card, visitedNodes);
            player.removeCardOnHand(card);//the player chooses the next card, it's a request!
            notify(new PlaceCardResponse(card, true, row, column, player.getOnMapResources()), player.getUserHash());
        }else{
            player.getMatrix().deleteCard(row,column);
            notify(new PlaceCardResponse(card, false, row, column, null), player.getUserHash());
        }

    }

    /**
     * @param row and column are the top left corner of the card
     * @return true if the card passed all the requirements
     * it's important to remember that the card is already inserted!
     */
    public synchronized  boolean verificationSetting(Player player, int row, int column, ArrayList<Node> visitedNodes){
        //if one corner isn't available
        if(!checkNotAvailability(player,row,column))
            return false;

        Map<Position, int[]> setIncrement=player.getMatrix().parsingPositionCorners();
        int[] delta= new int[]{0,0};

        for(Position position: getPossiblePosition()){
            //turning to the starting position
            row-=delta[0]; column-=delta[1];
            delta = setIncrement.get(position);
            row+=delta[0]; column+=delta[1];

            //if in the matrix node there's only the corner of the card that I want to add, there's nothing to check
            if(player.getMatrix().getNode(row, column).getCorners().size()!=1){
                //Can't be more than 2 cards on a corner!
                if(player.getMatrix().getNode(row,column).getCorners().size()==3)
                    return false;
                // I added the node that I visited inside the arraylist, because it has 2 corners in the node
                visitedNodes.add(player.getMatrix().getNode(row,column));
                // If I visited more than 1 node with 2 corners
                if(visitedNodes.size()>1){
                    for(int x = 0; x< visitedNodes.size()-1; x++){
                        for(int y = x+1; y< visitedNodes.size(); y++){
                            if(visitedNodes.get(x).getCorners().getFirst().getId() == visitedNodes.get(y).getCorners().getFirst().getId())
                                return false;
                        }
                    }
                }
            }
        }
        //if the card doesn't cover at least one card, it's an error
        return !visitedNodes.isEmpty();
    }

    public synchronized  boolean checkActivationCost(Player player,PlaceableCard card){
        if(card.getStateCardActivationCost().isEmpty())
            return true;
        for (Map.Entry<Resource, Integer> entry : card.getStateCardActivationCost().entrySet())
            if(player.getResourceQuantity(entry.getKey()) < entry.getValue())
                return false;

        return true;
    }

    public synchronized boolean checkNotAvailability(Player player,int row, int column){
        Map<Position, int[]> setIncrement=player.getMatrix().parsingPositionCorners();

        for(Position position: getPossiblePosition()){
            int[] delta = setIncrement.get(position);
            if (player.getMatrix().getNode(row + delta[0], column + delta[1]).checkIsNotAvailable())
                return false;
        }
        return true;
    }

    public synchronized void deleteCoveredResource(Player player,int row, int column){
        Map<Position, int[]> setIncrement = player.getMatrix().parsingPositionCorners();

        for(Position position: getPossiblePosition()){
            int[] delta = setIncrement.get(position);
            if (player.getMatrix().getNode(row + delta[0], column + delta[1]).getCorners().size()==2)
                player.deleteOnMapResources(player.getMatrix().getNode(row + delta[0], column + delta[1]).getCorners().getFirst().getResource());
        }
    }
    public synchronized void addCardPointsOnPlayer(Player player,PlaceableCard card, ArrayList<Node> visitedNodes) {
        Resource goldenResource=card.getStateCardGoldenBuffResource();
        if(goldenResource.equals(Resource.NULL))
            player.addPoints(card.getStateCardPoints());
        else if(goldenResource.equals(Resource.PATTERN))
            player.addPoints(card.getStateCardPoints() * visitedNodes.size());
        else if (goldenResource.equals(Resource.FEATHER) || goldenResource.equals(Resource.PERGAMENA) || goldenResource.equals(Resource.POTION) )
            player.addPoints(card.getStateCardPoints()*player.getResourceQuantity(goldenResource));
        else
            player.addPoints(card.getStateCardPoints());
        if(player.getPoints() >= 20 && finalTurn == false){
            finalTurn = true;
            finalTurnPlayer = onTurnPlayer;
        }
        notifyAll(new PlayerPointsUpdateResponse(player.getUsername(), player.getPoints()));
    }

    /**
     * @param player who has the turn
     *  This method is to call at the end of the game!
     */
    public synchronized void checkCounterQuestPoints(Player player){
        for(Quest quest : getCommonQuests()){
            if(quest instanceof QuestCounter)
                player.addQuestPoints(((QuestCounter) quest).questAlgorithm(player.getOnMapResources()));
        }
    }

    private Position[] getPossiblePosition() {
        return new Position[]{Position.TOPLEFT, Position.TOPRIGHT, Position.BOTTOMRIGHT, Position.BOTTOMLEFT};
    }


    public void startTurns() {
        onTurnPlayer = players.getFirst();
        notifyAll(new ChatMessageResponse("Server", "it's " + onTurnPlayer.getUsername() + "'s turn, make your move!"));
    }


    private void endGame(Optional<Player> max) {
        notifyAll(new EndGameResponse(max));
    }
}
