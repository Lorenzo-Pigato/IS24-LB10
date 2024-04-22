package it.polimi.ingsw.lb10.server.model.quest;

import it.polimi.ingsw.lb10.server.model.Matrix;
import it.polimi.ingsw.lb10.server.model.Resource;

import java.util.Hashtable;
import java.util.Map;

/**
 * The Quest card which task it's to achieve points by the numbers of resources
 */
public class QuestCounter extends Quest{
    private Map<Resource,Integer> activationCost = new Hashtable<>();

    public QuestCounter(int id, int points,Map<Resource,Integer> activationCost) {
        super(id, points);
        this.activationCost=activationCost;
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
            if (activationCost.containsKey(resource)) {
                minimum=Math.min(minimum, value / activationCost.get(resource));
            }
        }
        return minimum*getPoints();
    }

    @Override
    public boolean isPattern(Matrix matrix, int row, int column) {
        return false;
    }
}
