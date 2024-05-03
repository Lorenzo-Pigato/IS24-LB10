package it.polimi.ingsw.lb10.server.model;

import it.polimi.ingsw.lb10.server.model.cards.Color;
import it.polimi.ingsw.lb10.server.model.cards.PlaceableCard;
import it.polimi.ingsw.lb10.server.model.cards.StartingCard;
import it.polimi.ingsw.lb10.server.model.quest.Quest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Player implements Serializable {

    private final int hashCode;
    private final String username;

    private boolean inMatch = false;
    private Matrix matrix;

    private final HashMap<Resource,Integer> onMapResources= new HashMap<>();
    private int points = 0;

    private Quest privateQuest;
    private final ArrayList<Quest> privateQuests = new ArrayList<>();

    private int questPoints = 0;
    private Color color;

    private final ArrayList<PlaceableCard> hand = new ArrayList<>();
    private StartingCard startingCard;

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
     *                    It's important to manage the fact that the max score is 29!!!!
     */
    public void addQuestPoints(int questPoints){
        setQuestPoints(questPoints+getQuestPoints());
    }

    public void addCardOnHand(PlaceableCard card){hand.add(card);}
    public void removeCardOnHand(PlaceableCard cardToRemove){getHand().remove(getHand().stream().filter(placeableCard -> placeableCard.getId() == cardToRemove.getId()).findFirst().orElse(null));}


    // --------> GETTER <--------

    public int getResourceQuantity(Resource resource) {return onMapResources.getOrDefault(resource, 0);}
    public HashMap<Resource, Integer> getOnMapResources() {return onMapResources;}
    public int getPoints() {return points;}
    public Matrix getMatrix() {return matrix;}
    public int getQuestPoints() {return questPoints;}
    public ArrayList<Quest> getPrivateQuests () {return privateQuests;}
    public Quest getPrivateQuest(){return privateQuest;}
    public Color getColor() {return color;}
    public int getUserHash() {return hashCode;}
    public String getUsername() { return username;}
    public boolean isInMatch() {return inMatch;}
    public ArrayList<PlaceableCard> getHand() {return hand;}
    public StartingCard getStartingCard(){return startingCard;}
    public void setStartingCard(StartingCard startingCard) {this.startingCard = startingCard;}


    // --------> SETTER <--------
    public void setMatrix(Matrix matrix) {this.matrix = matrix;}
    public void setPoints(int points) {this.points = points;}
    public void setColor(Color color) {this.color = color;}
    public void setPrivateQuests(Quest privateQuest1, Quest privateQuest2) {privateQuests.add(privateQuest1);privateQuests.add(privateQuest2);}
    public void setQuestPoints(int questPoints) {this.questPoints = questPoints;}
    public void setPrivateQuest(Quest quest){this.privateQuest = quest;}
    public void setInMatch(boolean status){inMatch = status;}

}
