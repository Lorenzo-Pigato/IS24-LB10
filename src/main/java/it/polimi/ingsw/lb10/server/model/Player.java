package it.polimi.ingsw.lb10.server.model;

import it.polimi.ingsw.lb10.server.model.cards.PlaceableCard;
import it.polimi.ingsw.lb10.server.model.quest.Quest;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    private String username;
    private Matrix matrix;
    private boolean inGame;
    private ArrayList<PlaceableCard> hand= new ArrayList<>();
    private HashMap<Resource,Integer> OnMapResources= new HashMap<>();
    private Quest privateQuest;
    private int points;

    public Player(String username) {
        this.username = username;
        inGame=true;
        points=0;
    }

    // --------> METHODS <--------

    public void addOnMapResources(Resource resource) {
        if(resource!=Resource.EMPTY && resource!=Resource.NULL) {
            if (OnMapResources.containsKey(resource)) {
                int currentQuantity = OnMapResources.get(resource);
                OnMapResources.put(resource, currentQuantity + 1);
            } else
                OnMapResources.put(resource, 1);
        }
    }

    public void deleteOnMapResources(Resource resource) {
        if (resource!=Resource.EMPTY && resource!=Resource.NULL) {
            if (OnMapResources.containsKey(resource)) {
                int currentQuantity = OnMapResources.get(resource);
                OnMapResources.put(resource, currentQuantity - 1);
            } else
                System.out.println(">>> E r r o r <<<");
        }
    }

    public void addPoints(int point){
        int tempPoints=getPoints()+point;
        setPoints(tempPoints);
    }

    public void addCardOnHand(PlaceableCard card){
        hand.add(card);
    }

    public void removeCardOnHand(PlaceableCard cardToRemove){
        getHand().remove(cardToRemove);
    }

    // --------> GETTER <--------
    public int getResourceQuantity(Resource resource) {
        return OnMapResources.getOrDefault(resource, 0);
    }

    public int getPoints() {
        return points;
    }
    public String getUsername() {
        return username;
    }
    public Matrix getMatrix() {
        return matrix;
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

}
