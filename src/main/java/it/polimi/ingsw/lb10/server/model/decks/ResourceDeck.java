package it.polimi.ingsw.lb10.server.model.decks;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import it.polimi.ingsw.lb10.server.model.Player;
import it.polimi.ingsw.lb10.server.model.cards.*;

import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.corners.*;

import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ResourceDeck implements Deck {

    ArrayList<ResourceCard> cards= new ArrayList<>();
    ArrayList<Corner> corners= new ArrayList<>();


    // --------> METODI <--------

    public void shuffle() {
        Collections.shuffle(getCards());
    }

    public Card draw() {
        Card temp=cards.get(cards.size()-1);
        cards.remove(cards.size()-1);
        return temp;
    }
    public ArrayList<ResourceCard> getCards() {
        return cards;
    }

    /**
     * This method calls the json with the complete resource cards' deck
     */
    public void fillDeck() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        corners.add(new CornerAvailable(Position.TOPRIGHT,Resource.MUSHROOM));
        corners.add(new CornerAvailable(Position.TOPLEFT,Resource.NULL));

//        for(int i=0;i<3;i++){
//            cards.add(new ResourceCard(i, 1,corners, Color.BLUE));
//        }

        for(Card card : cards)
            for(Corner corner:card.getStateCardCorners())
                System.out.println(corner.getResource());

//        for(Card card : cards) {
//            card.setFlippedState();
//            for (Corner corner : card.getStateCardCorners())
//                System.out.println(corner.getResource());
//
//        }


//        mapper.enable(SerializationFeature.INDENT_OUTPUT);
//        mapper.writeValue(new File("src/main/resources/it/polimi/ingsw/lb10/json", "questDeck.json"), cards);
//
//        try {
//            cards = mapper.readValue(new File("src/main/resources/it/polimi/ingsw/lb10/json/questDeck.json"),new TypeReference<ArrayList<ResourceCard>>() {});
//        } catch (Exception e) {
//            System.out.println(e);
//        }

    }


    public static void main(String args[]) throws IOException {

        ResourceDeck rd = new ResourceDeck();
        rd.fillDeck();
    }
}


