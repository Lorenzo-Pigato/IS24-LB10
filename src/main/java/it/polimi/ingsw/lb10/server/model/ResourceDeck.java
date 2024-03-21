package it.polimi.ingsw.lb10.server.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.FileNotFoundException;
import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
public class ResourceDeck implements Deck {

    ArrayList<ResourceCard> cards= new ArrayList<>();
    ArrayList<Corner> corners= new ArrayList<>();


    public void shuffle() {
    }
    public void draw() {
    }
    ;
    public ArrayList<ResourceCard> getCards() {
        return cards;
    }

    public void fillCards() throws IOException {
        corners.add(new Corner(1,true,false));
        for(int i=0;i<10;i++){
           cards.add(new ResourceCard(i, true, 1, corners));
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        mapper.writeValue(new File("src/main/java/it/polimi/ingsw/lb10/server/model/utility", "DeckR.json"), cards);

    }


    public static void main(String args[]) throws IOException {

        ResourceDeck rd = new ResourceDeck();
        rd.fillCards();

    }
}