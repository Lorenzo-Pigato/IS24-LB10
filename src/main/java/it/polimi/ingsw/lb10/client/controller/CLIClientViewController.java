package it.polimi.ingsw.lb10.client.controller;

import it.polimi.ingsw.lb10.client.cli.CLICommand;
import it.polimi.ingsw.lb10.client.cli.clipages.*;
import it.polimi.ingsw.lb10.client.exception.ConnectionErrorException;
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
import it.polimi.ingsw.lb10.network.response.match.PrivateQuestsResponse;
import it.polimi.ingsw.lb10.server.model.quest.Quest;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.CLIResponseHandler;

import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Scanner;

public class CLIClientViewController extends ClientViewController {

    private static CLIClientViewController instance;
    private CLIClientView view;
    private static final Object inputReaderLock = new Object();

    private static final CLIResponseHandler responseHandler = CLIResponseHandler.instance();
    private boolean matchStarted = false;
    private ArrayList<Quest> quests;

    public static CLIClientViewController instance() {
        if (instance == null) instance = new CLIClientViewController();
        return instance;
    }

    // ------------------ SETTERS ------------------ //
    public void setMatchStarted(boolean status){this.matchStarted = status;}
    public void setCliClientView(CLIClientView cliClientView) {this.view = cliClientView;}
    public void setQuests(ArrayList<Quest> quests) {this.quests = quests;}


    // ------------------ GETTERS ------------------ //
    public CLIClientView getView() {return view;}
    public ArrayList<Quest> getQuests(){return quests;}
    public static Object getLock(){return inputReaderLock;}

    // ------------------ METHODS ------------------ //

    @Override
    public void initializeConnection() throws ConnectionErrorException {
        Socket cliSocket;
        Scanner in = new Scanner(System.in);
        String input;
        String[] parsed;
        //x.y.z.w:k

        view.setPage(new CLIConnectionPage());
        view.displayPage(null);

        do {
            input = in.nextLine();

            if(input.isEmpty()) input = "127.0.0.1:1234";

            parsed = input.split(":");

            if (parsed.length != 2 ||
                    InputVerifier.isNotValidIP(parsed[0]) && InputVerifier.isNotValidPort(parsed[1])) { //invalid input, none of the fields is correct (ip:port)
                view.updatePageState(new CLIConnectionPage.InvalidInput());
            } else if (InputVerifier.isNotValidIP(parsed[0])) {
                view.updatePageState(new CLIConnectionPage.InvalidIP());
            } else if (InputVerifier.isNotValidPort(parsed[1])) {
                view.updatePageState(new CLIConnectionPage.InvalidPort());
            }
            view.displayPage(new String[]{input});
        } while (parsed.length != 2 || InputVerifier.isNotValidPort(parsed[1]) || InputVerifier.isNotValidIP(parsed[0]));
        try {
            cliSocket = new Socket(parsed[0], Integer.parseInt(parsed[1]));
        } catch (Exception e) {
            throw new ConnectionErrorException();
        }
        setSocket(cliSocket);
    }

    @Override
    public void login() {
        synchronized (CLIClientViewController.getLock()) {
            if (client.isActive()) {
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
                        CLIClientViewController.getLock().wait();
                    } while (client.isNotLogged());
                } catch (NullPointerException e) {
                    close();
                    getExceptionHandler().handle(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void joinMatch() {
        synchronized (CLIClientViewController.getLock()) {
            if (client.isActive()) {
                view.setPage(new CLILobbyPage());
                view.displayPage(null);
                Scanner in = new Scanner(System.in);
                String input;

                try {
                    do {
                        input = in.nextLine();
                        String[] splitInput = input.split(" ");

                        if (splitInput[0].equalsIgnoreCase("join") && splitInput.length == 2) {
                            try {
                                Integer.parseInt(splitInput[1]);
                                send(new LobbyToMatchRequest(Integer.parseInt(splitInput[1])));
                                CLIClientViewController.getLock().wait();
                            } catch (NumberFormatException e) {
                                view.updatePageState(new CLILobbyPage.InvalidInput());
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            if (client.isNotInMatch()) view.updatePageState(new CLILobbyPage.InvalidInput());

                        } else if (splitInput[0].equalsIgnoreCase("new") && splitInput.length == 2) {
                            try {
                                if (Integer.parseInt(splitInput[1]) >= 2 && Integer.parseInt(splitInput[1]) <= 4) {
                                    send(new NewMatchRequest(Integer.parseInt(splitInput[1])));
                                    CLIClientViewController.getLock().wait();
                                } else view.updatePageState(new CLILobbyPage.InvalidInput());
                            } catch (NumberFormatException nan) {
                                view.updatePageState(new CLILobbyPage.InvalidInput());
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }

                        } else if (splitInput[0].equalsIgnoreCase("quit")) {
                            send(new QuitRequest());
                            client.setActive(false);
                            break;
                        } else view.updatePageState(new CLILobbyPage.InvalidInput());
                        view.displayPage(new String[]{input});
                    } while (client.isNotInMatch());
                } catch (NullPointerException e) {
                    close();
                    exceptionHandler.handle(e);
                }
            }
        }
    }

    public void waitingRoom() {
        synchronized (getLock()) {
            if (client.isActive()) {
                view.setPage(new CLIWaitingPage());
                view.displayPage(new Object[]{matchId});
                try {
                    getLock().wait();
                } catch (InterruptedException e) {
                }
            }
        }
    }


    @Override
    public void game() {
        Thread terminalReader = asyncReadFromTerminal();
        terminalReader.start();
        try {
            terminalReader.join();
        } catch (InterruptedException e) {
            client.setActive(false);
            close();
            exceptionHandler.handle(e);
            //match is Terminated
        }

    }

    /**
     * This asynchronous method is run by a separated thread to receive asynchronous requests from user command line (commands)
     * This method uses CommandParser class' static method parse(String input) to figure out the command given and reacts by invoking
     * the handler method "..."
     *
     * @return the thread to be run
     */
    public Thread asyncReadFromTerminal() {
        return new Thread(() -> {
            Request futureRequest;
            while (client.isActive()) {
                try {
                    Scanner in = new Scanner(System.in);
                    String input = in.nextLine();

                    futureRequest = InputParser.parse(input);

                    CLICommand.restoreCursorPosition();
                    CLICommand.clearLineAfterCursor();

                    if (futureRequest != null) {
                        Thread t = asyncWriteToSocket(futureRequest);
                        t.start();
                        t.join();
                    }
                } catch (Exception e) {
                    close();
                    exceptionHandler.handle(e);
                }
            }
        });
    }
}


