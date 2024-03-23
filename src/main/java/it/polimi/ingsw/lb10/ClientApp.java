package it.polimi.ingsw.lb10;

import it.polimi.ingsw.lb10.client.Client;
import it.polimi.ingsw.lb10.client.controller.ClientViewController;
import it.polimi.ingsw.lb10.client.view.CLIClientView;
import it.polimi.ingsw.lb10.client.view.ClientView;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientApp {
    private static Client client;

    //shows first output, asks for CLI/GUI, port, ip and builds the client(Socket socket, int port, String ip)
    //checks the IOException while creating socket, the client has to run independently
    public static void main( String[] args )
    {
        System.out.println(args[1]);

    }

    public static String showConfig(){
        return null;
    }

    public static boolean parseConfig(String config){

        return true;
    }

    private static boolean isValidIP(String split){
            String ipv4Pattern = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
            Pattern pattern = Pattern.compile(ipv4Pattern);
            Matcher matcher = pattern.matcher(split);
            return matcher.matches();
    }

    private static boolean isValidPort(String port){
        try{
            int portNumber = Integer.parseInt(port);
            return portNumber >= 1024 && portNumber <= 65535;
        }catch(NumberFormatException e){
            return false;
        }
    }


}
