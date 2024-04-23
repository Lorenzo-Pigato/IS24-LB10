package it.polimi.ingsw.lb10.server.model;

import it.polimi.ingsw.lb10.network.response.match.TerminatedMatchResponse;
import it.polimi.ingsw.lb10.server.controller.MatchController;
import it.polimi.ingsw.lb10.server.model.cards.GoldenCard;
import it.polimi.ingsw.lb10.server.model.cards.PlaceableCard;
import it.polimi.ingsw.lb10.server.model.cards.ResourceCard;
import it.polimi.ingsw.lb10.server.model.cards.decks.GoldenDeck;
import it.polimi.ingsw.lb10.server.model.cards.decks.QuestDeck;
import it.polimi.ingsw.lb10.server.model.cards.decks.ResourceDeck;
import it.polimi.ingsw.lb10.server.model.cards.decks.StartingDeck;
import it.polimi.ingsw.lb10.server.model.quest.Quest;
import it.polimi.ingsw.lb10.network.requests.Request;
import it.polimi.ingsw.lb10.util.Observable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MatchModel extends Observable<Request> {

    private int numberOfPlayers;
    private MatchController controller;
    private String id;
    private List<Player> players = new ArrayList<>();
    private final ResourceDeck resourceDeck= new ResourceDeck();
    private final GoldenDeck goldenDeck = new GoldenDeck();
    private final StartingDeck startingDeck = new StartingDeck();
    private final QuestDeck questDeck = new QuestDeck();

    private final ArrayList<Quest> commonQuests = new ArrayList<>();
    private final ArrayList<PlaceableCard> goldenUncovered = new ArrayList<>();
    private final ArrayList<PlaceableCard> resourceUncovered= new ArrayList<>();

    public MatchModel(int numberOfPlayers, MatchController controller) {
        this.numberOfPlayers = numberOfPlayers;
        this.controller = controller;
        initializeTable();
    }


    /**
     * Core of the game start, it defines everything except the matrix which is defined inside the Player's constructor
     */
    public void initializeTable() {
        //----- IMPORTANT ------ implement decks and table initialization
    }

    @Override
    public void notify(Request request) {
        super.notify(request);
    }

    public void initializeDecks() throws IOException{
        resourceDeck.fillDeck(); ;
        goldenDeck.fillDeck();
        questDeck.fillDeck();
        startingDeck.fillDeck();
    }

    public void initializeCardsOnHand(){
        for( Player player : players) {
            player.addCardOnHand(getResourceCardFromDeck());
            player.addCardOnHand(getResourceCardFromDeck());
            player.addCardOnHand(getGoldenCardFromDeck());
        }
    }

    public void startingUncoveredCards(){
        for(int i=0;i<2;i++) {
            goldenUncovered.add(getGoldenCardFromDeck());
            resourceUncovered.add(getResourceCardFromDeck());
            commonQuests.add(getQuestCardFromDeck());
        }
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
    public void addOnTableResourceCard(int index){

    }


    /**
     * Add one golden card to the Golden uncovered
     */
    public void addOnTableGoldenCard(int index){

    }

    public void setPlayers(List<Player> players){
        this.players=players;
    }
    // --------> GETTER <--------
    public PlaceableCard getResourceCardFromDeck(){
        return (ResourceCard) getResourceDeck().drawCard();
    }

    public PlaceableCard getGoldenCardFromDeck(){
        return (GoldenCard) getGoldenDeck().drawCard();
    }
    public Quest getQuestCardFromDeck(){
        return getQuestDeck().drawCard();
    }

    public List<Quest> getCommonQuests() {
        return commonQuests;
    }

    public StartingDeck getStartingDeck() {
        return startingDeck;
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

    public List<PlaceableCard> getResourceUncovered() {
        return resourceUncovered;
    }
    public List<PlaceableCard> getGoldenUncovered() {
        return goldenUncovered;
    }
    public QuestDeck getQuestDeck() {
        return questDeck;
    }
    public List<Player> getPlayers() {
        return players;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }
}
