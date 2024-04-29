package it.polimi.ingsw.lb10.server.controller;
import it.polimi.ingsw.lb10.network.response.match.StartedMatchResponse;
import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.Server;
import it.polimi.ingsw.lb10.server.model.MatchModel;
import it.polimi.ingsw.lb10.server.model.Node;
import it.polimi.ingsw.lb10.server.model.Player;
import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.StartingCard;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;
import it.polimi.ingsw.lb10.server.model.cards.PlaceableCard;
import it.polimi.ingsw.lb10.server.model.quest.Quest;
import it.polimi.ingsw.lb10.server.model.quest.QuestCounter;
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
    private final BlockingQueue<MatchRequest> requests;
    private final ArrayList<RemoteView> remoteViews;
    private boolean started = false;
    private final ArrayList<Player> players;
    private final int numberOfPlayers;

    public MatchController(int numberOfPlayers) {
        //model = new MatchModel(numberOfPlayers, this);
        requests = new LinkedBlockingQueue<>();
        remoteViews = new ArrayList<>();
        players = new ArrayList<>();
        this.numberOfPlayers = numberOfPlayers;
    }

    public boolean isActive(){return active;}

    public int getId(){return id;}
    public boolean isStarted(){return started;}
    public void setActive(boolean status){this.active = status;}

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
     *  This method is called to insert a Card
     *  A boolean is returned to verify if the card is placeable
     *  --> At the beginning the algorithm checks if the card is flipped, with a consequent update of the state of the card.
     *      The card is placed inside the matrix if the activation cost is matched
     */
    public boolean insertCard(Player player, PlaceableCard card, int row, int column){

        if(!checkActivationCost(player,card))
            return false;

        player.getMatrix().setCard(card, row, column);

        return checkInsertion(player, card, row, column);
    }

    /**
     * @param player calls the method
     * @param card to add
     * @param row row
     * @param column column
     * The method that starts the Insertion rules
     * @return true, if the card passed all the verification rules,
     *  if the card passes the tests, at the end he is correctly positioned inside the matrix
     */
    public boolean checkInsertion(Player player,PlaceableCard card,int row, int column){

        ArrayList<Node> visitedNodes = new ArrayList<>();

        if (verificationSetting(player, row, column, visitedNodes)) {
            setCardResourceOnPlayer(player, card);
            deleteCoveredResource(player, row, column);
            addCardPointsOnPlayer(player, card, visitedNodes);
            player.removeCardOnHand(card);//the player chooses the next card, it's a request!

            return true;
        }
        player.getMatrix().deleteCard(row,column);
            return  false;
    }

    /**
     * @param row and the column is the top left corner of the card
     * @return true, if the card passed all the requirements,
     * it's important to remember that the card is already inserted!
     */
    public boolean verificationSetting(Player player, int row, int column, ArrayList<Node> visitedNodes){
        //if one corner isn't available
        if(!checkNotAvailability(player,row,column))
            return false;

        Map<Position, int[]> setIncrement=player.getMatrix().parsingPositionCorners();
        int[] delta= new int[]{0,0};

        for(Position position: getPossiblePosition()){
        //turning to the starting position
            row-=delta[0]; column-=delta[1];
                delta = setIncrement.get(position);
            row+=delta[0]; column+=delta[1];

            //if in the matrix node there's only the corner of the card that I want to add, there's nothing to check

            if(player.getMatrix().getNode(row, column).getCorners().size()!=1){
                //Can't be more than 2 cards on a corner!
                if(player.getMatrix().getNode(row,column).getCorners().size()==3)
                    return false;
                // I added the node that I visited inside the arraylist, because it has 2 corners in the node
                visitedNodes.add(player.getMatrix().getNode(row,column));
                // If I visited more than 1 node with 2 corners
                if(visitedNodes.size()>1){
                    for(int x = 0; x< visitedNodes.size()-1; x++){
                        for(int y = x+1; y< visitedNodes.size(); y++){
                            if(visitedNodes.get(x).getCorners().getFirst().getId() == visitedNodes.get(y).getCorners().getFirst().getId())
                                return false;
                        }
                    }
                }
            }


        }
        //turning to the starting position
        row-=delta[0]; column-=delta[1];
        //if the card doesn't cover at least one card, it's an error
        return !visitedNodes.isEmpty();
    }

    public boolean checkActivationCost(Player player,PlaceableCard card){
        if(card.getStateCardActivationCost().isEmpty())
            return true;
        for (Map.Entry<Resource, Integer> entry : card.getStateCardActivationCost().entrySet())
            if(player.getResourceQuantity(entry.getKey()) < entry.getValue())
                return false;

        return true;
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

    /**
     *
     * @param card of which we will add the resources
     *             we are setting all the resources of the card inside the OnMapResources of the player
     */

    public void setCardResourceOnPlayer(Player player, StartingCard card){

        for(Corner corner : card.getStateCardCorners())
            player.addOnMapResources(corner.getResource());

        if(!card.getStateCardResources().isEmpty()){
            for(Resource resource : card.getStateCardResources())
                player.addOnMapResources(resource);
        }

    }

    public void setCardResourceOnPlayer(Player player, PlaceableCard card){
        for(Corner corner : card.getStateCardCorners()){
            player.addOnMapResources(corner.getResource());
        }
        player.addOnMapResources(card.getStateCardMiddleResource());
    }

    public void deleteCoveredResource(Player player,int row, int column){
        Map<Position, int[]> setIncrement = player.getMatrix().parsingPositionCorners();

        for(Position position: getPossiblePosition()){
            int[] delta = setIncrement.get(position);
            if (player.getMatrix().getNode(row + delta[0], column + delta[1]).getCorners().size()==2){
                player.deleteOnMapResources(player.getMatrix().getNode(row + delta[0], column + delta[1]).getCorners().getFirst().getResource());
                player.getMatrix().getNode(row + delta[0], column + delta[1]).getCorners().getFirst().setResource(Resource.NULL);
            }
        }
    }

    public void addCardPointsOnPlayer(Player player,PlaceableCard card, ArrayList<Node> visitedNodes) {
        Resource goldenResource=card.getStateCardGoldenBuffResource();
        if(goldenResource.equals(Resource.NULL))
            player.addPoints(card.getStateCardPoints());
        else if(goldenResource.equals(Resource.PATTERN))
            player.addPoints(card.getStateCardPoints() * visitedNodes.size());
        else if (goldenResource.equals(Resource.FEATHER) || goldenResource.equals(Resource.PERGAMENA) || goldenResource.equals(Resource.POTION) )
            player.addPoints(card.getStateCardPoints()*player.getResourceQuantity(goldenResource));
        else
            player.addPoints(card.getStateCardPoints());

        visitedNodes =new ArrayList<>();
    }

    /**
     * @param player who has the turn
     *  This method is to call at the end of the game!
     */
    public void checkCounterQuestPoints(Player player){
        for(Quest quest : model.getCommonQuests()){
            if(quest instanceof QuestCounter)
                player.addQuestPoints(((QuestCounter) quest).questAlgorithm(player.getOnMapResources()));
        }
    }


    public void addPlayer(Player player){
        players.add(player);
    }

    // --------> GETTER <--------
    public Position[] getPossiblePosition() {
        return new Position[]{Position.TOPLEFT, Position.TOPRIGHT, Position.BOTTOMRIGHT, Position.BOTTOMLEFT};
    }

    /** this method puts the request in the BlockingQueue object to be handled by the MatchController
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

    /** This method is used to handle the "JoinMatchRequest"
     *  sent by the client to the LobbyController, and from the LobbyController to the MatchController
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

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
