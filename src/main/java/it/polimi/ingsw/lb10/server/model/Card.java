package it.polimi.ingsw.lb10.server.model;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public abstract class Card {

    public int id;

    public boolean flipped;

    public int points;
//        private ArrayList<Corner> corners = new ArrayList<Corner>();
//    public ArrayList<Corner> getCorner(){
//        return corners;
//    }
    public boolean isFlipped() {
        return flipped;
    }
    public int getPoints() {
        return points;
    }
    public int getId(){return id;}

    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
