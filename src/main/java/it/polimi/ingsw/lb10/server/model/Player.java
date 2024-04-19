package it.polimi.ingsw.lb10.server.model;

import it.polimi.ingsw.lb10.server.model.quest.Quest;
import it.polimi.ingsw.lb10.server.model.cards.Card;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    private int hashCode;
    private String username;
    private Matrix matrix;
    private boolean inGame;
    private ArrayList<Card> hand= new ArrayList<>();
    private HashMap<Resource,Integer> OnMapResources= new HashMap<>();
    private Quest privateQuest;
    private int points;

    public Player(int hashCode, String username) {
        this.hashCode = hashCode;
        this.username = username;
        matrix=new Matrix(this);
        inGame=true;
        points=0;
    }
    
            
    public Matrix getMatrix() {
        return matrix;
    }

    // --------> METHODS <--------

    public void addOnMapResources(Resource resource) {
        if(resource!=Resource.NULL) {
            if (OnMapResources.containsKey(resource)) {
                int currentQuantity = OnMapResources.get(resource);
                OnMapResources.put(resource, currentQuantity + 1);
            } else
                OnMapResources.put(resource, 1);
        }
    }

    public void deleteOnMapResources(Resource resource) {
        if (resource != Resource.NULL) {
            if (OnMapResources.containsKey(resource)) {
                int currentQuantity = OnMapResources.get(resource);
                OnMapResources.put(resource, currentQuantity - 1);
            } else
                System.out.println(">>> E r r o r <<<");
        }
    }

    public int getResourceQuantity(Resource resource) {
        return OnMapResources.getOrDefault(resource, 0);
    }

    public void addPoints(int point){
        int tempPoints=getPoints()+point;
        setPoints(tempPoints);
    }
    // --------> GETTER <--------
    public int getPoints() {
        return points;
    }

    // --------> SETTER <--------

    public void setPoints(int points) {
        this.points = points;
    }

    public int getUserHash() {
        return hashCode;
    }

    public String getUsername() { return username;}

}
