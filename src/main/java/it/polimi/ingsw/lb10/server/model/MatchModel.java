package it.polimi.ingsw.lb10.server.model;

import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.network.response.match.*;
import it.polimi.ingsw.lb10.server.Server;
import it.polimi.ingsw.lb10.server.model.cards.Color;
import it.polimi.ingsw.lb10.server.model.cards.GoldenCard;
import it.polimi.ingsw.lb10.server.model.cards.ResourceCard;
import it.polimi.ingsw.lb10.server.model.cards.decks.GoldenDeck;
import it.polimi.ingsw.lb10.server.model.cards.decks.QuestDeck;
import it.polimi.ingsw.lb10.server.model.cards.decks.ResourceDeck;
import it.polimi.ingsw.lb10.server.model.cards.decks.StartingDeck;
import it.polimi.ingsw.lb10.server.model.quest.Quest;
import it.polimi.ingsw.lb10.util.Observable;

import java.util.ArrayList;
import java.util.List;

public class MatchModel extends Observable<Response> {

    private int numberOfPlayers;
    private int id;
    private ArrayList<Player> players;
    private ResourceDeck resourceDeck;
    private GoldenDeck goldenDeck;
    private QuestDeck questDeck;
    private StartingDeck startingDeck;
    private Player onTurnPlayer;

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
            players.forEach(p-> notify(new GameSetupResponse(p, commonQuests),  p.getUserHash()));
        }
    }

    private void endTurn(){
        onTurnPlayer = players.get((players.indexOf(onTurnPlayer) + 1) % players.size());
    }

    /**
     * this method id used to pick a golden card from golden deck, provides simple logic to avoid NoSuchElementException inside deck drawing:
     * once deck is empty, a little notification is sent to the client, so that his simple logic prevents client from requesting golden deck picking again.
     * @return picked GOLDEN card
     */
    public ResourceCard drawResourceFromDeck(){
        ResourceCard picked = resourceDeck.drawCard();
        if(resourceDeck.getCards().isEmpty()){
            resourceDeckIsEmpty = true;
            notifyAll(new UnavailableResourceDeckResponse());
            if(resourceDeckIsEmpty && goldenDeckIsEmpty){
                //notifyAll(LAST TURN);
                //Could be done via chat very very cool!
            }
        }
        return picked;
    }

    /**
     * this method id used to pick a golden card from golden deck, provides simple logic to avoid NoSuchElementException inside deck drawing:
     * once deck is empty, a little notification is sent to the client, so that his simple logic prevents client from requesting golden deck picking again.
     * @return picked GOLDEN card
     */
    public GoldenCard drawGoldenFromDeck(){
        GoldenCard picked = goldenDeck.drawCard();
        if(goldenDeck.getCards().isEmpty()){
            goldenDeckIsEmpty = true;
            notifyAll(new UnavailableGoldenDeckResponse());
            if(resourceDeckIsEmpty && goldenDeckIsEmpty){
                //notifyAll(LAST TURN);
                //Could be done via chat very very cool!
            }
        }
        return picked;
    }
    /**this method is used to pick a card from table, there are always two cards, except when resource deck is empty :
     * in this case, client controller is notified, so that client can't even request to pick from resource deck.
     * If both decks are empty, match status changes to last turn.
     * @param player player who requested to pick a resource card from table
     * @param index <1 or 2>
     */
    public void drawResourceFromTable(Player player, int index){

        ResourceCard picked = resourceUncovered.get(index); //don't worry bout nosuchelementexception here, because client won't be able to draw from table once table is empty
        resourceUncovered.remove(picked);

        resourceUncovered.add(index, resourceDeck.drawCard());

        if(resourceDeck.getCards().isEmpty() && !resourceDeckIsEmpty) {
            resourceDeckIsEmpty = true;
            notifyAll(new UnavailableResourceDeckResponse());
            if (resourceDeckIsEmpty && goldenDeckIsEmpty) {
                //LAST TURN !!
            }
        }
        notify(new PickedCardResponse(picked), player.getUserHash());
        //endTurn()???
    }

    /**this method is used to pick a card from table, there are always two cards, except when golden deck is empty :
     * in this case, client controller is notified, so that client can't even request to pick from golden deck.
     * If both decks are empty, match status changes to last turn.
     * @param player player who requested to pick a resource card from table
     * @param index <1 or 2>
     */
    public void drawGoldenFromTable(Player player, int index){

        GoldenCard picked = goldenUncovered.get(index); //don't worry bout nosuchelementexception here, because client won't be able to draw from table once table is empty
        goldenUncovered.remove(picked);

        goldenUncovered.add(index, goldenDeck.drawCard());

        if(goldenDeck.getCards().isEmpty() && !goldenDeckIsEmpty) {
            goldenDeckIsEmpty = true;
            notifyAll(new UnavailableGoldenDeckResponse());
            if (resourceDeckIsEmpty && goldenDeckIsEmpty) {
                //LAST TURN !!
            }
        }
        notify(new PickedCardResponse(picked), player.getUserHash());
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
        //nootify via chat ?? cool!
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
}
