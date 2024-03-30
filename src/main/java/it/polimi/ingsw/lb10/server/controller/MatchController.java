package it.polimi.ingsw.lb10.server.controller;

import it.polimi.ingsw.lb10.server.model.MatchModel;
import it.polimi.ingsw.lb10.server.model.Node;
import it.polimi.ingsw.lb10.server.model.Player;
import it.polimi.ingsw.lb10.server.model.cards.Card;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;
import it.polimi.ingsw.lb10.network.requests.Request;
import it.polimi.ingsw.lb10.server.view.RemoteView;

import java.util.ArrayList;
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

    private Boolean active = true;
    private MatchModel model;
    private BlockingQueue<Request> requests;
    private final Position[] possiblePosition;

    public MatchController(MatchModel model){
        this.model=model;
        possiblePosition= new Position[]{Position.TOPLEFT, Position.TOPRIGHT, Position.BOTTOMRIGHT, Position.BOTTOMLEFT};
    }
    private RemoteView remoteView;

    //Game Model fields

    @Override
    public void run() {
        while(active){

        }
    }

    /**
     * The method that starts the Insertion rules
     *
     */
    public boolean checkInsertion(Player player,Card card,int row, int column){
        //if the player isn't in the MatchModel players List
        if(getModel().isNotPlayerIn(player))
            return false;
        if(verificationSetting(player,card,row,column)){
            setCardResourceOnPlayer(player,card);
            deleteCoveredResource(player,row,column);
            return true;
        }
        player.getMatrix().deleteCard(row,column);
        return  false;
    }

    /**
     * @param player calls the method, we need the reference for the matrix
     * @param card is to add
     * @param row row
     * @param column column
     * @return true if the card passed all the requirements
     */
    public boolean verificationSetting(Player player,Card card,int row, int column){

        //if one corner isn't available
        if(checkNotAvailability(player,row,column))
            return false;

        ArrayList<Node> nodesVisited= new ArrayList<>();
        Map<Position, int[]> setIncrement = player.getMatrix().parsingPositionCorners();
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
        Map<Position, int[]> setIncrement = player.getMatrix().parsingPositionCorners();

        for(Position position: getPossiblePosition()){
            int[] delta = setIncrement.get(position);
            if (player.getMatrix().getNode(row + delta[0], column + delta[1]).checkIsNotAvailable())
                return false;
        }
        return true;
    }

    public void setCardResourceOnPlayer(Player player, Card card){
        for(int i=0;i<4;i++)
            player.addOnMapResources(card.getCorners().get(i).getResource());
    }

    public void deleteCoveredResource(Player player,int row, int column){
        Map<Position, int[]> setIncrement = player.getMatrix().parsingPositionCorners();

        for(Position position: getPossiblePosition()){
            int[] delta = setIncrement.get(position);
            if (player.getMatrix().getNode(row + delta[0], column + delta[1]).getCorners().size()==2)
                player.deleteOnMapResources(player.getMatrix().getNode(row + delta[0], column + delta[1]).getCorners().get(0).getResource());
        }
    }

    public void process(Request m){

    }

    // --------> GETTER <--------
    public MatchModel getModel() {
        return model;
    }

    public Position[] getPossiblePosition() {
        return possiblePosition;
    }

}
