package it.polimi.ingsw.lb10.server.model.decks;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import it.polimi.ingsw.lb10.server.model.cards.*;

import it.polimi.ingsw.lb10.server.model.Resource;

import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResourceDeck implements Deck {

    ArrayList<ResourceCard> cards= new ArrayList<>();
    ArrayList<Corner> corners= new ArrayList<>();


    // --------> METODI <--------

    public void shuffle() {
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
     * This method'll call the json with the complete resource cards' deck
     */
    public void fillDeck() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        corners.add(new CornetNotAvailable(Position.TOPRIGHT));
        corners.add(new CornerAvailable(Position.TOPLEFT));

        for(int i=0;i<3;i++){
            cards.add(new ResourceCard(i, true, 1,corners, Resource.ANIMAL));
        }

        cards.get(0).setId(32);
        cards.get(0).getCorners().get(0).setId(cards.get(0).getId());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(new File("src/main/resources/it/polimi/ingsw/lb10/json", "questDeck.json"), cards);
        
//        try {
//            cards = mapper.readValue(new File("src/main/resources/it/polimi/ingsw/lb10/json/resourceDeck.json"),new TypeReference<ArrayList<ResourceCard>>() {});
//        } catch (Exception e) {
//            System.out.println(e);
//        }

    }

    public static void main(String args[]) throws IOException {

        ResourceDeck rd = new ResourceDeck();
        rd.fillDeck();
    }
}


