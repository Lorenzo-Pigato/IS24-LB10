package it.polimi.ingsw.lb10.server.model.quest;

import it.polimi.ingsw.lb10.server.model.Player;
import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.decks.QuestDeck;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

class QuestCounterTest {
    private static final QuestDeck questDeck = new QuestDeck();
    private static Quest counterQuest;
    private static Player player;
    private static final Map<Resource,Integer> activationCost = new HashMap<>();
    private static final Map<Resource,Integer> onMapResources= new HashMap<>();

    @BeforeAll
    static void setUp(){
        questDeck.fillDeck();
        counterQuest=questDeck.getCards().getFirst();
        player=new Player(123,"Tester");
    }

    @AfterEach
    void cleanUp(){
        player.getOnMapResources().clear();
    }

    @Test
    void algorithmResourcesNotInTheQuest() {
        counterQuest=questDeck.getCards().getFirst();
        onMapResources.put(Resource.MUSHROOM,10);
        for(int i=0;i<onMapResources.get(Resource.MUSHROOM);i++)
            player.addOnMapResources(Resource.MUSHROOM);
        Assertions.assertEquals(0, counterQuest.questAlgorithm(player.getOnMapResources()));
    }

    @Test
    void algorithmResourcesInQuest() {
        counterQuest=questDeck.getCards().getFirst();
        onMapResources.put(Resource.INSECT,10);
        for(int i=0;i<onMapResources.get(Resource.INSECT);i++)
            player.addOnMapResources(Resource.INSECT);
        Assertions.assertEquals(6, counterQuest.questAlgorithm(player.getOnMapResources()));
    }

    /**
     * test on card 94
     */
    @Test
    void algorithmsDifferentResources(){
        onMapResources.put(Resource.FEATHER,3);
        onMapResources.put(Resource.POTION,2);
        onMapResources.put(Resource.PERGAMENA,3);

        for(int i=0;i<onMapResources.get(Resource.FEATHER);i++)
            player.addOnMapResources(Resource.FEATHER);
        for(int i=0;i<onMapResources.get(Resource.POTION);i++)
            player.addOnMapResources(Resource.POTION);
        for(int i=0;i<onMapResources.get(Resource.PERGAMENA);i++)
            player.addOnMapResources(Resource.PERGAMENA);

        counterQuest=questDeck.getCards().get(7);
        Assertions.assertEquals(6, counterQuest.questAlgorithm(player.getOnMapResources()));
    }


}