package it.polimi.ingsw.lb10.server.controller;

import it.polimi.ingsw.lb10.network.ClientConnection;
import it.polimi.ingsw.lb10.network.requests.match.MatchRequest;
import it.polimi.ingsw.lb10.network.requests.preMatch.JoinMatchRequest;
import it.polimi.ingsw.lb10.network.response.match.JoinMatchResponse;
import it.polimi.ingsw.lb10.network.response.match.TerminatedMatchResponse;
import it.polimi.ingsw.lb10.server.model.Player;
import it.polimi.ingsw.lb10.server.view.RemoteView;
import it.polimi.ingsw.lb10.server.model.MatchModel;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.MatchRequestVisitor;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

/* this class is the single match controller, every match has one
the run() method takes continuously the requests (pushed by ClientConnection(s)) from the BlockingQueue snd processes
them by entering the model and updating the view
this implementation goes over the Observer implementation to optimize thread synchronization
In fact, using an Observer implementation, this class wouldn't be a Runnable instance, and ClientConnection
would access this object with synchronized blocks to update the model and the view. Using this kind of implementation (BlockingQueue),
ClientConnection won't have to wait for the model and view to be updated, they can get continuous request that will be submitted
to this queue and executed inside this separated thread!*/

public class MatchController implements Runnable, MatchRequestVisitor {

    private Boolean active = true;
    private MatchModel model;
    private BlockingQueue<MatchRequest> requests;
    private ArrayList<RemoteView> remoteViews;
    private boolean started = false;
    private ArrayList<Player> players = new ArrayList<>();
    //Game Model fields

    @Override
    public void run() {
        try{
            while(active){
                MatchRequest request = requests.take();
                request.accept(this);
            }
        }catch(InterruptedException e){
            remoteViews.forEach(remoteView -> remoteView.send(new TerminatedMatchResponse()));
        }
    }

    public void submitRequest(MatchRequest request) throws InterruptedException{
        this.requests.put(request);
    }

    public int getMatchId(){
        return model.getId();
    }

    @Override
    public void visit(JoinMatchRequest jmr) {
        players.add(new Player(jmr.getHashCode(), jmr.getUsername()));
        getRemoteView(jmr.getHashCode()).send(new JoinMatchResponse(true));


    }

    public void addRemoteView(RemoteView remoteView){
        remoteViews.add(remoteView);
    }

    public RemoteView getRemoteView(int hashCode){
        return remoteViews.stream().filter(remoteView -> remoteView.getSocket().hashCode() == hashCode).findFirst().get();
    }
}
