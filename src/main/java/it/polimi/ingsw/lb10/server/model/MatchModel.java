package it.polimi.ingsw.lb10.server.model;

import it.polimi.ingsw.lb10.network.response.match.TerminatedMatchResponse;
import it.polimi.ingsw.lb10.server.controller.MatchController;
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

    private List<Player> players = new ArrayList<>();
    private final int numberOfPlayers;
    private final MatchController controller;

    private final Deck resourceDeck= new ResourceDeck();
    private final Deck goldenDeck = new GoldenDeck();
    private final Deck questDeck = new QuestDeck();

    private final List<Quest> commonQuests = new ArrayList<>();
    private final List<Card> goldenUncovered = new ArrayList<>();
    private final List<Card> resourceUncovered= new ArrayList<>();


    public MatchModel(int numberOfPlayers, MatchController controller) {
        this.numberOfPlayers = numberOfPlayers;
        this.controller = controller;
        initializeTable();
    }


    /**
     * Core of the game start, it defines everything except the matrix which is defined inside the Player's constructor
     */
    public void initializeTable() {
        try {
            initializeDecks();
        } catch (IOException e) {
            controller.broadcast(new TerminatedMatchResponse());
        }
        startingUncoveredCards();
    }

    @Override
    public void notify(Request request) {
        super.notify(request);
    }

    private void initializeDecks() throws IOException{
        resourceDeck.fillDeck();
        goldenDeck.fillDeck();
        questDeck.fillDeck();
    }

    private void startingUncoveredCards(){
        for(int i=0;i<2;i++) {
            goldenUncovered.add(goldenDeck.draw());
            resourceUncovered.add(resourceDeck.draw());
        }
// commonQuests.add(questDeck.json.draw());
// I need time to dev the Quest!
    }

    public void addResourceUncovered(){
        resourceUncovered.add(resourceDeck.draw());
    }
    public void addGoldenUncovered(){
        goldenUncovered.add(goldenDeck.draw());
    }

    // --------> GETTER <--------
    public Card getResourceUncovered() {
        Card temp=resourceUncovered.get(resourceUncovered.size()-1);
        resourceUncovered.remove(resourceUncovered.size()-1);
        addResourceUncovered();
        return temp;
    }

    public Card getGoldenUncovered() {
        Card temp=goldenUncovered.get(goldenUncovered.size()-1);
        goldenUncovered.remove(goldenUncovered.size()-1);
        addGoldenUncovered();
        return temp;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }
}
