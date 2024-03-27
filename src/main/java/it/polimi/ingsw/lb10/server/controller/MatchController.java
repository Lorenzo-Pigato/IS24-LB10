package it.polimi.ingsw.lb10.server.controller;

import it.polimi.ingsw.lb10.network.Request;
import it.polimi.ingsw.lb10.server.model.MatchModel;
import it.polimi.ingsw.lb10.server.model.Player;
import it.polimi.ingsw.lb10.server.model.cards.Card;
import it.polimi.ingsw.lb10.util.Observer;

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

    /** Rules to position the card inside the matrix
     * @return 0
     */
    public int availableCorner(Player player,Card card,int i, int j){

        for(int count=0;count<4;count++){
            // all the code that I wrote...
        }

        //get i and j to the starting position
        player.getMatrix().setCard(card,i,j);
        // I need in matrix one method instead of setCard with all the methods
        return -1;
    }


    @Override
    public void update(Request request) {

    }

    public void process(Request m){

    }
}
