package it.polimi.ingsw.lb10.server.controller;

import it.polimi.ingsw.lb10.network.response.match.StartedMatchResponse;
import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.Server;
import it.polimi.ingsw.lb10.server.model.MatchModel;
import it.polimi.ingsw.lb10.server.model.Node;
import it.polimi.ingsw.lb10.server.model.Player;
import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.Card;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;
import it.polimi.ingsw.lb10.server.view.RemoteView;
import java.io.IOException;
import it.polimi.ingsw.lb10.network.requests.match.MatchRequest;
import it.polimi.ingsw.lb10.network.requests.match.JoinMatchRequest;
import it.polimi.ingsw.lb10.network.response.match.JoinMatchResponse;
import it.polimi.ingsw.lb10.network.response.match.TerminatedMatchResponse;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.MatchRequestVisitor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Map;
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
    private int numberOfPlayers;

    public MatchController(int numberOfPlayers) {
        //model = new MatchModel(numberOfPlayers, this);
        requests = new LinkedBlockingQueue<>();
        remoteViews = new ArrayList<>();
        players = new ArrayList<>();
        this.numberOfPlayers = numberOfPlayers;
    }

    public boolean isActive(){return active;}

    public int getId(){return id;}
    public ArrayList<Player> getPlayers() {return players;}
    public boolean isStarted(){return started;}
    public void setActive(boolean status){this.active = status;}

    private Position[] possiblePosition;

//    public MatchController(MatchModel model){
//        this.model=model;
//        possiblePosition= new Position[]{Position.TOPLEFT, Position.TOPRIGHT, Position.BOTTOMRIGHT, Position.BOTTOMLEFT};
//    }

    //Game Model fields
    @Override
    public void run() {
        try{
            while(active){
                MatchRequest request = requests.take();
                request.accept(this);
            }
        }catch(InterruptedException e){
            //
        }
        finally {
            remoteViews.forEach(remoteView -> remoteView.send(new TerminatedMatchResponse()));
        }
    }

    /**
     *  WIP
     */
    public void initGame() throws IOException {
        model.initializeTable();
    }
    /**
     *      It's the method that the caller calls
     *  with the initialization of the card inside the matrix
     */
    public boolean insertCard(Player player,Card card,int row, int column){
        if(card.isFlipped())
            card.setFlippedState();
        else
            card.setNotFlippedState();

        player.getMatrix().setCard(card, row, column);

        if(!checkActivationCost(player,card))
            return false;

        return checkInsertion(player, card, row, column);
    }

    public boolean checkActivationCost(Player player,Card card){
        if(card.getStateCardActivationCost()==null)
            return true;
        for (Map.Entry<Resource, Integer> entry : card.getStateCardActivationCost().entrySet()) {
            if(player.getResourceQuantity(entry.getKey()) < entry.getValue())
                return false;
            }
        return true;
    }

    /**
     * @param player calls the method
     * @param card to add
     * @param row row
     * @param column column
     * The method that starts the Insertion rules
     * @return true if the card passed all the verification rules
     *  if the card passes the tests, at the end he is correctly positioned inside the matrix
     */
    public boolean checkInsertion(Player player,Card card,int row, int column){

        if (verificationSetting(player, row, column)) {
            setCardResourceOnPlayer(player, card);
            deleteCoveredResource(player, row, column);
            player.addPoints(card.getStateCardPoints());
            return true;
        }
        player.getMatrix().deleteCard(row,column);
            return  false;
    }


    /**
     * @param row and column are the top left corner of the card
     * @return true if the card passed all the requirements
     */
    public boolean verificationSetting(Player player,int row, int column){
        //if one corner isn't available
        if(checkNotAvailability(player,row,column))
            return false;

        ArrayList<Node> nodesVisited= new ArrayList<>();
        Map<Position, int[]> setIncrement=player.getMatrix().parsingPositionCorners();
        int[] delta= new int[]{0,0};

        for(Position position: getPossiblePosition()){
        //turning to the starting position
            row-=delta[0]; column-=delta[1];
                delta = setIncrement.get(position);
            row+=delta[0]; column+=delta[1];

            //if in the matrix node there's only the corner of the card that I want to add, there's nothing to check
            if(player.getMatrix().getNode(row, column).getCorners().size()==1){
                //Can't be more than 2 cards on a corner!
                if(player.getMatrix().getNode(row,column).getCorners().size()==3)
                    return false;
                // I added the node that I visited inside the arraylist, because it has 2 corners in the node
                nodesVisited.add(player.getMatrix().getNode(row,column));
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
        }
        //turning to the starting position
        row-=delta[0]; column-=delta[1];

        //if the card doesn't cover at least one card, it's an error
        return !nodesVisited.isEmpty();
    }

    public boolean checkNotAvailability(Player player,int row, int column){
        Map<Position, int[]> setIncrement=player.getMatrix().parsingPositionCorners();

        for(Position position: getPossiblePosition()){
            int[] delta = setIncrement.get(position);
            if (player.getMatrix().getNode(row + delta[0], column + delta[1]).checkIsNotAvailable())
                return false;
        }
        return true;
    }

    public void setCardResourceOnPlayer(Player player, Card card){
        for(Corner corner : card.getStateCardCorners()){
            player.addOnMapResources(corner.getResource());
        }
    }

    public void deleteCoveredResource(Player player,int row, int column){
        Map<Position, int[]> setIncrement = player.getMatrix().parsingPositionCorners();

        for(Position position: getPossiblePosition()){
            int[] delta = setIncrement.get(position);
            if (player.getMatrix().getNode(row + delta[0], column + delta[1]).getCorners().size()==2)
                player.deleteOnMapResources(player.getMatrix().getNode(row + delta[0], column + delta[1]).getCorners().get(0).getResource());
        }
    }

    // --------> GETTER <--------
    public MatchModel getModel() {
        return model;
    }

    public Position[] getPossiblePosition() {
        return possiblePosition;
    }

    /** this method puts the request in the BlockinQueue object to be handled by the MatchController
     * @param request request sent by the client, which is referred to the specific match.
     * @throws InterruptedException in case MatchController thread terminates.
     */
    public synchronized void submitRequest(MatchRequest request) throws InterruptedException{
        this.requests.put(request);
    }

    /**
     * @return match id
     */
    public synchronized int getMatchId(){
        return id;
    }

    /** this method is used to handle the "JoinMatchRequest" sent by the client to the LobbyController, and from the LobbyController to the MatchController
     * in this method MatchController adds the player to his players, sends positive response and checks if the match can start. In case the match can start, sends a
     * broadcast message to all the players waiting.
     * @param jmr join match request
     */
    @Override
    public synchronized void visit(@NotNull JoinMatchRequest jmr) {
        players.add(jmr.getPlayer());

        getRemoteView(jmr.getUserHash()).send(new JoinMatchResponse(true));
        Server.log(">> Added player to match: " + jmr.getPlayer().getUsername() + " - Match ID: " + id);
        if(players.size() == numberOfPlayers) start();
    }

    /** this method adds the remote view to the MatchController whenever a new client joins the match
     * @param remoteView the client remote view
     */
    public void addRemoteView(RemoteView remoteView){
        remoteViews.add(remoteView);
    }

    public RemoteView getRemoteView(int hashCode){
        return remoteViews.stream().filter(remoteView -> remoteView.getSocket().hashCode() == hashCode).findFirst().get();
    }

    /** this method removes a player and his remote view from the match in case the client sends a QuitRequest or disconnects from the socket, in this case the method
     * is triggered by an IOException in ClientConnection thread, who is the first thread to notice the disconnection, calling LobbyController static method *disconnectClient()*
     * which will handle removing the client from his match, in case the player is in a started match.
     * @param p the player to be removed
     */
    public void removePlayer(Player p){
        players.remove(p);
        try {
            getRemoteView(p.getUserHash()).getSocket().close();
        }catch(IOException e){
            throw new RuntimeException();
        }
        remoteViews.remove(getRemoteView(p.getUserHash()));
        //model. remove player!!!! ---------------------------------------------------
        Server.log(">> Player " + p.getUsername() + " removed from match " + getMatchId());
        if((players.isEmpty() && !isStarted()) || (players.size() == 1 && isStarted())){
            setActive(false);
            Server.log(">> Match " + getMatchId() + " terminated");
            //VINCITORE???????????????????????????????????????????????????????????????????????????????????????????????????????
        }
    }

    /**
     * this method sets started state to true whenever the match reaches the prefixed number of players, sends a broadcast message
     * to all clients in the starting match
     */
    private void start(){
        Server.log(" >> Match " +  id + " started");
        started = true;
        //model. ...
        broadcast(new StartedMatchResponse());
    }

    /** sends a specific response to all RemoteViews connected
     * @param response response to be sent
     */
    public void broadcast (Response response){
        remoteViews.forEach(remoteView -> remoteView.send(response));
    }

}
