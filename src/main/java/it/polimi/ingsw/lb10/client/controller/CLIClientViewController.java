package it.polimi.ingsw.lb10.client.controller;

import it.polimi.ingsw.lb10.client.Client;
import it.polimi.ingsw.lb10.client.cli.clipages.CLIConnectionPage;
import it.polimi.ingsw.lb10.client.cli.clipages.CLILoginPage;
import it.polimi.ingsw.lb10.client.exception.ConnectionErrorException;
import it.polimi.ingsw.lb10.client.exception.ExceptionHandler;
import it.polimi.ingsw.lb10.client.view.CLIClientView;
import it.polimi.ingsw.lb10.network.requests.*;
import it.polimi.ingsw.lb10.network.requests.preMatch.LoginRequest;
import it.polimi.ingsw.lb10.network.response.*;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.CLIResponseHandler;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CLIClientViewController implements ClientViewController{

    private static CLIClientViewController instance;
    private  CLIClientView view;
    private Socket socket;
    private Client client;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;
    private int hash;
    private static final CLIResponseHandler responseHandler = CLIResponseHandler.instance();


   public static CLIClientViewController instance(){
       if(instance == null) instance = new CLIClientViewController();
       return instance;
   }

    // ------------------ SETTERS ------------------ //
    public void setCliClientView(CLIClientView cliClientView){this.view = cliClientView;}
    @Override
    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public Socket getSocket() {return socket;}
    public Client getClient() {return client;}

    // ------------------- UTILS ------------------- //
    @Override
    public void close() {
        try{
            socketIn.close();
            socketOut.close();
            socket.close();
        }catch(IOException e){
            ExceptionHandler.handle(e, view);
        }finally{
            client.setActive(false);
        }
    }

    protected boolean isNotValidIP(String split){
        String ipv4Pattern ="^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        Pattern pattern = Pattern.compile(ipv4Pattern);
        Matcher matcher = pattern.matcher(split);
        return !matcher.matches();
    }

    protected boolean isNotValidPort(String port){
        try{
            int portNumber = Integer.parseInt(port);
            return portNumber < 1024 || portNumber > 65535;
        }catch(NumberFormatException e){
            return true;
        }
    }

    // ------------------ METHODS ------------------ //

    /**
     * this method is the first to be run after instantiation of the controller,
     * opening socket streams to communicate with the server
     */
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

    /**
     * this method provides an input scanner for login requests, checking the string for the username
     * and sending it to the Server.
     * A Boolean Response is waited and handled , setting "logged" flag to true is response is positive
     */
    @Override
    public void login() {
        view.setPage(new CLILoginPage());
        Scanner in = new Scanner(System.in);
        String username = null;
        do {
            if ((username == null)) view.pageStateDisplay(new CLILoginPage.Default(), null);
            else view.pageStateDisplay(new CLILoginPage.alreadyTaken(), new String[]{username});
            do {
                username = in.nextLine();
                if (username.length() < 2 || username.length() > 15)
                    view.pageStateDisplay(new CLILoginPage.invalidLength(), new String[]{username});
            } while (username.length() < 2 || username.length() > 15);

            send(new LoginRequest(hash, username));

            syncReceive().accept(responseHandler);

        }while(!client.isLogged());

    }

    public void send(Request request){
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
        }catch( Exception e){
            ExceptionHandler.handle(e, view);
            close();
        }
        return response;
    }

    // --------------- ASYNC IO HANDLING ------------- //

    @Override
    public void showUserOutput(Object o) {
        Thread viewChanger = asyncWriteToTerminal();
        viewChanger.start();
    }

    @Override
    public void getUserInput() {
        Thread userListener = asyncReadFromTerminal();
        userListener.start();
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
                    Response input = (Response)socketIn.readObject();
                    // REACTS!!!
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
        return new Thread(() -> {
            send(message);
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

                } catch (Exception e) {
                    ExceptionHandler.handle(e, view);
                }finally {
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
        view.setPage(new CLIConnectionPage());
        Scanner in = new Scanner(System.in);
        String input;
        String[] parsed;
        //x.y.z.w:k

        view.pageStateDisplay(new CLIConnectionPage.Default(), null);

        do{
            input = in.nextLine();
            parsed = input.split(":");
            if(parsed.length != 2 ||
                     isNotValidIP(parsed[0]) && isNotValidPort(parsed[1])){ //invalid input, none of the fields is correct (ip:port)
                view.pageStateDisplay(new CLIConnectionPage.InvalidInput(), new String[] {input});

            } else if (isNotValidIP(parsed[0])) {
                view.pageStateDisplay(new CLIConnectionPage.InvalidIP(), new String[] {input});

            } else if (isNotValidPort(parsed[1])) {
                view.pageStateDisplay(new CLIConnectionPage.InvalidPort(), new String[] {input});
            }
        }while(parsed.length != 2 || isNotValidPort(parsed[1]) || isNotValidIP(parsed[0]));

        try {
            cliSocket = new Socket(parsed[0], Integer.parseInt(parsed[1]));
        } catch (Exception e) {
            throw new ConnectionErrorException();
        }
        setSocket(cliSocket);
    }

    @Override
    public void setHash() {
        try{
            HashResponse hashResponse = (HashResponse) socketIn.readObject();
            this.hash = hashResponse.getHash();
        }catch(Exception e){
            ExceptionHandler.handle(e,view);
        }
    }
}


