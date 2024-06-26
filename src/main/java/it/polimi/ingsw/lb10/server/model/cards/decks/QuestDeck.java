package it.polimi.ingsw.lb10.server.model.cards.decks;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.lb10.server.model.cards.Color;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.Diagonal.BottomLeftDiagonal;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.Diagonal.TopLeftDiagonal;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.LJ.BottomLeft;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.LJ.BottomRight;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.LJ.TopLeft;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.LJ.TopRight;
import it.polimi.ingsw.lb10.server.model.quest.Quest;
import it.polimi.ingsw.lb10.server.model.quest.QuestCounter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

/**
 * Complete deck of QuestCard, it has 16 cards
 * The cards are taken from the json in resource.
 */
public class QuestDeck {

    private final ArrayList<Quest> cards;

    public QuestDeck() {
        cards = new ArrayList<>();
    }

    public void shuffle() {
        Collections.shuffle(getCards());
    }

    public Quest drawCard() throws NoSuchElementException {
        Quest card = cards.getLast();
        cards.removeLast();
        return card;
    }

    public ArrayList<Quest> getCards() {
        return cards;
    }


    public void fillDeck() {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<QuestCounter> counterQuest = new ArrayList<>();
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("counterQuestDeck.json");
        try {
            counterQuest = objectMapper.readValue(inputStream, new TypeReference<ArrayList<QuestCounter>>() {
            });
        } catch (Exception e) {
        }
        cards.addAll(counterQuest);
        cards.add(new BottomLeftDiagonal(95, 2, Color.RED));
        cards.add(new BottomLeftDiagonal(96, 2, Color.BLUE));
        cards.add(new TopLeftDiagonal(97, 2, Color.GREEN));
        cards.add(new TopLeftDiagonal(98, 2, Color.PURPLE));

        cards.add(new TopRight(99, 3, Color.BLUE, Color.RED));
        cards.add(new TopLeft(100, 3, Color.PURPLE, Color.BLUE));
        cards.add(new BottomRight(101, 3, Color.RED, Color.GREEN));
        cards.add(new BottomLeft(102, 3, Color.GREEN, Color.PURPLE));
    }

}
