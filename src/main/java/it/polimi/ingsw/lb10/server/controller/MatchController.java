package it.polimi.ingsw.lb10.server.controller;

import it.polimi.ingsw.lb10.network.requests.match.*;
import it.polimi.ingsw.lb10.network.response.match.*;
import it.polimi.ingsw.lb10.server.Server;
import it.polimi.ingsw.lb10.server.model.MatchModel;
import it.polimi.ingsw.lb10.server.model.Player;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;
import it.polimi.ingsw.lb10.server.view.RemoteView;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.MatchRequestVisitor;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
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
    private final BlockingQueue<MatchRequest> requests;
    private final ArrayList<RemoteView> remoteViews;

    private boolean started = false;
    private final ArrayList<Player> players;
    private final int numberOfPlayers;

    public MatchController(int numberOfPlayers) {
        requests = new LinkedBlockingQueue<>();
        remoteViews = new ArrayList<>();
        players = new ArrayList<>();
        this.numberOfPlayers = numberOfPlayers;
    }

    public boolean isActive() {
        return active;
    }

    public int getId() {
        return id;
    }

    public boolean isStarted() {
        return started;
    }

    public void setActive(boolean status) {
        this.active = status;
    }

    public boolean isTerminated() {
        return model.isTerminated();
    }


    @Override
    public void run() {
        try {
            while (isActive()) {
                MatchRequest request = requests.take();
                request.accept(this);
            }
        } catch (Throwable e) {
            Server.log("[" + id + "]" + ">> " + e.getMessage());
            remoteViews.forEach(remoteView -> remoteView.send(new TerminatedMatchResponse()));
        }

    }

    // --------> GETTER <--------

    /**
     * this method puts the request in the BlockingQueue object to be handled by the MatchController
     *
     * @param request request sent by the client, which is referred to the specific match.
     * @throws InterruptedException in case MatchController thread terminates.
     */
    public synchronized void submitRequest(MatchRequest request) throws InterruptedException {
        requests.put(request);
    }

    /**
     * @return match id
     */
    public int getMatchId() {
        return id;
    }

    /**
     * This method is used to handle the "JoinMatchRequest"
     * sent by the client to the LobbyController, and from the LobbyController to the MatchController
     * in this method MatchController adds the player to his players, sends positive response and checks if the match can start. In case the match can start, sends a
     * broadcast message to all the players waiting.
     *
     * @param jmr join match request
     */
    @Override
    public synchronized void visit(@NotNull JoinMatchRequest jmr) {
        players.add(jmr.getPlayer()); //adds new player, safe because LobbyController checked if it's possible
        getRemoteView(jmr.getUserHash()).send(new JoinMatchResponse(true, getMatchId())); //sends response
        Server.log("[ " + id + "]" + ">>match joined [username : " + getPlayer(jmr.getUserHash()).getUsername());
        if (players.size() == numberOfPlayers) start();
    }

    /**
     * @param privateQuestsRequest request sent by the client to view and choose his private quests
     */
    @Override
    public synchronized void visit(@NotNull PrivateQuestsRequest privateQuestsRequest) {
        try {
            Server.log("[ " + id + "]" + ">>private quests request [username : " + getPlayer(privateQuestsRequest.getUserHash()).getUsername() + "]");
            getRemoteView(privateQuestsRequest.getUserHash()).send(new PrivateQuestsResponse(getPlayer(privateQuestsRequest.getUserHash()).getPrivateQuests()));
        } catch (Exception e) {
            Server.log("[ " + id + "]" + e.getMessage());
        }
    }

    /**
     * @param privateQuestSelectedRequest this request is sent by the client when a private quest is chosen
     */
    @Override
    public synchronized void visit(@NotNull PrivateQuestSelectedRequest privateQuestSelectedRequest) {
        Server.log("[ " + id + "]" + ">>private quest selected request [username : " + getPlayer(privateQuestSelectedRequest.getUserHash()).getUsername() + "]");
        getPlayer(privateQuestSelectedRequest.getUserHash()).setPrivateQuest(privateQuestSelectedRequest.getSelectedQuest());
        model.assignPrivateQuest(getPlayer(privateQuestSelectedRequest.getUserHash()), privateQuestSelectedRequest.getSelectedQuest());;
    }

    /**
     * @param chatRequest this request is sent by the client to send a message to the match chat
     */
    @Override
    public void visit(@NotNull ChatRequest chatRequest) {
        Server.log("[ " + id + "]" + ">>chat message from " + getPlayer(chatRequest.getUserHash()).getUsername());

        String [] splitMessage = chatRequest.getMessage().split(" ");
        if(splitMessage[0].equals("to")) {
            players.stream().filter(p -> p.getUsername().equals(splitMessage[1])).map(Player::getUserHash)
                    .findFirst()
                    .ifPresent(u -> model.notify(new ChatMessageResponse(
                            getPlayer(chatRequest.getUserHash()).getUsername(),
                            chatRequest.getMessage().substring(4 + getPlayer(u).getUsername().length())), u));
        } else
            model.notifyAll(new ChatMessageResponse(getPlayer(chatRequest.getUserHash()).getUsername(), chatRequest.getMessage()));
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
        Server.log("[" + id + "]" + ">>draw golden card from table request from " + getPlayer(drawGoldenFromTableRequest.getUserHash()).getUsername());
        if (isOnTurnPlayer(drawGoldenFromTableRequest.getUserHash()) && hasToPick(drawGoldenFromTableRequest.getUserHash())) {
            model.drawGoldenFromTable(getPlayer(drawGoldenFromTableRequest.getUserHash()), drawGoldenFromTableRequest.getIndex());
            Server.log("[" + id + "]" + ">>draw request valid");
        }  else if (!hasToPick(drawGoldenFromTableRequest.getUserHash())) {
            Server.log("[" + id + "]" + ">>draw request not valid");
            model.notify(new ServerNotification("You must place a card before picking one!", false), drawGoldenFromTableRequest.getUserHash());

        }else if (!isOnTurnPlayer(drawGoldenFromTableRequest.getUserHash())) {
            model.notify(new NotYourTurnResponse(model.getOnTurnPlayer().getUsername()), drawGoldenFromTableRequest.getUserHash());
            Server.log("[" + id + "]" + ">>draw request not valid");
        }
    }

    /**
     * @param drawResourceFromTableRequest this request is sent by the client to draw a resource card from uncovered table resource cards.
     */
    @Override
    public void visit(DrawResourceFromTableRequest drawResourceFromTableRequest) {
        Server.log("[" + id + "]" + ">>draw resource card from table request from " + getPlayer(drawResourceFromTableRequest.getUserHash()).getUsername());
        if (isOnTurnPlayer(drawResourceFromTableRequest.getUserHash()) && hasToPick(drawResourceFromTableRequest.getUserHash())) {
            model.drawResourceFromTable(getPlayer(drawResourceFromTableRequest.getUserHash()), drawResourceFromTableRequest.getIndex());
            Server.log("[" + id + "]" + ">>draw request valid");
        } else if (!isOnTurnPlayer(drawResourceFromTableRequest.getUserHash())) {
            model.notify(new NotYourTurnResponse(model.getOnTurnPlayer().getUsername()), drawResourceFromTableRequest.getUserHash());
            Server.log("[" + id + "]" + ">>draw request not valid");
        } else if (!hasToPick(drawResourceFromTableRequest.getUserHash())) {
            model.notify(new ServerNotification("You must place a card before picking one!", false), drawResourceFromTableRequest.getUserHash());
            Server.log("[" + id + "]" + ">>draw request not valid");
        }

    }

    /**
     * @param drawResourceFromDeckRequest this request is sent by the client to draw a resource card directly from deck.
     */
    @Override
    public void visit(DrawResourceFromDeckRequest drawResourceFromDeckRequest) {
        Server.log("[" + id + "]" + ">>draw resource card from deck request from " + getPlayer(drawResourceFromDeckRequest.getUserHash()).getUsername());
        if (isOnTurnPlayer(drawResourceFromDeckRequest.getUserHash()) && hasToPick(drawResourceFromDeckRequest.getUserHash())) {
            model.drawResourceFromDeck(getPlayer(drawResourceFromDeckRequest.getUserHash()));
            Server.log("[" + id + "]" + ">>draw request valid");
        } else if (!isOnTurnPlayer(drawResourceFromDeckRequest.getUserHash())) {
            model.notify(new NotYourTurnResponse(model.getOnTurnPlayer().getUsername()), drawResourceFromDeckRequest.getUserHash());
            Server.log("[" + id + "]" + ">>draw request not valid");
        } else if (!hasToPick(drawResourceFromDeckRequest.getUserHash())) {
            model.notify(new ServerNotification("You must place a card before picking one!", false), drawResourceFromDeckRequest.getUserHash());
            Server.log("[" + id + "]" + ">>draw request not valid");
        }

    }

    /**
     * @param drawGoldenFromDeckRequest this request is sent by the client to draw a golden card directly from deck.
     */
    @Override
    public void visit(@NotNull DrawGoldenFromDeckRequest drawGoldenFromDeckRequest) {
        Server.log("[" + id + "]" + ">>draw resource card from deck request from " + getPlayer(drawGoldenFromDeckRequest.getUserHash()).getUsername());
        if (isOnTurnPlayer(drawGoldenFromDeckRequest.getUserHash()) && hasToPick(drawGoldenFromDeckRequest.getUserHash())) {
            model.drawGoldenFromDeck(getPlayer(drawGoldenFromDeckRequest.getUserHash()));
            Server.log("[" + id + "]" + ">>draw request valid");
        } else if (!isOnTurnPlayer(drawGoldenFromDeckRequest.getUserHash())) {
            model.notify(new NotYourTurnResponse(model.getOnTurnPlayer().getUsername()), drawGoldenFromDeckRequest.getUserHash());
            Server.log("[" + id + "]" + ">>draw request not valid");
        } else if (!hasToPick(drawGoldenFromDeckRequest.getUserHash())) {
            model.notify(new ServerNotification("You must place a card before picking one!", false), drawGoldenFromDeckRequest.getUserHash());
            Server.log("[" + id + "]" + ">>draw request not valid");
        }
    }

    @Override
    public void visit(@NotNull PlaceStartingCardRequest placeStartingCardRequest) {
        Server.log("[" + id + "]" + "player " + getPlayer(placeStartingCardRequest.getUserHash()).getUsername() + " placed starting card");
        model.getPlayer(placeStartingCardRequest.getUserHash()).setStartingCard(placeStartingCardRequest.getStartingCard());
        model.insertStartingCard(getPlayer(placeStartingCardRequest.getUserHash()));
        if (players.stream().noneMatch(player -> player.getMatrix().getNode(41, 41).getCorners().isEmpty()))
            model.startTurns();
    }

    /**
     * See PlaceCardRequest constructor for more info on placing stream.
     *
     * @param placeCardRequest request sent by the client at the beginning of his turn when he wants to place a card on his matrix
     */
    @Override
    public void visit(PlaceCardRequest placeCardRequest) {
        Server.log("[ " + id + "]" + ">>new place card request");
        if (model.getPlayer(placeCardRequest.getUserHash()).equals(model.getOnTurnPlayer()) && !hasToPick(placeCardRequest.getUserHash())) {
            if (model.checkValidMatrixCardId(placeCardRequest.getMatrixCardId(), getPlayer(placeCardRequest.getUserHash()))) {
                Position position = placeCardRequest.getPosition();
                switch (position) {
                    case TOPLEFT ->
                            model.insertCard(getPlayer(placeCardRequest.getUserHash()), placeCardRequest.getCard(), getPlayer(placeCardRequest.getUserHash()).getMatrix().getCardRow(placeCardRequest.getMatrixCardId()) + 1, getPlayer(placeCardRequest.getUserHash()).getMatrix().getCardColumn(placeCardRequest.getMatrixCardId()) + 1);
                    case TOPRIGHT ->
                            model.insertCard(getPlayer(placeCardRequest.getUserHash()), placeCardRequest.getCard(), getPlayer(placeCardRequest.getUserHash()).getMatrix().getCardRow(placeCardRequest.getMatrixCardId()) + 1, getPlayer(placeCardRequest.getUserHash()).getMatrix().getCardColumn(placeCardRequest.getMatrixCardId()) - 1);
                    case BOTTOMLEFT ->
                            model.insertCard(getPlayer(placeCardRequest.getUserHash()), placeCardRequest.getCard(), getPlayer(placeCardRequest.getUserHash()).getMatrix().getCardRow(placeCardRequest.getMatrixCardId()) - 1, getPlayer(placeCardRequest.getUserHash()).getMatrix().getCardColumn(placeCardRequest.getMatrixCardId()) + 1);
                    case BOTTOMRIGHT ->
                            model.insertCard(getPlayer(placeCardRequest.getUserHash()), placeCardRequest.getCard(), getPlayer(placeCardRequest.getUserHash()).getMatrix().getCardRow(placeCardRequest.getMatrixCardId()) - 1, getPlayer(placeCardRequest.getUserHash()).getMatrix().getCardColumn(placeCardRequest.getMatrixCardId()) - 1);
                }

            } else {
                model.notify(new PlaceCardResponse(null, false, 0, 0, null, "Invalid card id", model.isFinalTurnStarted()), placeCardRequest.getUserHash());
            }

        } else {
            if(model.getOnTurnPlayer() == null) model.notify(new ServerNotification("Wait for all players to place starting card before placing one!", false), placeCardRequest.getUserHash());
            else if(!model.getPlayer(placeCardRequest.getUserHash()).equals(model.getOnTurnPlayer())) model.notify(new NotYourTurnResponse(model.getOnTurnPlayer().getUsername()), placeCardRequest.getUserHash());
            else model.notify(new PlaceCardResponse(null, false, 0, 0, null, "You already placed your card,\npick one to end your turn!", model.isFinalTurnStarted()), placeCardRequest.getUserHash());
        }

    }

    @Override
    public void visit(PickRequest pickRequest) { //TEST !!
        Server.log("[ " + id + "]" + ">>new pick request");
        if(model.isFinalTurnStarted()) {
            model.notify(new PickedCardResponse(null, false, "Final turn reached", getPlayer(pickRequest.getUserHash()).getMatrix()), pickRequest.getUserHash());
            model.endTurn(pickRequest.getUserHash(), getPlayer(pickRequest.getUserHash()).getPoints());
        } else {
            model.notify(new ShowPickingPossibilitiesResponse(!model.getGoldenDeck().getCards().isEmpty() ? model.getGoldenDeck().getCards().getLast() : null, !model.getResourceDeck().getCards().isEmpty() ? model.getResourceDeck().getCards().getLast() : null, model.getGoldenUncovered(), model.getResourceUncovered()), pickRequest.getUserHash());
        }
    }

    /**
     * @param moveBoardRequest request sent by the client to move his board
     */
    @Override
    public void visit(MoveBoardRequest moveBoardRequest) {
        Server.log("[ " + id + "]" + ">>move board request");
        model.notify(new MoveBoardResponse(model.getPlayer(moveBoardRequest.getUserHash()).getMatrix(), moveBoardRequest.getxOffset(), moveBoardRequest.getyOffset()), moveBoardRequest.getUserHash());
    }


    @Override
    public void visit(QuitMatchRequest quitMatchRequest) {
        Server.log("[" + id + "] " + getPlayer(quitMatchRequest.getUserHash()).getUsername() + " left match");
        removePlayer(getPlayer(quitMatchRequest.getUserHash()));
    }

    /**
     * this method adds the remote view to the MatchController whenever a new client joins the match
     *
     * @param remoteView the client remote view
     */
    public synchronized void addRemoteView(RemoteView remoteView) {
        remoteViews.add(remoteView);
    }

    public synchronized RemoteView getRemoteView(int hashCode) {
        try {
            return remoteViews
                    .stream()
                    .filter(remoteView -> remoteView.getSocket().hashCode() == hashCode)
                    .findFirst()
                    .orElseThrow(() -> new Exception(">>RemoteView not found [hash : " + hashCode + "]"));
        } catch (Exception e) {
            Server.log("[" + id + "]" + e.getMessage());
            return null;
        }

    }

    /**
     * this method removes a player and his remote view from the match in case the client sends a QuitRequest or disconnects from the socket, in this case the method
     * is triggered by an IOException in ClientConnection thread, who is the first thread to notice the disconnection, calling LobbyController static method *disconnectClient()*
     * which will handle removing the client from his match, in case the player is in a started match.
     *
     * @param p the player to be removed
     */
    public synchronized void removePlayer(Player p) {
        players.remove(p);
        p.setInMatch(false);
        if(isStarted()) {
            model.removeObserver(getRemoteView(p.getUserHash()));
            model.removePlayer(p);
            if (((players.size() < 2 && isStarted()) && !model.isTerminated())) { //logic : if match has STARTED but is not TERMINATED, all players have left apart from one -> notify endGame and close, if is
                //STARTED and TERMINATED -> game is correctly terminated -> all players are quitting correctly
                setActive(false);
                model.notify(new EndGameResponse(players.getFirst(), players, false), players.getFirst().getUserHash());
                model.terminate();
            }
        }else if(players.isEmpty()) setActive(false);
        remoteViews.remove(getRemoteView(p.getUserHash()));


        if (isStarted() && isTerminated() || isActive()) {
            LobbyController.terminateMatch(id);
        }
    }

    /**
     * this method sets started state to true whenever the match reaches the prefixed number of players, sends a broadcast message
     * to all clients in the starting match
     */
    private synchronized void start() {
        Server.log("[ " + id + "]" + ">>match started");
        try {
            started = true;
            model = new MatchModel(numberOfPlayers, players);
            remoteViews.forEach(r -> model.addObserver(r));
            model.setId(id);
            model.gameSetup(); //initializer for decks, table cards , players hand and colors.
        } catch (Exception e) {
            Server.log("[" + id + "]" + e.getMessage());
        }
    }


    public synchronized ArrayList<Player> getPlayers() {
        return players;
    }

    public synchronized Player getPlayer(int userHash) {
        try {
            return players.stream().filter(player -> player.getUserHash() == userHash)
                    .findFirst()
                    .orElseThrow(() -> new Exception(">> Player not found [hash : " + userHash + "]"));
        } catch (Exception e) {
            Server.log("[" + id + "]" + e.getMessage());
        }
        return null;
    }

    public boolean isOnTurnPlayer(int userHash) {
        return getPlayer(userHash).equals(model.getOnTurnPlayer());
    }

    public boolean hasToPick(int userHash) {
        return model.getPlayer(userHash).getHand().size() == 2;
    }
}
