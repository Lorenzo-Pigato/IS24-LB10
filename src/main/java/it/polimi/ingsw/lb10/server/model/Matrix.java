package it.polimi.ingsw.lb10.server.model;

import it.polimi.ingsw.lb10.server.model.cards.Card;
import it.polimi.ingsw.lb10.server.model.cards.Corner;
import it.polimi.ingsw.lb10.server.model.cards.Position;

import java.util.ArrayList;

/**
 *
 */
public class Matrix {
    private ArrayList<ArrayList<Node>> matrix= new ArrayList<>();
    Player player;

    public Matrix(Player player){
        startingMatrix();
        this.player=player;
    }

    /**
     *    we work with the worst case, 83x83
     */
    public void startingMatrix(){
        for(int i=0;i<83;i++){
            matrix.add(new ArrayList<>());
            for(int j=0;j<83;j++){
                Node node = new Node();
                matrix.get(i).add(node);
            }
        }
    }

    /**
     * @param card is the starting card!
     */
    public void setCard(Card card){
        setCard(card,41,41);
    }

    /**
     * @param card to set inside the matrix
     * @param i row
     * @param j column
     *i and j are the top-left node.
     */
    public void setCard(Card card,int i, int j){
        for(Corner corner: card.getCorners() ){
           if(corner.getPosition().equals(Position.TOPLEFT))
               getMatrixNode(i,j).addCorner(corner);
           if(corner.getPosition().equals(Position.TOPRIGHT))
               getMatrixNode(i++,j).addCorner(corner);
           if(corner.getPosition().equals(Position.BOTTOMRIGHT))
               getMatrixNode(i++,j++).addCorner(corner);
           if(corner.getPosition().equals(Position.BOTTOMLEFT))
               getMatrixNode(i,j++).addCorner(corner);
        }
    }

    /**
     * @param i row
     * @param j column
     *          if the card it's not validated, it's removed
     */
    public void deleteCard(int i , int j){
        getMatrixNode(i,j).deleteLastCorner();
        getMatrixNode(i++,j).deleteLastCorner();
        getMatrixNode(i,j++).deleteLastCorner();
        getMatrixNode(i++,j++).deleteLastCorner();
    }

    /**
     * @param i row
     * @param j column
     * the problem with this method is that in the object node could be more
     * than 1 card, this is good for the fact that I can delete the covered resource
     * from the player's obj!
     */
    public void getCard(int i, int j){
        getMatrixNode(i,j);
        getMatrixNode(i+1,j);
        getMatrixNode(i,j+1);
        getMatrixNode(i+1,j+1);
    }

    public Node getMatrixNode(int i, int j){
        return  matrix.get(i).get(j);
    }

}
