package it.polimi.ingsw.lb10.server.model.quest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.lb10.server.model.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * The Quest card which task it's to achieve points by the numbers of resource
 */
public class QuestCounter extends Quest{

    private Map<Resource,Integer> activationCost;

@JsonCreator
    public QuestCounter(@JsonProperty("id") int id, @JsonProperty("points") int points, @JsonProperty("activationCost") Map<Resource,Integer> activationCost) {
        super(id, points);
        this.activationCost = activationCost;
    }

    public Map<Resource, Integer> getActivationCost() {
        return activationCost;
    }

    public void setActivationCost( Map<Resource, Integer> activationCost) {
        this.activationCost = activationCost;
    }
}
