package it.polimi.ingsw.lb10.server.model;

import it.polimi.ingsw.lb10.server.model.quest.Quest;
import it.polimi.ingsw.lb10.server.model.cards.Card;
import it.polimi.ingsw.lb10.server.model.decks.Deck;
import it.polimi.ingsw.lb10.server.model.decks.GoldenDeck;
import it.polimi.ingsw.lb10.server.model.decks.QuestDeck;
import it.polimi.ingsw.lb10.server.model.decks.ResourceDeck;
import it.polimi.ingsw.lb10.network.requests.Request;

import it.polimi.ingsw.lb10.util.Observable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MatchModel extends Observable<Request> {

    private final String id;
    private final List<Player> players = new ArrayList<>();
    private final int numberOfPlayers;

    private final Deck resourceDeck= new ResourceDeck();
    private final Deck goldenDeck = new GoldenDeck();
    private final Deck questDeck = new QuestDeck();

    private final List<Quest> commonQuests = new ArrayList<>();
    private final List<Card> goldenUncovered = new ArrayList<>();
    private final List<Card> resourceUncovered= new ArrayList<>();


    public MatchModel(String id, int numberOfPlayers) throws IOException {
        this.id = id;
        this.numberOfPlayers = numberOfPlayers;
        initializeTable();
    }

    /**
     * Core of the start of the game
     */
    public void initializeTable() throws IOException {
        initializeDecks();
        initializeCardOnHand();
        startingUncoveredCards();
        initializeMatrix();

    }

    private void initializeDecks() throws IOException {
        resourceDeck.fillDeck();
        goldenDeck.fillDeck();
        questDeck.fillDeck();
    }

    private void initializeCardOnHand(){
        for( Player player : players) {
            addGoldenDeckCardOnHand(player);
            addResourceDeckCardOnHand(player);
            addResourceDeckCardOnHand(player);
        }
    }

    private void startingUncoveredCards(){
        for(int i=0;i<2;i++) {
            goldenUncovered.add(getGoldenCard());
            resourceUncovered.add(getResourceCard());
        }
// commonQuests.add(questDeck.json.draw());
// I need time to dev the Quest!
    }

    /**
     * init of the matrix
     */
    public void initializeMatrix(){
        for( Player player : players )
            player.setMatrix(new Matrix());
    }

    // --------> ADDER <--------
    /**
     * @param player wants to add a card from the resource uncovered  to the cards on hand
     */
    public void addResourceUncoveredCardOnHand (Player player ){
        player.addCardOnHand(getResourceUncovered());
    }
    /**
     * @param player wants to add a card from the golden uncovered to the cards on hand
     */
    public void addGoldenUncoveredCardOnHand (Player player){
        player.addCardOnHand(getGoldenUncovered());
    }
    /**
     * @param player wants to add a card from the golden deck to the cards on hand
     */
    public void addGoldenDeckCardOnHand (Player player) {
        player.addCardOnHand(getGoldenCard());
    }

    /**
     * @param player wants to add a card from the resource deck to the cards on hand
     */
    public void addResourceDeckCardOnHand (Player player) {
        player.addCardOnHand(getResourceCard());
    }

    /**
     * Add one resource card to the Resource uncovered
     */
    public void addResourceUncovered(){
        resourceUncovered.add(getResourceCard());
    }

    /**
     * Add one golden card to the Golden uncovered
     */
    public void addGoldenUncovered(){
        goldenUncovered.add(getGoldenCard());
    }

    // --------> GETTER <--------

    /**
     * @return uncovered card from the table
     */
    public Card getResourceUncovered() {
        Card temp=resourceUncovered.get(resourceUncovered.size()-1);
        resourceUncovered.remove(resourceUncovered.size()-1);
        addResourceUncovered();
        return temp;
    }

    /**
     * @return uncovered card from the table
     */
    public Card getGoldenUncovered() {
        Card temp=goldenUncovered.get(goldenUncovered.size()-1);
        goldenUncovered.remove(goldenUncovered.size()-1);
        addGoldenUncovered();
        return temp;
    }

    /**
     * @return the resource card draw from the deck
     */
    public Card getResourceCard(){
        return getResourceDeck().draw();
    }

    /**
     * @return the golden card draw from the deck
     */
    public Card getGoldenCard(){
        return getGoldenDeck().draw();
    }

    /**
     * @return the Resource Deck
     */
    public Deck getResourceDeck() {
        return resourceDeck;
    }
    /**
     * @return the Golden Deck
     */
    public Deck getGoldenDeck() {
        return goldenDeck;
    }
    /**
     * @return the List of players
     */
    public List<Player> getPlayers() {
        return players;
    }
}
