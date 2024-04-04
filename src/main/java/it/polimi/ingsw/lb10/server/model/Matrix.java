package it.polimi.ingsw.lb10.server.model;

import it.polimi.ingsw.lb10.server.model.cards.Card;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Matrix {
    private final ArrayList<ArrayList<Node>> matrix= new ArrayList<>();
    Player player;

    public Matrix(Player player){
        startingMatrix();
        this.player=player;
    }

    /**
     *    we work with the worst case, 83x83
     */
    public void startingMatrix(){
        for(int row=0;row<83;row++){
            matrix.add(new ArrayList<>());
            for(int column=0;column<83;column++){
                Node node = new Node();
                matrix.get(row).add(node);
            }
        }
    }

    public Map<Position, int[]> parsingPositionCorners(){
            Map<Position, int[]> setIncrement = new HashMap<>();
            setIncrement.put(Position.TOPLEFT, new int[]{0, 0});
            setIncrement.put(Position.TOPRIGHT, new int[]{0, 1});
            setIncrement.put(Position.BOTTOMRIGHT, new int[]{1, 1});
            setIncrement.put(Position.BOTTOMLEFT, new int[]{1, 0});
        return setIncrement;
    }

    /**
     * @param card is the starting card!
     */
    public void setCard(Card card){
        setCard(card,41,41);
    }

    /**
     * @param card to set inside the matrix, it's not the staring
     *      i and j are the top-left node.
     */
    public void setCard(Card card,int row, int column){
        Map<Position, int[]> setIncrement = parsingPositionCorners();
        for (Corner corner : card.getStateCardCorners()) {
            int[] delta = setIncrement.get(corner.getPosition());
            getNode(row + delta[0], column + delta[1]).addCorner(corner);
        }
    }

    /**
     * @param row row
     * @param column column
     *          if the card it's not validated, it's removed
     */
    public void deleteCard(int row , int column){
        getNode(row,column).deleteLastCorner();
        getNode(row++,column).deleteLastCorner();
        getNode(row,column++).deleteLastCorner();
        getNode(row++,column++).deleteLastCorner();
    }


    public Node getNode(int row, int column){
        return  matrix.get(row).get(column);
    }

}
