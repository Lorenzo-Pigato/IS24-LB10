package it.polimi.ingsw.lb10.server.controller;

import it.polimi.ingsw.lb10.network.requests.match.MatchRequest;
import it.polimi.ingsw.lb10.network.requests.match.JoinMatchRequest;
import it.polimi.ingsw.lb10.network.response.match.JoinMatchResponse;
import it.polimi.ingsw.lb10.network.response.match.TerminatedMatchResponse;
import it.polimi.ingsw.lb10.server.model.Player;
import it.polimi.ingsw.lb10.server.view.RemoteView;
import it.polimi.ingsw.lb10.server.model.MatchModel;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.MatchRequestVisitor;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/* this class is the single match controller, every match has one
the run() method takes continuously the requests (pushed by ClientConnection(s)) from the BlockingQueue snd processes
them by entering the model and updating the view
this implementation goes over the Observer implementation to optimize thread synchronization
In fact, using an Observer implementation, this class wouldn't be a Runnable instance, and ClientConnection
would access this object with synchronized blocks to update the model and the view. Using this kind of implementation (BlockingQueue),
ClientConnection won't have to wait for the model and view to be updated, they can get continuous request that will be submitted
to this queue and executed inside this separated thread!*/

public class MatchController implements Runnable, MatchRequestVisitor {

    private final int id = this.hashCode();
    private Boolean active = true;
    private MatchModel model;
    private BlockingQueue<MatchRequest> requests;
    private ArrayList<RemoteView> remoteViews;
    private boolean started = false;
    private ArrayList<Player> players;

    public MatchController(int numberOfPlayers) {
        model = new MatchModel(id, numberOfPlayers);
        requests = new LinkedBlockingQueue<>();
        remoteViews = new ArrayList<>();
        players = new ArrayList<>();
    }

    public int getId(){return id;}
    public boolean isStarted(){return started;}

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

    public synchronized void submitRequest(MatchRequest request) throws InterruptedException{
        this.requests.put(request);
    }

    public synchronized int getMatchId(){
        return model.getId();
    }

    /** this method is used to handle the "JoinMatchRequest" sent by the client to the LobbyController, and from the LobbyController to the MatchController
     * in this method MatchController adds the player to his players, sends positive response and checks if the match can start. In case the match can start, sends a
     * broadcast message to all the players waiting.
     * @param jmr join match request
     */
    @Override
    public void visit(JoinMatchRequest jmr) {
        players.add(jmr.getPlayer());
        getRemoteView(jmr.getHashCode()).send(new JoinMatchResponse(true));
        if(players.size() == model.getNumberOfPlayers()) start();
    }

    public void addRemoteView(RemoteView remoteView){
        remoteViews.add(remoteView);
    }

    public RemoteView getRemoteView(int hashCode){
        return remoteViews.stream().filter(remoteView -> remoteView.getSocket().hashCode() == hashCode).findFirst().get();
    }

    private void start(){
        started = true;
        //model. ????
    }

}
