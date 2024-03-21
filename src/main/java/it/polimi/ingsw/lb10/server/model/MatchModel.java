package it.polimi.ingsw.lb10.server.model;

import it.polimi.ingsw.lb10.network.Request;
import it.polimi.ingsw.lb10.server.model.cards.Card;
import it.polimi.ingsw.lb10.server.model.decks.GoldenDeck;
import it.polimi.ingsw.lb10.server.model.decks.QuestDeck;
import it.polimi.ingsw.lb10.server.model.decks.ResourceDeck;
import it.polimi.ingsw.lb10.util.Observable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MatchModel extends Observable<Request>{

    private final String id;
    private List<Player> players = new ArrayList<>();
    private final int numberOfPlayers;
    private int turn;

    private final ResourceDeck resourceDeck= new ResourceDeck();
    private final GoldenDeck goldenDeck = new GoldenDeck();
    private final QuestDeck questDeck = new QuestDeck();

    private List<Quest> commonQuests = new ArrayList<>();
    private List<Card> goldenUncovered = new ArrayList<>();
    private List<Card> resourceUncovered= new ArrayList<>();


    public MatchModel(String id, int numberOfPlayers) throws IOException {
        this.id = id;
        this.numberOfPlayers = numberOfPlayers;
        initializeTable();
    }

    private void initializeDecks() throws IOException {
        resourceDeck.fillDeck();
        goldenDeck.fillDeck();
        questDeck.fillDeck();
    }

    public void initializeTable() throws IOException {
        initializeDecks();
        for(int i=0;i<2;i++)
            goldenUncovered.add(goldenDeck.draw());
        for(int i=0;i<2;i++)
            resourceUncovered.add(resourceDeck.draw());
//        for(int i=0;i<2;i++)
//            commonQuests.add(questDeck.draw());
        //I need time to dev the Quest!
    }


    @Override
    public void notify(Request request) {
        super.notify(request);
    }

}
