package it.polimi.ingsw.lb10.server.model;

import it.polimi.ingsw.lb10.server.model.DrawType.DrawStrategy;
import it.polimi.ingsw.lb10.server.model.cards.Deck;
import it.polimi.ingsw.lb10.server.model.cards.PlaceableCard;
import it.polimi.ingsw.lb10.server.model.quest.Quest;
import it.polimi.ingsw.lb10.network.requests.Request;

import it.polimi.ingsw.lb10.util.Observable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * If the Decks create problem it's possible to change with 4 different decks as before
 */
public class MatchModel extends Observable<Request> {

    private final String id;
    private final List<Player> players = new ArrayList<>();
    private DrawStrategy drawStrategy;
    private final Deck resourceDeck= new Deck();
    private final Deck goldenDeck = new Deck();
    private final Deck questDeck = new Deck();

    private final List<Quest> commonQuests = new ArrayList<>();
    private final List<PlaceableCard> goldenUncovered = new ArrayList<>();
    private final List<PlaceableCard> resourceUncovered= new ArrayList<>();


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
    public PlaceableCard getResourceCardFromDeck(){
        return (PlaceableCard) getResourceDeck().drawCard();
    }

    /**
     * @return the golden card draw from the deck
     */
    public PlaceableCard getGoldenCardFromDeck(){
        return (PlaceableCard) getGoldenDeck().drawCard();
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

    public List<PlaceableCard> getResourceUncovered() {
        return resourceUncovered;
    }
    public List<PlaceableCard> getGoldenUncovered() {
        return goldenUncovered;
    }
    /**
     * @return the List of players
     */
    public List<Player> getPlayers() {
        return players;
    }
}
