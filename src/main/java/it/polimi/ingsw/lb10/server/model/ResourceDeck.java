package it.polimi.ingsw.lb10.server.model;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.security.interfaces.RSAKey;
import java.util.ArrayList;
public class ResourceDeck implements Deck {

    ArrayList<ResourceCard> cards= new ArrayList<>();

    String text = "[{\"id\":1,\"flipped\":true,\"points\":3},{\"id\":2,\"flipped\":true,\"points\":3}]";

    public void shuffle() {
    }
    public void draw() {
    }
    ;
    public ArrayList<ResourceCard> getCards() {
        return cards;
    }

    public void fillCards() throws FileNotFoundException {
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<ResourceCard>>(){}.getType();
        ArrayList<ResourceCard> mazzo = gson.fromJson(text, ArrayList.class);

        for(ResourceCard card : mazzo){
            System.out.println(card.getId());
        }

    }


    public static void main(String args[]) throws FileNotFoundException {

        ResourceDeck rd = new ResourceDeck();
        rd.fillCard();

    }
}