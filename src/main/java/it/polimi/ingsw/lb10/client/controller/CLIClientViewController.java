package it.polimi.ingsw.lb10.client.controller;

import it.polimi.ingsw.lb10.client.Client;
import it.polimi.ingsw.lb10.client.cli.clipages.CLI404Page;
import it.polimi.ingsw.lb10.client.cli.clipages.CLIConnectionPage;
import it.polimi.ingsw.lb10.client.cli.clipages.CLIErrorPage;
import it.polimi.ingsw.lb10.client.exception.ConnectionErrorException;
import it.polimi.ingsw.lb10.client.view.CLIClientView;
import it.polimi.ingsw.lb10.network.requests.Request;
import it.polimi.ingsw.lb10.network.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CLIClientViewController implements ClientViewController{

    private final CLIClientView view;
    private Socket socket;
    private Client client;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;

    // ---------------- CONSTRUCTOR ---------------- //
    public CLIClientViewController(CLIClientView cliClientView) {
        this.view = cliClientView;
    }

    // ------------------ SETTERS ------------------ //
    @Override
    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    // ------------------ METHODS ------------------ //
    @Override
    public void close() {
        try{
            socketIn.close();
            socketOut.close();
        }catch(IOException e){
            view.setPage(new CLIErrorPage());
            view.pageStateDisplay(new CLIErrorPage.Default(), new String[] {">> Error closing sockets <<", e.getMessage()});
        }finally{
            client.setActive(false);
        }

    }

    /**
     * this method is the first to be run after instantiation of the controller,
     * opening socket streams to communicate with the server
     */
    @Override
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
     *  This asynchronous method is run by a separated thread to receive asynchronous requests from user command line (commands)
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
                //handle
            }
            finally {
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
            try{
                socketOut.reset();
                socketOut.writeObject(message);
                socketOut.flush();
            }catch(Exception e){
                //handle
            }finally{
                close();
            }
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
                    //Response command = CommandParser.parse(input);
                    // REACTS!!!
                } catch (Exception e) {
                    e.printStackTrace();
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
    public Socket initializeConnection() throws ConnectionErrorException {

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
                view.pageStateDisplay(new CLIConnectionPage.InvalidInput(), null);

            } else if (isNotValidIP(parsed[0])) {
                view.pageStateDisplay(new CLIConnectionPage.InvalidIP(), null);

            } else if (isNotValidPort(parsed[1])) {
                view.pageStateDisplay(new CLIConnectionPage.InvalidPort(), null);
            }
        }while(parsed.length != 2 || isNotValidPort(parsed[1]) || isNotValidIP(parsed[0]));

        try {
            cliSocket = new Socket(parsed[0], Integer.parseInt(parsed[1]));
        } catch (IOException e) {
            throw new ConnectionErrorException();
        }

        return cliSocket;
    }

    private boolean isNotValidIP(String split){
        String ipv4Pattern ="^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        Pattern pattern = Pattern.compile(ipv4Pattern);
        Matcher matcher = pattern.matcher(split);
        return !matcher.matches();
    }

    private boolean isNotValidPort(String port){
        try{
            int portNumber = Integer.parseInt(port);
            return portNumber < 1024 || portNumber > 65535;
        }catch(NumberFormatException e){
            return true;
        }
    }

    @Override
    public void handleConnectionError() {
        view.setPage(new CLI404Page());
        view.pageStateDisplay(new CLI404Page.Default(), null);
    }
}
