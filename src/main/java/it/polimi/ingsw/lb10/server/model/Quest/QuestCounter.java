package it.polimi.ingsw.lb10.server.model.Quest;

import it.polimi.ingsw.lb10.server.model.Resource;

import java.util.HashMap;

/**
 * The Quest card which task it's to achieve points by the numbers of resource
 */
public class QuestCounter extends Quest{
    private HashMap<Resource,Integer> questResource= new HashMap<>();

    public QuestCounter(int id, int points){
        setId(id);
        setPoints(points);
    }

    public HashMap<Resource, Integer> getQuestResource() {
        return questResource;
    }
    public void setQuestResource(HashMap<Resource, Integer> questResource) {
        this.questResource = questResource;
    }
}
