package it.polimi.ingsw.lb10.server.model;

import it.polimi.ingsw.lb10.server.model.cards.PlaceableCard;
import it.polimi.ingsw.lb10.server.model.quest.Quest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class Player {

    private int hashCode;
    private String username;

    private Matrix matrix;
    private int points = 0;

    private Quest privateQuest;
    private int questPoints = 0;


    private final ArrayList<PlaceableCard> hand= new ArrayList<>();
    private final Map<Resource,Integer> onMapResources = new HashMap<>();

    public Player(int hashCode, String username) {
        this.hashCode = hashCode;
        this.username = username;
    }

    // --------> METHODS <--------

    public void addOnMapResources(Resource resource) {
        if(resource!=Resource.EMPTY && resource!=Resource.NULL) {
            if (onMapResources.containsKey(resource)) {
                onMapResources.compute(resource, (k, currentQuantity) -> currentQuantity + 1);
            } else
                onMapResources.put(resource, 1);
        }
    }

    public void deleteOnMapResources(Resource resource) {
        if (resource!=Resource.EMPTY && resource!=Resource.NULL) {
            if (onMapResources.containsKey(resource)) {
                onMapResources.compute(resource, (k, currentQuantity) -> currentQuantity - 1);
            } else {
                // Tira un errore
            }
        }
    }

    public void maxScore(){
        if(getPoints()>29)
            setPoints(29);
    }

    public void addPoints(int point){

        setPoints(point+getPoints());
        maxScore();
    }

    /**
     * @param questPoints to add,
     *                    It's important to manage the fact that the max score is 30!!!!
     */
    public void addQuestPoints(int questPoints){
        setQuestPoints(questPoints+getQuestPoints());
    }
    public void addCardOnHand(PlaceableCard card){
        hand.add(card);
    }
    public void removeCardOnHand(PlaceableCard cardToRemove){
        getHand().remove(cardToRemove);
    }

    // --------> GETTER <--------
    public int getResourceQuantity(Resource resource) {
        return onMapResources.getOrDefault(resource, 0);
    }
    public Map<Resource, Integer> getOnMapResources() {
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

    // --------> SETTER <--------
    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    public ArrayList<PlaceableCard> getHand() {
        return hand;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getUserHash() {
        return hashCode;
    }

    public String getUsername() { return username;}

    public void setQuestPoints(int questPoints) {
        this.questPoints = questPoints;
    }
}
