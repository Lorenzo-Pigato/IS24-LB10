package it.polimi.ingsw.lb10.client.controller;

import it.polimi.ingsw.lb10.client.Client;
import it.polimi.ingsw.lb10.client.cli.CLICommand;
import it.polimi.ingsw.lb10.client.cli.clipages.*;
import it.polimi.ingsw.lb10.client.exception.ConnectionErrorException;
import it.polimi.ingsw.lb10.client.exception.ExceptionHandler;
import it.polimi.ingsw.lb10.client.util.InputParser;
import it.polimi.ingsw.lb10.client.util.InputVerifier;
import it.polimi.ingsw.lb10.client.view.CLIClientView;
import it.polimi.ingsw.lb10.network.requests.QuitRequest;
import it.polimi.ingsw.lb10.network.requests.Request;
import it.polimi.ingsw.lb10.network.requests.match.PrivateQuestSelectedRequest;
import it.polimi.ingsw.lb10.network.requests.preMatch.LobbyToMatchRequest;
import it.polimi.ingsw.lb10.network.requests.preMatch.LoginRequest;
import it.polimi.ingsw.lb10.network.requests.preMatch.NewMatchRequest;
import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.network.response.lobby.HashResponse;
import it.polimi.ingsw.lb10.network.response.match.PrivateQuestsResponse;
import it.polimi.ingsw.lb10.server.Server;
import it.polimi.ingsw.lb10.server.model.Player;
import it.polimi.ingsw.lb10.server.model.cards.PlaceableCard;
import it.polimi.ingsw.lb10.server.model.cards.StartingCard;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.CLIResponseHandler;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class CLIClientViewController implements ClientViewController{

    private static CLIClientViewController instance;
    private  CLIClientView view;
    private Socket socket;
    private Client client;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;
    private int hash;
    private static final CLIResponseHandler responseHandler = CLIResponseHandler.instance();
    private int matchId;
    private Player onTurn;
    private boolean resourceDeckAvailable = true;
    private boolean goldenDeckAvailable = true;
    private ArrayList<PlaceableCard> hand;
    private StartingCard startingCard;


    public static CLIClientViewController instance(){
       if(instance == null) instance = new CLIClientViewController();
       return instance;
   }

    // ------------------ SETTERS ------------------ //
    public void setCliClientView(CLIClientView cliClientView){this.view = cliClientView;}
    @Override
    public void setSocket(Socket socket) {this.socket = socket;}
    public void setClient(Client client) {this.client = client;}
    public void setOnTurn(Player player){onTurn = player;}
    public void setResourceDeckAvailable(boolean status){this.resourceDeckAvailable = status;}
    public void setGoldenDeckAvailable(boolean status){this.goldenDeckAvailable = status;}
    public boolean resourceDeckIsAvailable() {return resourceDeckAvailable;}
    public boolean goldenDeckIsAvailable() {return goldenDeckAvailable;}
    public void setStartingCard(StartingCard startingCard) {this.startingCard = startingCard;}

    public Socket getSocket() {return socket;}
    public Client getClient() {return client;}
    public CLIClientView getView() {return view;}
    public Player getOnTurn() {return onTurn;}
    public void setHand(ArrayList<PlaceableCard> hand){this.hand = hand;}
    public void flipCard(int index){hand.get(index).swapState();}
    public ArrayList<PlaceableCard> getHand() {return hand;}
    public StartingCard getStartingCard() {return startingCard;}

    /**
     *
     */
    @Override
    public void flipStarting() {startingCard.swapState();}


    @Override
    public void setHash() {
        try{
            HashResponse hashResponse = (HashResponse) socketIn.readObject();
            this.hash = hashResponse.getHash();
        }catch(Exception e){
            Server.log(e.getMessage());
            close();
        }
    }

    @Override
    public void setMatchId(int  matchId){
        this.matchId = matchId;
    }

    @Override
    public int getMatchId(){
        return matchId;
    }

    public int getUserHash(){return hash;}
    @Override
    public void close() {
       if(!socket.isClosed()) {
           try {
               socketIn.close();
               socketOut.close();
               socket.close();
           } catch (IOException e) {
               ExceptionHandler.handle(e, view);
           } finally {
               client.setActive(false);
           }
       }
    }


    @Override
    public void setUp(){
        try{
            socketOut = new ObjectOutputStream(socket.getOutputStream());
            socketOut.flush();
        }catch(IOException e){
            ExceptionHandler.handle(e, view);
        }
        try{
            socketIn = new ObjectInputStream(socket.getInputStream());
        }catch(IOException e){
            ExceptionHandler.handle(e, view);
        }
    }

    @Override
    public void login() {
       if(client.isActive()) {
           view.setPage(new CLILoginPage());
           view.displayPage(null);

           Scanner in = new Scanner(System.in);
           String username;
           try {
               do {
                   do {
                       username = in.nextLine();
                       if (username.length() < 2 || username.length() > 15)
                           view.updatePageState(new CLILoginPage.invalidLength());
                       view.displayPage(new String[]{username});
                   } while (username.length() < 2 || username.length() > 15);
                   send(new LoginRequest(username));
                   syncReceive().accept(responseHandler);
                   if (!client.isLogged()) {
                       view.updatePageState(new CLILoginPage.alreadyTaken());
                       view.displayPage(new String[]{username});
                   }
               } while (!client.isLogged());
           } catch (NullPointerException e) {
               close();
           }
       }
    }

    @Override
    public void joinMatch() {
       if(client.isActive()) {
           view.setPage(new CLILobbyPage());
           view.displayPage(null);
           Scanner in = new Scanner(System.in);
           String input;

           try {
               do {
                   input = in.nextLine();
                   String[] splitInput = input.split(" ");

                   if (splitInput[0].equalsIgnoreCase("join") && splitInput.length == 2) {
                       send(new LobbyToMatchRequest(Integer.parseInt(splitInput[1])));
                       syncReceive().accept(responseHandler);

                       if (!client.isInMatch()) view.updatePageState(new CLILobbyPage.InvalidInput());

                   } else if (splitInput[0].equalsIgnoreCase("new") && splitInput.length == 2) {
                       try{
                           if (Integer.parseInt(splitInput[1]) >= 2 && Integer.parseInt(splitInput[1]) <= 4) {
                               send(new NewMatchRequest(Integer.parseInt(splitInput[1])));
                               syncReceive().accept(responseHandler);
                           } else view.updatePageState(new CLILobbyPage.InvalidInput());
                       } catch (NumberFormatException nan){
                           view.updatePageState(new CLILobbyPage.InvalidInput());
                       }

                   } else if (splitInput[0].equalsIgnoreCase("quit")) {
                       send(new QuitRequest());
                       client.setActive(false);
                       break;

                   } else view.updatePageState(new CLILobbyPage.InvalidInput());

                   view.displayPage(new String[]{input});
               } while (!client.isInMatch());
           } catch (NullPointerException e) {
               close();
           }
       }
    }

    public void waitingRoom(){
       if(client.isActive()) {
           view.setPage(new CLIWaitingPage());
           view.displayPage(new Object[]{matchId});
           try {
               syncReceive().accept(responseHandler); //either StartedMatchResponse or TerminatedMatchResponse
           } catch (NullPointerException e) { //socket has been closed, response is null => close communication with error
               close();
           }
       }
    }

    @Override
    public void privateQuestSelection(PrivateQuestsResponse response){
        Scanner in = new Scanner(System.in);
        String input;
        view.setPage(new CLIStartMatchPage());
        view.displayPage(new Object[]{response.getPrivateQuests().get(0), response.getPrivateQuests().get(1)});
        String[] parsed;
        boolean valid;
        do{
            input = in.nextLine();
            parsed = input.split(" ");
            valid = parsed.length == 1 && ((parsed[0].equalsIgnoreCase("1")) || (parsed[0].equalsIgnoreCase("2")));
            if(parsed[0].equalsIgnoreCase("quit")) send(new QuitRequest());
            if(!valid){
                view.updatePageState(new CLIStartMatchPage.InvalidInput());
                view.displayPage(new Object[]{input});
            } else if ("1".equalsIgnoreCase(parsed[0])) {
                send(new PrivateQuestSelectedRequest(matchId, response.getPrivateQuests().getFirst()));
            } else if ("2".equalsIgnoreCase(parsed[0])) {
                send(new PrivateQuestSelectedRequest(matchId, response.getPrivateQuests().get(1)));
            }
        }while(!valid);
        view.updatePageState(new CLIStartMatchPage.WaitPage());
        view.displayPage(new Object[]{input});
    }


    @Override
    public void game(){
        Thread terminalReader = asyncReadFromTerminal();
        Thread socketReader = asyncReadFromSocket();
        terminalReader.start();
        socketReader.start();
        try{
            terminalReader.join();
            socketReader.join();
        }catch(InterruptedException e){
            client.setActive(false);
            close();
            //match is Terminated
        }

    }

    public void send(Request request){
       request.setUserHash(hash); //wraps hashcode inside request, very important!!
        try{
            socketOut.reset();
            socketOut.writeObject(request);
            socketOut.flush();
        }catch(IOException e){
            ExceptionHandler.handle(e, view);
            close();
        }
    }

    public Response syncReceive(){
        Response response = null;
        try{
            response = (Response)socketIn.readObject();
        }catch(Exception e){
            ExceptionHandler.handle(e, view);
            close();
        }
        return response;
    }

    @Override
    public void showUserOutput(Object o) {
        Thread viewChanger = asyncWriteToTerminal();
        viewChanger.start();
    }


    /**
     * This asynchronous method is run by a separated thread to receive asynchronous requests from user command line (commands)
     * This method uses CommandParser class' static method parse(String input) to figure out the command given and reacts by invoking
     * the handler method "..."
     */
    public Thread asyncReadFromSocket(){
        return new Thread(() -> {
            try{
                while(client.isActive()){
                    Response response = (Response)socketIn.readObject();
                    response.accept(responseHandler);
                }
            }catch(Exception e){
                close();
            }
        });
    }

    /**
     *  This asynchronous method provides a thread to send asynchronous requests to the network layer through
     *  serialization
     * @param message the request
     * @return the thread
     */
    public Thread asyncWriteToSocket(Request message){
        message .setUserHash(hash);
        return new Thread(() -> send(message));
    }

    /**
     * This asynchronous method is run by a separated thread to receive asynchronous requests from user command line (commands)
     * This method uses CommandParser class' static method parse(String input) to figure out the command given and reacts by invoking
     * the handler method "..."
     * @return the thread to be run
     */
    public Thread asyncReadFromTerminal() {
        return new Thread(() -> {
            Scanner in = new Scanner(System.in);
            Request futureRequest;
            while (client.isActive()) {
                try {
                    String input = in.nextLine();
                    futureRequest = InputParser.parse(input);

                    CLICommand.restoreCursorPosition();
                    CLICommand.clearLineAfterCursor();

                    if(futureRequest != null) {
                        asyncWriteToSocket(futureRequest).start();
                    }else{
                        //view error invalid command!
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    close();
                }
            }
        });
    }

    public Thread asyncWriteToTerminal() {
        return new Thread(() -> {
           //show view via the VIEW
        });
    }

    @Override
    public void initializeConnection() throws ConnectionErrorException { //------> Tested | OK <------//
        Socket cliSocket;
        Scanner in = new Scanner(System.in);
        String input;
        String[] parsed;
        //x.y.z.w:k

        view.setPage(new CLIConnectionPage());
        view.displayPage(null);

        do{
            input = in.nextLine();
            parsed = input.split(":");

            if(parsed.length != 2 ||
                    InputVerifier.isNotValidIP(parsed[0]) && InputVerifier.isNotValidPort(parsed[1])){ //invalid input, none of the fields is correct (ip:port)
                view.updatePageState(new CLIConnectionPage.InvalidInput());
            } else if (InputVerifier.isNotValidIP(parsed[0])) {
                view.updatePageState(new CLIConnectionPage.InvalidIP());
            } else if (InputVerifier.isNotValidPort(parsed[1])) {
                view.updatePageState(new CLIConnectionPage.InvalidPort());
            }
            view.displayPage(new String[] {input});
        }while(parsed.length != 2 || InputVerifier.isNotValidPort(parsed[1]) || InputVerifier.isNotValidIP(parsed[0]));
        try {
            cliSocket = new Socket(parsed[0],Integer.parseInt(parsed[1]));
        } catch (Exception e) {
            throw new ConnectionErrorException();
        }
        setSocket(cliSocket);
    }



}


