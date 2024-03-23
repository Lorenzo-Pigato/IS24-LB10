package it.polimi.ingsw.lb10.server.model;

import it.polimi.ingsw.lb10.server.model.Quest.Quest;
import it.polimi.ingsw.lb10.server.model.cards.Card;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    private String username;
    private boolean inGame;
    private ArrayList<Card> hand= new ArrayList<>();
    private HashMap<Resource,Integer> OnMapresources= new HashMap<>();
    private Quest privateQuest;
    private int points;
    public Player(String username) {
        this.username = username;
        inGame=true;
        points=0;
    }

    public String getUsername() {
        return username;
    }
}
