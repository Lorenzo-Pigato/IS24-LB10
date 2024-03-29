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
     * @return true if the card passed all the requirements
     */
    public boolean verificationSetting(Player player,Card card,int i, int j){
        //if the player isn't in the MatchModel players List
        if(!getModel().isPlayerIn(player))
            return false;
        //if one corner isn't available
        if(!checkAvailability(player,i,j))
            return false;

        ArrayList<Node> nodesVisited= new ArrayList<>();
        boolean visitedOneCard=false;

        for(int count=0;count<4;count++){
            //if in the matrix node there's only the corner of the card that I want to add, there's nothing to check
            if(player.getMatrix().getMatrixNode(i, j).getCorners().size()==1){
                //Can't be more than 2 cards on a corner!
                if(player.getMatrix().getMatrixNode(i,j).getCorners().size()==3)
                    return false;
                // I added the node that I visited inside the arraylist, because it has 2 corners in the node
                nodesVisited.add(player.getMatrix().getMatrixNode(i,j));
                // If I visited more than 1 node with 2 corners
                if(nodesVisited.size()>1){
                    for(int x=0;x<nodesVisited.size()-1;x++){
                        for(int y=x+1;y<nodesVisited.size();y++){
                            if(nodesVisited.get(x).getCorners().get(0).getId() == nodesVisited.get(y).getCorners().get(0).getId())
                                return false;
                        }
                    }
                }
            }
            if(count==0) i++;
            if(count==1) j++;
            if(count==2) i--;
        }
        //if the card doesn't cover at least one card, it's an error
        return !nodesVisited.isEmpty();
    }

    public boolean checkAvailability(Player player,int i, int j){
        if(player.getMatrix().getMatrixNode(i, j).checkAvailability())
           return false;
        if(player.getMatrix().getMatrixNode(i++, j).checkAvailability())
            return false;
        if(player.getMatrix().getMatrixNode(i, j++).checkAvailability())
            return false;
        if(player.getMatrix().getMatrixNode(i++, j++).checkAvailability())
            return false;
        return true;
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
