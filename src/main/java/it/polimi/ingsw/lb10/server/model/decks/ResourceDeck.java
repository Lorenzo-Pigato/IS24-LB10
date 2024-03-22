package it.polimi.ingsw.lb10.server.model.decks;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import it.polimi.ingsw.lb10.server.model.cards.Card;
import it.polimi.ingsw.lb10.server.model.cards.Corner;
import it.polimi.ingsw.lb10.server.model.cards.ResourceCard;

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

    public void fillDeck() throws IOException {

//        corners.add(new Corner(true, false,Resource.FEATHER));
//        corners.add(new Corner(false, true,Resource.PLANT));
//        for(int i=0;i<3;i++){
//            cards.add(new ResourceCard(i, true, 1, Resource.ANIMAL));
//            cards.add(new ResourceCard(i, true, 1, corners,Resource.ANIMAL));
//        }

        ObjectMapper mapper = new ObjectMapper();
//        mapper.enable(SerializationFeature.INDENT_OUTPUT);
//        mapper.writeValue(new File("src/main/resources/it/polimi/ingsw/lb10/json", "try.json"), cards);
        try {
            cards = mapper.readValue(new File("src/main/resources/it/polimi/ingsw/lb10/json/resourceDeck.json"),new TypeReference<ArrayList<ResourceCard>>() {});
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String args[]) throws IOException {

        ResourceDeck rd = new ResourceDeck();
        rd.fillDeck();

    }
}