package it.polimi.ingsw.lb10.server.model;

import it.polimi.ingsw.lb10.server.model.cards.Card;
import it.polimi.ingsw.lb10.server.model.cards.Corner;

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
        //I may create a method that if there's a determinate position, skip to the next
        getMatrixNode(41,41).addNode(card);
        getMatrixNode(41+1,41).addNode(card);
        getMatrixNode(41,41+1).addNode(card);
        getMatrixNode(41+1,41+1).addNode(card);
    }

    /**
     * @param card is the card to add
     * @param i is the row
     * @param j is the column
     * i and j are the top-left node.
     * I didn't use the corners of the cards because they aren't unique.
     */
    public void setCard(Card card,int i, int j){
       getMatrixNode(i,j).addNode(card);
       getMatrixNode(i+1,j).addNode(card);
       getMatrixNode(i,j+1).addNode(card);
       getMatrixNode(i+1,j+1).addNode(card);

//       setAvailabilityCorner(card,i--,j--);
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

//    /**
//     * @param card to add
//     * @param i row
//     * @param j column
//     */
//    public void setAvailabilityCorner(Card card, int i, int j){
//        for (Corner corner: card.getCorner() ){
//            if(corner.isLeft_Right() && corner.isUp_Down())//x,y
//                updatingCard(corner.getResource(),i,j);
//            if(!corner.isLeft_Right() && corner.isUp_Down())//x+1,y
//                updatingCard(corner.getResource(),i+1,j);
//            if(corner.isLeft_Right() && !corner.isUp_Down())//x,y+1
//                updatingCard(corner.getResource(),i,j+1);
//            if(!corner.isLeft_Right() && !corner.isUp_Down())//x+1,y+1
//                updatingCard(corner.getResource(),i+1,j+1);
//           }
//    }


    public void updatingCard(Resource resource, int i, int j){
        getMatrixNode(i,j).setAvailable(true);
        player.addOnMapResources(resource);
        //now I must delete form the player map the resource, I have to check the
        //node, id it has 2 of size I take the first and I delete the resource.
    }

    public Node getMatrixNode(int i, int j){
        return  matrix.get(i).get(j);
    }

}
