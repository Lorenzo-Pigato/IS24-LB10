package it.polimi.ingsw.lb10.client.controller;

import it.polimi.ingsw.lb10.client.Client;
import it.polimi.ingsw.lb10.client.exception.ConnectionErrorException;

import it.polimi.ingsw.lb10.client.exception.ExceptionHandler;
import it.polimi.ingsw.lb10.network.requests.Request;
import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.network.response.lobby.HashResponse;
import it.polimi.ingsw.lb10.network.response.match.PrivateQuestsResponse;
import it.polimi.ingsw.lb10.server.Server;
import it.polimi.ingsw.lb10.server.model.cards.PlaceableCard;
import it.polimi.ingsw.lb10.server.model.cards.StartingCard;
import org.jetbrains.annotations.NotNull;


import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public abstract class ClientViewController {

    protected Socket socket;
    protected Client client;
    protected ObjectInputStream socketIn;
    protected ObjectOutputStream socketOut;
    protected int hash;

    protected int matchId;
    protected ArrayList<PlaceableCard> hand;
    protected StartingCard startingCard;

    protected boolean startingCardHasBeenPlaced = false;

    protected ExceptionHandler exceptionHandler;

    // ------------------ SETTERS ------------------ //

    public void setSocket(Socket socket) {this.socket = socket;}
    public void setClient(Client client) {this.client = client;}
    public void setExceptionHandler(ExceptionHandler exceptionHandler) {this.exceptionHandler = exceptionHandler;}
    public void setMatchId(int matchId) {this.matchId = matchId;}
    public void setHand(ArrayList<PlaceableCard> hand) {this.hand = hand;}
    public void setStartingCard(StartingCard startingCard) {this.startingCard = startingCard;}
    public void setStartingCardHasBeenPlaced(boolean status) {this.startingCardHasBeenPlaced = status;}

    // -------------- GETTERS -------------- //

    public ExceptionHandler getExceptionHandler() {return exceptionHandler;}
    public int getUserHash() {return hash;}
    public int getMatchId() {return matchId;}
    public StartingCard getStartingCard() {return startingCard;}
    public boolean startingCardHasBeenPlaced() {return !startingCardHasBeenPlaced;}
    public ArrayList<PlaceableCard> getHand() {return hand;}
    public Socket getSocket() {return socket;}
    public Client getClient() {return client;}



    // ------------------ METHODS ------------------ //

    /**
     * This method is used to set up the communication streams with the Server.
     */
    public void setUp() {
        try {
            socketOut = new ObjectOutputStream(socket.getOutputStream());
            socketOut.flush();
        } catch (IOException e) {
            exceptionHandler.handle(e);
            close();
        }
        try {
            socketIn = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            exceptionHandler.handle(e);
            close();
        }
    }

    /**
     * This asynchronous method is run by a separated thread to receive asynchronous requests from user command line (commands)
     * This method uses CommandParser class' static method parse(String input) to figure out the command given and reacts by invoking
     * the handler method "..."
     */
    public abstract Thread asyncReadFromSocket();

    /**
     * This asynchronous method provides a thread to send asynchronous requests to the network layer through
     * serialization
     *
     * @param message the request
     * @return the thread
     */
    public Thread asyncWriteToSocket(Request message) {
        return new Thread(() -> send(message));
    }

    public void send(@NotNull Request request) {
        request.setUserHash(hash); //wraps hashcode inside request, very important!!
        try {
            socketOut.reset();
            socketOut.writeObject(request);
            socketOut.flush();
        } catch (IOException e) {
            exceptionHandler.handle(e);
            close();
        }
    }

    public Response syncReceive() {
        Response response = null;
        try {
            response = (Response) socketIn.readObject();
        } catch (EOFException e) {
            close();
            client.setActive(false);
            exceptionHandler.handle(e);
        } catch (IOException  | ClassNotFoundException e){
            exceptionHandler.handle(e);
        }
        return response;
    }

    public abstract void initializeConnection() throws ConnectionErrorException;

    /**
     * Used to provide an input scanner for login requests, checking the string for the username
     * and sending it to the Server.
     * A Boolean Response is waited and handled , setting "logged" flag to true is response is positive
     */
    public abstract void login();

    public abstract void joinMatch();

    /**
     * Used to set the layout of the waiting room, state in which the player has already joined the match but the match
     * hasn't started yet.
     */
    public abstract void waitingRoom();

    /**
     * Used to get unique hashCode from server, this hashCode will be wrapped inside every request, so the Server controllers
     * can easily handle multiple requests from multiple clients.
     */
    public void setHash() {
        try {
            HashResponse hashResponse = (HashResponse) socketIn.readObject();
            this.hash = hashResponse.getHash();
        } catch (Exception e) {
            Server.log(e.getMessage());
            close();
        }
    }


    public void flipStarting() {startingCard.swapState();}

    public void flipCard(int index) {hand.get(index).swapState();}

    public abstract void game();

    /**
     * This method closes all Socket streams used to communicate
     */
    public void close() {
        if (!socket.isClosed()) {
            try {
                socketIn.close();
                socketOut.close();
                socket.close();
            } catch (IOException e) {
                exceptionHandler.handle(e);
            } finally {
                client.setActive(false);
            }
        }
    }
}







