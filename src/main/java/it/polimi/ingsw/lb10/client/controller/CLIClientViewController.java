package it.polimi.ingsw.lb10.client.controller;

import it.polimi.ingsw.lb10.client.Client;
import it.polimi.ingsw.lb10.client.view.CLIClientView;
import it.polimi.ingsw.lb10.network.Request;
import it.polimi.ingsw.lb10.network.Response;
import it.polimi.ingsw.lb10.util.CommandParser;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class CLIClientViewController implements ClientViewController{

    private CLIClientView cliClientView;
    private Socket socket;
    private Client client;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;


    public CLIClientViewController(CLIClientView cliClientView, Socket socket, Client client) {
        this.cliClientView = cliClientView;
        this.socket = socket;
    }

    /**
     * This method is called to set up the communication environment between the client and all the possible streams in the
     * application : the Objs in/out stream via socket (Message requests) and the TUI streams.
     * After the setup all the listening threads are launched, every thread handles the closing phase of the streams when communication ends.
     */
    public void launch(){

        setUp();
        Thread waiter = new Thread(() -> {
            Thread socketReader = asyncReadFromSocket();
            Thread terminalReader = asyncReadFromTerminal();

            terminalReader.start();
            socketReader.start();

            try{
                terminalReader.join();
                socketReader.join();

            }catch(Exception e){
                //handle
            }finally{
                try{
                    socketIn.close();
                    socketOut.close();
                }catch(IOException e){
                    //handle
                }
            }
        });
        waiter.start();
    }

    /**
     *  This asynchronous method is run by a separated thread to receive asynchronous requests from user command line (commands)
     * This method uses CommandParser class' static method parse(String input) to figure out the command given and reacts by invoking
     * the handler method "..."
     */
    public Thread asyncReadFromSocket(){
        return new Thread(() -> {
            try{
                while(client.isActive()){
                    Response input = (Response) socketIn.readObject();
                    // REACTS!!!
                }
            }catch(Exception e){
                //handle
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
        return new Thread(() -> {
            try{
                socketOut.reset();
                socketOut.writeObject(message);
                socketOut.flush();
            }catch(Exception e){
                //handle
            }
        });
    }

    /**
     * This asynchronous method is run by a separated thread to print the result of the view's state change on the TUI
     * @param message the message to print
     * @return the thread to be run
     */
    public Thread asyncWriteToTerminal(String message){
        return new Thread(() -> {

        });
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
            while (client.isActive()) {
                try {
                    String input = in.nextLine();
                    Response command = CommandParser.parse(input);
                    // REACTS!!!
                } catch (Exception e) {
                    //handle
                }
            }
        });
    }

    public void setUp(){
        try{
            socketIn = new ObjectInputStream(socket.getInputStream());
        }catch(IOException e){
            System.out.println("Error creating Socket Input Stream...");
        }
        try{
            socketOut = new ObjectOutputStream(socket.getOutputStream());
        }catch(IOException e){
            System.out.println("Error creating Socket Output Stream...");
        }
    }
}
