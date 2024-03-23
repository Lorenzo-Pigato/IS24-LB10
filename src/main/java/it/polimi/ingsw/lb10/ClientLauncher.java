package it.polimi.ingsw.lb10;

import it.polimi.ingsw.lb10.client.Client;
import it.polimi.ingsw.lb10.client.clidesign.clipages.CLI404Page;
import it.polimi.ingsw.lb10.client.clidesign.clipages.CLIConnectionPage;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientLauncher {
    private static Client client;

    //shows first output, asks for CLI/GUI, port, ip and builds the client(Socket socket, int port, String ip)
    //checks the IOException while creating socket, the client has to run independently
    public static void main( String[] args )
    {
        if(args[1].equals("cli")){
            CLIConnectionPage.display();

            Scanner in = new Scanner(System.in);
            String input;

            do{
                input = in.nextLine();

                if(input.split(":").length != 2)
                    CLIConnectionPage.invalidInput();
                else if(!isValidIP(input.split(":")[0]) && !isValidPort(input.split(":")[1]))
                    CLIConnectionPage.invalidInput();
                else if(!isValidIP(input.split(":")[0]))
                    CLIConnectionPage.invalidIp();
                else if(!isValidPort(input.split(":")[1]))
                    CLIConnectionPage.invalidPort();


            } while(input.split(":").length != 2 ||
                    !isValidIP(input.split(":")[0]) ||
                    !isValidPort(input.split(":")[1])
                    );

            CLI404Page.display404Page();
            input = in.nextLine();
        }

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
