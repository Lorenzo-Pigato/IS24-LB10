package it.polimi.ingsw.lb10.client.util;

import it.polimi.ingsw.lb10.client.clidesign.CLICommand;
import it.polimi.ingsw.lb10.client.clidesign.CLIString;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This class is used to verify whether input matches expected parameters
 * Warning: cursor should be previously set where user input is expected
 */
public abstract class InputVerifier {
    public static String verify(String[] expected, CLIString message, CLIString errorMessage, boolean atCenter) {
        CLICommand.saveCursorPosition();

        if(atCenter) message.centerPrint();
        else message.print();

        CLICommand.restoreCursorPosition();
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();

        while(Arrays.stream(expected).noneMatch(input::equalsIgnoreCase)) {
            CLIString.replace(message, errorMessage);
            CLICommand.restoreCursorPosition();
            CLICommand.clearLineAfterCursor();

            input = in.nextLine();
        }

        CLICommand.restoreCursorPosition();
        CLICommand.clearLineAfterCursor();

        return input;

    }

}
