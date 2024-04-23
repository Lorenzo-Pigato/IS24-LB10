package it.polimi.ingsw.lb10.server.model.quest;


import it.polimi.ingsw.lb10.server.model.Matrix;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.lb10.server.model.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * The Quest card which task it's to achieve points by the numbers of resources
 */
public class QuestCounter extends Quest{
    private Map<Resource,Integer> activationCost = new HashMap<>();


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

    /**
     * @param onMapResources are the resources of the player on the map
     * @return the points to add on the player counter
     * This algorithm works with the simple case of three same resources and with the case where are all different
     */
    public int questAlgorithm(Map<Resource, Integer> onMapResources) {
        int minimum=Integer.MAX_VALUE;
        for (Resource resource : onMapResources.keySet()) {

            int value=onMapResources.getOrDefault(resource, 0);
            if (activationCost.containsKey(resource) && activationCost.get(resource) != 0 ) {
                minimum=Math.min(minimum, value / activationCost.get(resource));
            }
        }
        if(minimum==Integer.MAX_VALUE)
            return 0;
        return minimum*getPoints();
    }

    @Override
    public boolean isPattern(Matrix matrix, int row, int column) {
        return false;
    }
}
