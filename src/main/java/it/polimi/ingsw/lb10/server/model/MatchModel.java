package it.polimi.ingsw.lb10.server.model;

import it.polimi.ingsw.lb10.server.model.DrawType.DrawStrategy;
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
    private DrawStrategy drawStrategy;

    private final Deck resourceDeck= new ResourceDeck();
    private final Deck goldenDeck = new GoldenDeck();
    private final Deck questDeck = new QuestDeck();

    private final List<Quest> commonQuests = new ArrayList<>();
    private final List<Card> goldenUncovered = new ArrayList<>();
    private final List<Card> resourceUncovered= new ArrayList<>();


    public MatchModel(String id) throws IOException {
        this.id = id;
        drawStrategy=null;
    }

    /**
     * Core of the start of the game
     */
    public void initializeTable() throws IOException {
        initializeDecks();
        initializeCardsOnHand();
        startingUncoveredCards();
        initializeMatrix();
    }

    private void initializeDecks() throws IOException {
        resourceDeck.fillDeck();
        goldenDeck.fillDeck();
        questDeck.fillDeck();
    }

    private void initializeCardsOnHand(){
        for( Player player : players) {
            player.addCardOnHand(getResourceCardFromDeck());
            player.addCardOnHand(getResourceCardFromDeck());
            player.addCardOnHand(getGoldenCardFromDeck());
        }
    }

    private void startingUncoveredCards(){
        for(int i=0;i<2;i++) {
            goldenUncovered.add(getGoldenCardFromDeck());
            resourceUncovered.add(getResourceCardFromDeck());
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

    /**
     * Add one resource card to the Resource uncovered
     */
    public void addResourceUncovered(int index){
        resourceUncovered.add(index, getResourceCardFromDeck());
        drawStrategy.drawCard();
    }
    /**
     * Add one golden card to the Golden uncovered
     */
    public void addGoldenUncovered(int index){
        goldenUncovered.add(index, getGoldenCardFromDeck());
        drawStrategy.drawCard();
    }

    // --------> GETTER <--------

    public DrawStrategy getDrawState() {
        return drawStrategy;
    }

    /**
     * @return the resource card draw from the deck
     */
    public Card getResourceCardFromDeck(){
        return getResourceDeck().draw();
    }

    /**
     * @return the golden card draw from the deck
     */
    public Card getGoldenCardFromDeck(){
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

    public List<Card> getResourceUncovered() {
        return resourceUncovered;
    }
    public List<Card> getGoldenUncovered() {
        return goldenUncovered;
    }
    /**
     * @return the List of players
     */
    public List<Player> getPlayers() {
        return players;
    }
}
