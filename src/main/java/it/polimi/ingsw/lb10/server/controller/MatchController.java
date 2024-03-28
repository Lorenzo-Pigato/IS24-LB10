package it.polimi.ingsw.lb10.server.controller;

import it.polimi.ingsw.lb10.network.Request;
import it.polimi.ingsw.lb10.server.model.MatchModel;
import it.polimi.ingsw.lb10.server.model.Node;
import it.polimi.ingsw.lb10.server.model.Player;
import it.polimi.ingsw.lb10.server.model.cards.Card;
import it.polimi.ingsw.lb10.util.Observer;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

/*@ this class is the single match controller, every match has one
*@ the run() method takes continuously the requests (pushed by ClientConnection(s)) from the BlockingQueue snd processes
*@ them by entering the model and updating the view
*@ this implementation goes over the Observer implementation to optimize thread synchronization
*@ Infact, using an Observer implementation, this class wouldn't be a Runnable istance, and ClientConnection
*@ would access this object
*@ with synchronized blocks to update the model and the view. Using this kind of implementation (BlockingQueue),
*@ ClientConnection
*@ won't have to wait for the model and view to be updated, they can get continuous request that will be submitted
*@ to this queue and executed inside this separeted thread!*/

public class MatchController implements Runnable , Observer<Request> {

    private MatchModel model;
    private BlockingQueue<Request> requests;

    //Game Model fields

    @Override
    public void run() {
        while(true){

        }

    }


    /**
     * @param player calls the method, we need the reference for the matrix
     * @param card is to add
     * @param i row
     * @param j column
     *          --->Errors<---
     *     IN THIS CODE I DON'T CHECK THE AVAILABILITY OF THE CORNER THAT I WANT TO POSITION! FIX IT!
     * @return 1 if one corner isn't available
     * @return 2 if you covered more than one corner of a single card
     * @return 0 check if there are more than 2 cards in a corner
     * @return 3 check if at least one card it's covered
     *          --->Okay<---
     * @return -1 if there aren't errors
     */
    public int verificationSetting(Player player,Card card,int i, int j){
        if(!getModel().isPlayerIn(player))
            return 0;
        ArrayList<Node> nodesVisited= new ArrayList<>();
        for(int count=0;count<4;count++){
            if(!player.getMatrix().getMatrixNode(i, j).getCards().isEmpty()){
                if(player.getMatrix().getMatrixNode(i,j).getCards().size()==2)
                    return 0;
                if(!player.getMatrix().getMatrixNode(i,j).isAvailable())
                    return 1;
                nodesVisited.add(player.getMatrix().getMatrixNode(i,j));
                if(nodesVisited.size()>1){
                    for(int x=0;x<nodesVisited.size()-1;x++){
                        for(int y=x+1;y<nodesVisited.size();y++){
                            if(nodesVisited.get(x).getCards().get(0).getId() == nodesVisited.get(y).getCards().get(0).getId())
                                return 1;
                        }
                    }
                }
            }
            if(count==0) i++;
            if(count==1) j++;
            if(count==2) i--;
        }
        if(nodesVisited.isEmpty())
            return 3;
        j--;//get i and j to the starting position
        player.getMatrix().setCard(card,i,j);
        // I need in matrix one method instead of setCard with all the methods
        return -1;
    }

    public MatchModel getModel() {
        return model;
    }

    @Override
    public void update(Request request) {

    }

    public void process(Request m){

    }


}
