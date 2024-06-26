package it.polimi.ingsw.lb10.server.model;

import it.polimi.ingsw.lb10.server.model.cards.Color;
import it.polimi.ingsw.lb10.server.model.cards.PlaceableCard;
import it.polimi.ingsw.lb10.server.model.cards.StartingCard;
import it.polimi.ingsw.lb10.server.model.quest.Quest;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Player implements Serializable {
    @Serial
    private static final long serialVersionUID = 37L;


    private final int hashCode;
    private final String username;

    private boolean inMatch = false;
    private Matrix matrix;

    private final HashMap<Resource, Integer> onMapResources = new HashMap<>();
    private int points = 0;

    private Quest privateQuest;
    private final ArrayList<Quest> privateQuests = new ArrayList<>();

    private int questPoints = 0;
    private Color color;

    private final ArrayList<PlaceableCard> hand = new ArrayList<>();
    private StartingCard startingCard;
    private boolean ready = false;

    public Player(int hashCode, String username) {
        this.hashCode = hashCode;
        this.username = username;
    }

    // --------> METHODS <--------

    /**
     * @param resource to add to onMapResource
     */
    public void addOnMapResources(Resource resource) {
        if (resource != Resource.EMPTY && resource != Resource.NULL) {
            if (onMapResources.containsKey(resource)) {
                onMapResources.compute(resource, (k, currentQuantity) -> currentQuantity + 1);
            } else
                onMapResources.put(resource, 1);
        }
    }

    /**
     * @param resource to remove from the onMapResource of the player
     */
    public void deleteOnMapResources(Resource resource) {
        if (resource != Resource.EMPTY && resource != Resource.NULL) {
            if (onMapResources.containsKey(resource)) {
                onMapResources.compute(resource, (k, currentQuantity) -> currentQuantity - 1);
            }
        }
    }

    public void maxScore() {
        if (getPoints() > 29)
            setPoints(29);
    }

    public void addPoints(int point) {
        setPoints(point + getPoints());
        maxScore();
    }


    /**
     * @param questPoints to add
     */
    public void addQuestPoints(int questPoints) {
        setQuestPoints(questPoints + getQuestPoints());
    }

    public void addCardOnHand(PlaceableCard card) {
        hand.add(card);
    }

    public void removeCardOnHand(PlaceableCard cardToRemove) {
        getHand().remove(getHand().stream().filter(placeableCard -> placeableCard.getId() == cardToRemove.getId()).findFirst().orElse(null));
    }


    // --------> GETTER <--------

    public int getResourceQuantity(Resource resource) {
        return onMapResources.getOrDefault(resource, 0);
    }

    public HashMap<Resource, Integer> getOnMapResources() {
        return onMapResources;
    }

    public int getPoints() {
        return points;
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public int getQuestPoints() {
        return questPoints;
    }

    public ArrayList<Quest> getPrivateQuests() {
        return privateQuests;
    }

    public Quest getPrivateQuest() {
        return privateQuest;
    }

    public Color getColor() {
        return color;
    }

    public int getUserHash() {
        return hashCode;
    }

    public String getUsername() {
        return username;
    }

    public boolean isInMatch() {
        return inMatch;
    }

    public ArrayList<PlaceableCard> getHand() {
        return hand;
    }

    public StartingCard getStartingCard() {
        return startingCard;
    }

    public void setStartingCard(StartingCard startingCard) {
        this.startingCard = startingCard;
    }

    public void setFinalScore() {
        addPoints(questPoints);
    }


    // --------> SETTER <--------
    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setPrivateQuests(Quest privateQuest1, Quest privateQuest2) {
        privateQuests.add(privateQuest1);
        privateQuests.add(privateQuest2);
    }

    public void setQuestPoints(int questPoints) {
        this.questPoints = questPoints;
    }

    public void setPrivateQuest(Quest quest) {
        this.privateQuest = quest;
    }

    public void setInMatch(boolean status) {
        inMatch = status;
    }

    public boolean isReady() {return ready;}

    public void setReady(boolean status) {ready = status;}

    public void resetPlayer() {
        onMapResources.clear();
        points = 0;
        questPoints = 0;
        inMatch = false;
        privateQuests.clear();
        color = null;
        startingCard = null;
        ready = false;
        matrix.getMatrix().clear();
        hand.clear();
    }
}
