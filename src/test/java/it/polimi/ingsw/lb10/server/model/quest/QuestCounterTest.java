package it.polimi.ingsw.lb10.server.model.quest;

import it.polimi.ingsw.lb10.server.model.Player;
import it.polimi.ingsw.lb10.server.model.Resource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Hashtable;
import java.util.Map;

import static org.junit.Assert.*;

class QuestCounterTest {
private static Quest counterQuest;
private static Player player;
private static Map<Resource,Integer> activationCost = new Hashtable<>();
    private static Map<Resource,Integer> onMapResources= new Hashtable<>();
    @BeforeAll
    static void setUp(){
        activationCost.put(Resource.MUSHROOM,3);
        onMapResources.put(Resource.MUSHROOM,10);
        counterQuest = new QuestCounter(0,3,activationCost);
        player=new Player(0, "tony");
        for(int i=0;i<activationCost.get(Resource.MUSHROOM);i++)
            player.addOnMapResources(Resource.MUSHROOM);
    }

    @Test
    void algorithmResources_singleResource() {
        assertEquals(3,counterQuest.questAlgorithm(player.getOnMapResources()));
    }
    
    /*@Test
public void testAlgorithmResources_SingleResource() {
    MyClass myClassInstance = new MyClass();

    // Creazione di una mappa di risorse con un'unica risorsa
    Map<Resource, Integer> resourcesMap = new HashMap<>();
    resourcesMap.put(Resource.WOOD, 100);

    // Definizione dei costi di attivazione per questa unica risorsa
    Map<Resource, Integer> activationCost = new HashMap<>();
    activationCost.put(Resource.WOOD, 10);

    myClassInstance.setActivationCost(activationCost);

    int result = myClassInstance.algorithmResources(resourcesMap);

    // Dato che abbiamo solo una risorsa con costo 10, il risultato dovrebbe essere 100 / 10 * punti
    int expected = (100 / 10) * myClassInstance.getPoints();

    assertEquals(expected, result);
}

@Test
public void testAlgorithmResources_MultipleResources() {
    MyClass myClassInstance = new MyClass();

    // Creazione di una mappa di risorse con più risorse diverse
    Map<Resource, Integer> resourcesMap = new HashMap<>();
    resourcesMap.put(Resource.WOOD, 100);
    resourcesMap.put(Resource.STONE, 200);
    resourcesMap.put(Resource.GOLD, 300);

    // Definizione dei costi di attivazione per ogni risorsa
    Map<Resource, Integer> activationCost = new HashMap<>();
    activationCost.put(Resource.WOOD, 10);
    activationCost.put(Resource.STONE, 20);
    activationCost.put(Resource.GOLD, 30);

    myClassInstance.setActivationCost(activationCost);

    int result = myClassInstance.algorithmResources(resourcesMap);

    // Calcoliamo il risultato atteso separatamente per ogni risorsa
    int expectedWood = (100 / 10) * myClassInstance.getPoints();
    int expectedStone = (200 / 20) * myClassInstance.getPoints();
    int expectedGold = (300 / 30) * myClassInstance.getPoints();

    // Il risultato finale sarà la somma dei risultati attesi per ogni risorsa
    int expected = expectedWood + expectedStone + expectedGold;

    assertEquals(expected, result);
}
*/
}