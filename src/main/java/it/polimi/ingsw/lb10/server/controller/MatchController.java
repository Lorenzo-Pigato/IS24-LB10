package it.polimi.ingsw.lb10.server.controller;
import it.polimi.ingsw.lb10.network.requests.match.*;
import it.polimi.ingsw.lb10.network.requests.match.DrawGoldenFromDeckRequest;
import it.polimi.ingsw.lb10.network.response.match.PrivateQuestsResponse;
import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.Server;
import it.polimi.ingsw.lb10.server.model.MatchModel;
import it.polimi.ingsw.lb10.server.model.Node;
import it.polimi.ingsw.lb10.server.model.Player;
import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;
import it.polimi.ingsw.lb10.server.model.cards.PlaceableCard;
import it.polimi.ingsw.lb10.server.model.quest.Quest;
import it.polimi.ingsw.lb10.server.model.quest.QuestCounter;
import it.polimi.ingsw.lb10.server.view.RemoteView;
import java.io.IOException;

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
    private boolean resourceDeckAvalaible = true;
    private boolean goldenDeckAvalaible = true;

    public MatchController(int numberOfPlayers) {
        requests = new LinkedBlockingQueue<>();
        remoteViews = new ArrayList<>();
        players = new ArrayList<>();
        this.numberOfPlayers = numberOfPlayers;
    }

    public boolean isActive(){return active;}

    public int getId(){return id;}
    public boolean isStarted(){return started;}
    public void setActive(boolean status){this.active = status;}


    @Override
    public void run() {
        try{
            while(isActive()){
                MatchRequest request = requests.take();
                request.accept(this);
            }
        }catch(Throwable e){
            Server.log(">> " + e.getMessage());
        }
        finally {
            remoteViews.forEach(remoteView -> remoteView.send(new TerminatedMatchResponse())); //either match model or match controller send this, better matchmodel via obs
        }
    }

    /**
     *  This method is called to insert a Card
     *  A boolean is returned to verify if the card is placeable
     *  --> At the beginning the algorithm checks if the card is flipped, with a consequent update of the state of the card.
     *      the card is placed inside the matrix if the activation cost is matched
     */
    public synchronized boolean insertCard(Player player, PlaceableCard card, int row, int column){
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
     * @return true if the card passed all the verification rules
     *  if the card passes the tests, at the end he is correctly positioned inside the matrix
     *  it's called all the
     */
    public synchronized boolean checkInsertion(Player player,PlaceableCard card,int row, int column){

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
     * @param row and column are the top left corner of the card
     * @return true if the card passed all the requirements
     * it's important to remember that the card is already inserted!
     */
    public synchronized  boolean verificationSetting(Player player, int row, int column, ArrayList<Node> visitedNodes){
        //if one corner isn't available
        if(checkNotAvailability(player,row,column))
            return false;

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

    public synchronized  boolean checkActivationCost(Player player,PlaceableCard card){
        if(card.getStateCardActivationCost().isEmpty())
            return true;
        for (Map.Entry<Resource, Integer> entry : card.getStateCardActivationCost().entrySet())
            if(player.getResourceQuantity(entry.getKey()) < entry.getValue())
                return false;

        return true;
    }

    public synchronized boolean checkNotAvailability(Player player,int row, int column){
        Map<Position, int[]> setIncrement=player.getMatrix().parsingPositionCorners();

        for(Position position: getPossiblePosition()){
            int[] delta = setIncrement.get(position);
            if (player.getMatrix().getNode(row + delta[0], column + delta[1]).checkIsNotAvailable())
                return false;
        }
        return true;
    }

    public synchronized void setCardResourceOnPlayer(Player player, PlaceableCard card){
        for(Corner corner : card.getStateCardCorners()){
            player.addOnMapResources(corner.getResource());
        }
        player.addOnMapResources(card.getStateCardMiddleResource());
    }

    public synchronized void deleteCoveredResource(Player player,int row, int column){
        Map<Position, int[]> setIncrement = player.getMatrix().parsingPositionCorners();

        for(Position position: getPossiblePosition()){
            int[] delta = setIncrement.get(position);
            if (player.getMatrix().getNode(row + delta[0], column + delta[1]).getCorners().size()==2)
                player.deleteOnMapResources(player.getMatrix().getNode(row + delta[0], column + delta[1]).getCorners().getFirst().getResource());
        }
    }
    public synchronized void addCardPointsOnPlayer(Player player,PlaceableCard card, ArrayList<Node> visitedNodes) {
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
    public synchronized void checkCounterQuestPoints(Player player){
        for(Quest quest : model.getCommonQuests()){
            if(quest instanceof QuestCounter)
                player.addQuestPoints(((QuestCounter) quest).questAlgorithm(player.getOnMapResources()));
        }
    }

    public synchronized void addPlayer(Player player){
        players.add(player);
    }

    private Position[] getPossiblePosition() {
        return new Position[]{Position.TOPLEFT, Position.TOPRIGHT, Position.BOTTOMRIGHT, Position.BOTTOMLEFT};
    }

    /** this method puts the request in the BlockingQueue object to be handled by the MatchController
     * @param request request sent by the client, which is referred to the specific match.
     * @throws InterruptedException in case MatchController thread terminates.
     */
    public synchronized void submitRequest(MatchRequest request) throws InterruptedException{
        requests.put(request);
    }

    /**
     * @return match id
     */
    public int getMatchId(){
        return id;
    }

    /** this method is used to handle the "JoinMatchRequest" sent by the client to the LobbyController, and from the LobbyController to the MatchController
     * in this method MatchController adds the player to his players, sends positive response and checks if the match can start. In case the match can start, sends a
     * broadcast message to all the players waiting.
     * @param jmr join match request
     */
    @Override
    public synchronized  void visit(@NotNull JoinMatchRequest jmr) {
        players.add(jmr.getPlayer()); //adds new player, safe because LobbyController checked if it's possible
        getRemoteView(jmr.getUserHash()).send(new JoinMatchResponse(true, getMatchId())); //sends response
        Server.log(">>match joined [username : " + getPlayer(jmr.getUserHash()).getUsername() + ", match : " + getMatchId());
        if(players.size() == numberOfPlayers) start();
    }

    /**
     * @param privateQuestsRequest request sent by the client to view and choose his private quests
     */
    @Override
    public synchronized  void visit(@NotNull PrivateQuestsRequest privateQuestsRequest) {
        try{
            Server.log(">>private quests request [username : " + getPlayer(privateQuestsRequest.getUserHash()).getUsername() + "]");
            getRemoteView(privateQuestsRequest.getUserHash()).send(new PrivateQuestsResponse(getPlayer(privateQuestsRequest.getUserHash()).getPrivateQuests()));
        }catch(Exception e){
            Server.log(e.getMessage());
        }
    }

    /**
     * @param privateQuestSelectedRequest this request is sent by the client when a private quest is chosen
     */
    @Override
    public synchronized void visit(@NotNull PrivateQuestSelectedRequest privateQuestSelectedRequest) {
        Server.log(">>private quest selected request [username : " + getPlayer(privateQuestSelectedRequest.getUserHash()).getUsername() + "]");
        getPlayer(privateQuestSelectedRequest.getUserHash()).setPrivateQuest(privateQuestSelectedRequest.getSelectedQuest());
        model.assignPrivateQuest(getPlayer(privateQuestSelectedRequest.getUserHash()), privateQuestSelectedRequest.getSelectedQuest());
    }

    /**
     * @param chatRequest this request is sent by the client to send a message to the match chat
     */
    @Override
    public void visit(@NotNull ChatRequest chatRequest) {

    }

    /**
     * @param showPlayerRequest this request is sent by the client to see a specific player's board on his view
     */
    @Override
    public void visit(@NotNull ShowPlayerRequest showPlayerRequest) {

    }

    /**
     * @param drawGoldenFromTableRequest this request is sent by the client to draw a golden card from uncovered table golden card
     */
    @Override
    public void visit(DrawGoldenFromTableRequest drawGoldenFromTableRequest) {

    }

    /**
     * @param drawResourceFromTableRequest this request is sent by the client to draw a resource card from uncovered table resource cards.
     */
    @Override
    public void visit(DrawResourceFromTableRequest drawResourceFromTableRequest) {

    }

    /**
     * @param drawResourceFromDeckRequest this request is sent by the client to draw a resource card directly from deck.
     */
    @Override
    public void visit(DrawResourceFromDeckRequest drawResourceFromDeckRequest) {

    }

    /**
     * @param drawGoldenFromDeckRequest this request is sent by the client to draw a golden card directly from deck.
     */
    @Override
    public void visit(DrawGoldenFromDeckRequest drawGoldenFromDeckRequest) {

    }

    /** this method adds the remote view to the MatchController whenever a new client joins the match
     * @param remoteView the client remote view
     */
    public synchronized void addRemoteView(RemoteView remoteView){
        remoteViews.add(remoteView);
    }

    public synchronized RemoteView getRemoteView(int hashCode){
        try {
            return remoteViews
                    .stream()
                    .filter(remoteView -> remoteView.getSocket().hashCode() == hashCode)
                    .findFirst()
                    .orElseThrow(() -> new Exception(">> RemoteView not found [hash : " + hashCode + "]"));
        } catch (Exception e) {
            Server.log(e.getMessage());
            return null;
        }

    }

    /** this method removes a player and his remote view from the match in case the client sends a QuitRequest or disconnects from the socket, in this case the method
     * is triggered by an IOException in ClientConnection thread, who is the first thread to notice the disconnection, calling LobbyController static method *disconnectClient()*
     * which will handle removing the client from his match, in case the player is in a started match.
     * @param p the player to be removed
     */
    public synchronized void removePlayer(Player p){
        players.remove(p);
        try {
            getRemoteView(p.getUserHash()).getSocket().close();
        }catch(IOException e){
            Server.log(e.getMessage());
        }
        remoteViews.remove(getRemoteView(p.getUserHash()));
        //model. remove player!!!! ---------------------------------------------------
        if((players.isEmpty() && !isStarted()) || (players.size() == 1 && isStarted())){
            setActive(false);
            //VINCITORE???????????????????????????????????????????????????????????????????????????????????????????????????????
        }
    }
    /**
     * this method sets started state to true whenever the match reaches the prefixed number of players, sends a broadcast message
     * to all clients in the starting match
     */
    private synchronized void start(){
        Server.log(">>match started [id : " + getMatchId() + "]");
        try {
            started = true;
            model = new MatchModel(numberOfPlayers, players);
            remoteViews.forEach(r -> model.addObserver(r));
            model.setId(id);
            model.gameSetup(); //initializer for decks, table cards , players hand and colors.
        }catch(Exception e){Server.log(e.getMessage());}
    }


    /** sends a specific response to all RemoteViews connected
     * @param response response to be sent
     */
    public synchronized void broadcast (Response response){
            remoteViews.forEach(remoteView -> remoteView.send(response));
    }

    public synchronized ArrayList<Player> getPlayers() {
        return players;
    }
    public synchronized Player getPlayer(int userHash){
        try {
            return players.stream().filter(player -> player.getUserHash() == userHash)
                                   .findFirst()
                                   .orElseThrow(() -> new Exception(">> Player not found [hash : " + userHash + "]"));
        } catch (Exception e) {
            Server.log(e.getMessage());
        }
        return null;
    }
}
