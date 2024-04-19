package it.polimi.ingsw.lb10.server.controller;

import it.polimi.ingsw.lb10.server.model.*;
import it.polimi.ingsw.lb10.server.model.DrawType.DrawStrategy;
import it.polimi.ingsw.lb10.server.model.DrawType.GoldenDeckDraw;
import it.polimi.ingsw.lb10.server.model.DrawType.ResourceDeckDraw;
import it.polimi.ingsw.lb10.server.model.DrawType.ResourceUncovered;
import it.polimi.ingsw.lb10.server.model.cards.Card;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;
import it.polimi.ingsw.lb10.network.requests.Request;
import it.polimi.ingsw.lb10.server.view.RemoteView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

/*@ this class is the single match controller, every match has one
*@ the run() method takes continuously the requests (pushed by ClientConnection(s)) from the BlockingQueue snd processes
*@ them by entering the model and updating the view
*@ this implementation goes over the Observer implementation to optimize thread synchronization
*@ In fact, using an Observer implementation, this class wouldn't be a Runnable istance, and ClientConnection
*@ would access this object
*@ with synchronized blocks to update the model and the view. Using this kind of implementation (BlockingQueue),
*@ ClientConnection
*@ won't have to wait for the model and view to be updated, they can get continuous request that will be submitted
*@ to this queue and executed inside this separeted thread!*/

public class MatchController implements Runnable {
//set the condition fot the end of the game
    private Boolean active = true;
    private MatchModel model;
    private BlockingQueue<Request> requests;
    private final Position[] possiblePosition;
    private DrawStrategy drawStrategy;
    private ArrayList<Player> players;
    public MatchController(){
        possiblePosition= new Position[]{Position.TOPLEFT, Position.TOPRIGHT, Position.BOTTOMRIGHT, Position.BOTTOMLEFT};
        drawStrategy=null;
    }
    private RemoteView remoteView;

    //Game Model fields

    @Override
    public void run() {
        while(active){

        }
    }

    /**
     *  WIP
     */
    public void initGame(String id ) throws IOException {
        model= new MatchModel(id);
        model.initializeTable();
    }

    // --------> INSERTION <--------

    /**
     *      It's the method that the caller calls
     *  with the initialization of the card inside the matrix
     *  --> At the beginning the algorithm checks the flipped status of the card, with the consequent update of the state of the card.
     *      the card is set inside the matrix,
     *      it's checked if the card respects the activation cost (in any case),
     *      after that it's called checkInsertion()
     */
    public boolean insertCard(Player player,Card card,int row, int column){
        player.getMatrix().setCard(card, row, column);

        if(!checkActivationCost(player,card))
            return false;

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
    public boolean checkInsertion(Player player,Card card,int row, int column){

        if (verificationSetting(player, row, column)) {
            setCardResourceOnPlayer(player, card);
            deleteCoveredResource(player, row, column);
            player.addPoints(card.getStateCardPoints());
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

    public boolean checkActivationCost(Player player,Card card){
        if(card.getStateCardActivationCost()==null)
            return true;
        for (Map.Entry<Resource, Integer> entry : card.getStateCardActivationCost().entrySet()) {
            if(player.getResourceQuantity(entry.getKey()) < entry.getValue())
                return false;
        }
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

    public void addPlayer(Player player){
        players.add(player);
    }
    public void removePlayer(Player player){
        players.remove(player);
    }
    // --------> REQUESTS <--------

    /**
     * @param player wants to add a card from the resource uncovered  to the cards on hand
     * @param first means if th player wants to draw the first card, if it's false he'll draw th second card
     */
    public void addResourceUncoveredCardOnHand (Player player,boolean first){
        drawStrategy= new ResourceUncovered(player,getModel(), first);
        drawStrategy.drawCard();
    }
    /**
     * @param player wants to add a card from the golden uncovered to the cards on hand
     * @param first means if th player wants to draw the first card, if it's false he'll draw th second card
     */
    public void addGoldenUncoveredCardOnHand (Player player, boolean first){
        drawStrategy= new ResourceUncovered(player, getModel(),first);
        drawStrategy.drawCard();
    }
    /**
     * @param player wants to add a card from the golden deck to the cards on hand
     */
    public void addGoldenDeckCardOnHand (Player player) {
        drawStrategy= new GoldenDeckDraw(player,getModel());
        drawStrategy.drawCard();
    }
    /**
     * @param player wants to add a card from the resource deck to the cards on hand
     */
    public void addResourceDeckCardOnHand (Player player) {
        drawStrategy= new ResourceDeckDraw(player,getModel());
        drawStrategy.drawCard();
    }

    // --------> GETTER <--------
    public MatchModel getModel() {
        return model;
    }

    public Position[] getPossiblePosition() {
        return possiblePosition;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
