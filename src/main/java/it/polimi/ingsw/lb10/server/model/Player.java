package it.polimi.ingsw.lb10.server.model;

import it.polimi.ingsw.lb10.server.model.quest.Quest;
import it.polimi.ingsw.lb10.server.model.cards.Card;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    private String username;
    private Matrix matrix;
    private boolean inGame;
    private ArrayList<Card> hand= new ArrayList<>();
    private HashMap<Resource,Integer> OnMapResources= new HashMap<>();
    private Quest privateQuest;
    private int points;

    public Player(String username) {
        this.username = username;
        matrix=new Matrix(this);
        inGame=true;
        points=0;
    }


    public String getUsername() {
        return username;
    }
    public Matrix getMatrix() {
        return matrix;
    }

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
}
