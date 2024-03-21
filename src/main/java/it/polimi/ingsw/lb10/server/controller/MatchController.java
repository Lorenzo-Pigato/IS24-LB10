package it.polimi.ingsw.lb10.server.controller;

import it.polimi.ingsw.lb10.network.requests.Request;
import it.polimi.ingsw.lb10.util.Observer;
import it.polimi.ingsw.lb10.server.model.MatchModel;

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

public class MatchController implements Runnable, Observer<Request> {

    private MatchModel model;
    private BlockingQueue<Request> requests;

    //Game Model fields

    @Override
    public void run() {
        while(true){

        }

    }

    @Override
    public void update(Request request) {

    }

    public void process(Request m){

    }
}
