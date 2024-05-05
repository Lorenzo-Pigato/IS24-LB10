package it.polimi.ingsw.lb10.client.util;

import it.polimi.ingsw.lb10.client.cli.CLICommand;
import it.polimi.ingsw.lb10.client.cli.CLIString;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is used to verify whether input matches expected parameters
 * Warning: cursor should be previously set where user input is expected
 */
public abstract class InputVerifier {

    public static String verify(String[] expected, CLIString message, CLIString errorMessage, boolean atCenter) {
        CLICommand.saveCursorPosition();

        if (atCenter) message.centerPrint();
        else message.print();

        CLICommand.restoreCursorPosition();
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();

        while (Arrays.stream(expected).noneMatch(input::equalsIgnoreCase)) {
            CLIString.replace(message, errorMessage);
            CLICommand.clearUserInput(input);
            CLICommand.restoreCursorPosition();

            input = in.nextLine();
        }

        CLICommand.clearUserInput(input);
        CLICommand.restoreCursorPosition();

        return input;

    }

    public static boolean isNotValidIP(String ip) {
        String ipv4Pattern = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        Pattern pattern = Pattern.compile(ipv4Pattern);
        Matcher matcher = pattern.matcher(ip);
        return !matcher.matches();
    }

    public static boolean isNotValidPort(String port) {
        try {
            int portNumber = Integer.parseInt(port);
            return portNumber < 1024 || portNumber > 65535;
        } catch (NumberFormatException e) {
            return true;
        }
    }

}
