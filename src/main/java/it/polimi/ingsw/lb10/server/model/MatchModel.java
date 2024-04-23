package it.polimi.ingsw.lb10.server.model;

import it.polimi.ingsw.lb10.server.controller.MatchController;
import it.polimi.ingsw.lb10.server.model.cards.Color;
import it.polimi.ingsw.lb10.server.model.cards.PlaceableCard;
import it.polimi.ingsw.lb10.server.model.cards.decks.GoldenDeck;
import it.polimi.ingsw.lb10.server.model.cards.decks.QuestDeck;
import it.polimi.ingsw.lb10.server.model.cards.decks.ResourceDeck;
import it.polimi.ingsw.lb10.server.model.quest.Quest;
import it.polimi.ingsw.lb10.network.requests.Request;
import it.polimi.ingsw.lb10.util.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * If the Decks create problem it's possible to change with 4 different decks as before
 */
public class MatchModel extends Observable<Request> {

    private int     numberOfPlayers;
    private         MatchController controller;
    private         String id;
    private final   List<Player> players = new ArrayList<>();
    private final   ResourceDeck resourceDeck= new ResourceDeck();
    private final   GoldenDeck goldenDeck = new GoldenDeck();
    private final   QuestDeck questDeck = new QuestDeck();

    private final   List<Quest> commonQuests = new ArrayList<>();
    private final   List<PlaceableCard> goldenUncovered = new ArrayList<>();
    private final   List<PlaceableCard> resourceUncovered= new ArrayList<>();


    public MatchModel(int numberOfPlayers, MatchController controller) {
        this.numberOfPlayers = numberOfPlayers;
        this.controller = controller;
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
    }

    /**
     * @param player player which will receive the deal
     */
    private void firstDeal(Player player){
        player.addCardOnHand(resourceDeck.drawCard());
        player.addCardOnHand(resourceDeck.drawCard());
        player.addCardOnHand(goldenDeck.drawCard());
        player.setPrivateQuests(questDeck.drawCard(), questDeck.drawCard());
    }

    /**
     * Add one resource card to the uncovered Resource cards
     */
    public void addOnTableResourceCard(int index){

    }


    /**
     * Add one golden card to the uncovered Golden cards
     */
    public void addGoldenCardOnTable(int index){

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

    public List<PlaceableCard> getResourceUncovered() {
        return resourceUncovered;
    }
    public List<PlaceableCard> getGoldenUncovered() {
        return goldenUncovered;
    }
    public List<Player> getPlayers() {
        return players;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }
}
