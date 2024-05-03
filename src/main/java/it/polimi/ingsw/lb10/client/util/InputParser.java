package it.polimi.ingsw.lb10.client.util;

import it.polimi.ingsw.lb10.client.cli.clipages.CLIMatchPage;
import it.polimi.ingsw.lb10.client.controller.CLIClientViewController;
import it.polimi.ingsw.lb10.network.requests.match.MoveBoardRequest;
import it.polimi.ingsw.lb10.network.requests.QuitRequest;
import it.polimi.ingsw.lb10.network.requests.Request;
import it.polimi.ingsw.lb10.network.requests.match.*;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;
import org.jetbrains.annotations.Contract;

public class InputParser {

    public static CLIClientViewController controller = CLIClientViewController.instance();


    public static Request parse(String input){
        String[] parsed = input.split(" ");

        if (parsed[0].equalsIgnoreCase( "chat") && !parsed[1].isEmpty()) {
            String message = input.substring(5);
            return new ChatRequest(controller.getMatchId(), message);
        }

       switch(parsed.length){
            case 1 : return parseOneWordCommand(parsed);
            case 2 : return parseTwoWordsCommand(parsed);
            case 3 : return parseThreeWordCommand(parsed);
            case 4 : return parseFourWordsCommand(parsed);
           default : return null;
        }
    }

    private static Request parseThreeWordCommand(String[] parsed) {

        switch (parsed[0]){
            case "move" : {
                if(parsed.length == 3 && isValidNumber(parsed[1]) && isValidNumber(parsed[2])){
                    return new MoveBoardRequest(controller.getMatchId(), Integer.parseInt(parsed[1]), Integer.parseInt(parsed[2]));
                }else{
                return null;
                }

            }
            default : CLIMatchPage.serverReply("Invalid command, type <help> to see all commands");
        }
        return null;
    }

    private static Request parseFourWordsCommand(String[] parsed) {
        switch (parsed[0]){
            case "place" : {
                if(!(!isValidHandCard(parsed[1]) || !isValidPosition(parsed[2]) || !isValidNumber(parsed[3]))) {
                    return new PlaceCardRequest(controller.getMatchId(), controller.getHand().get(Integer.parseInt(parsed[1]) - 1), parsePosition(parsed[2]), Integer.parseInt(parsed[3]));
                }
                else {
                    CLIMatchPage.serverReply("Invalid card placement, type <help> to see all commands");
                    return null;
                }
            }
            default:
                CLIMatchPage.serverReply("Invalid command, type <help> to see all commands");
        }
        return null;
    }

    private static Request parseTwoWordsCommand(String[] parsed) {
        switch(parsed[0]) {
            case ("flip") -> {
                switch (parsed[1]) {

                    case "1" -> {
                        controller.flipCard(0);
                        CLIMatchPage.flipCard(0, controller.getHand().getFirst());
                    }
                    case "2" -> {
                        controller.flipCard(1);
                        CLIMatchPage.flipCard(1, controller.getHand().get(1));
                    }
                    case "3" -> {
                        controller.flipCard(2);
                        CLIMatchPage.flipCard(2, controller.getHand().get(2));
                    }
                    case "s" -> {
                        if (!controller.startingCardHasBeenPlaced()) { //prevents from re drawing starting card inside table
                            controller.flipStarting();
                            CLIMatchPage.StartingTurn.flipStartingCard(controller.getStartingCard());
                        } else CLIMatchPage.serverReply("Starting card has already been placed");
                    }
                    default -> CLIMatchPage.serverReply("You can flip cards in range [1] to [3] or 'S'");
                }
            }

            case ("place") -> {
                if(!controller.startingCardHasBeenPlaced() && parsed[1].equalsIgnoreCase("s")) {
                    controller.setStartingCardHasBeenPlaced(true);
                    return new PlaceStartingCardRequest(controller.getMatchId(), controller.getStartingCard());
                }
            }

            case ("pick") -> {
                switch(parsed[1]) {
                    case "1" : return new DrawGoldenFromTableRequest(controller.getMatchId(), 0);
                    case "2" : return new DrawGoldenFromTableRequest(controller.getMatchId(), 1);
                    case "3" : return new DrawResourceFromTableRequest(controller.getMatchId(), 0);
                    case "4" : return new DrawResourceFromTableRequest(controller.getMatchId(), 1);
                    case "5" : return new DrawGoldenFromDeckRequest(controller.getMatchId());
                    case "6" : return new DrawResourceFromDeckRequest(controller.getMatchId());
                    default  : {
                        CLIMatchPage.serverReply("Invalid picking id, choose between [1] and [6]");
                        return null;
                    }
                }
            }

            default -> {
                CLIMatchPage.serverReply("Invalid command, type <help> to see all commands...");
                return null;
            }
        }
        return null;
    }

    private static Request parseOneWordCommand(String[] parsed) {
        if(parsed[0].equalsIgnoreCase("quit")){
            return new QuitRequest();
        }else if(parsed[0].equalsIgnoreCase("help")){
            //help
            return null;
        }else{
            CLIMatchPage.serverReply("Invalid command, please retry or type <help> to see al commands...");
            return null;
        }
    }

    private static boolean isValidHandCard(String input){
        boolean result = false;
        try{
            result = Integer.parseInt(input) <= 3 && Integer.parseInt(input) >= 1;
        }catch (NumberFormatException e){
            return false;
        }
        return result;
    }

    private static boolean isValidPosition(String input){
        return input.equalsIgnoreCase("tl") || input.equalsIgnoreCase("tr") || input.equalsIgnoreCase("bl") || input.equalsIgnoreCase("br");
    }

    private static Position parsePosition(String input){
        return switch (input) {
            case ("tl") -> Position.TOPLEFT;
            case ("tr") -> Position.TOPRIGHT;
            case ("bl") -> Position.BOTTOMLEFT;
            case ("br") -> Position.BOTTOMRIGHT;
            default -> null;
        };
    }

    @Contract(pure = true)
    private static boolean isValidNumber(String input){
        try{
            Integer.parseInt(input);
        }catch(NumberFormatException e){
            return false;
        }
        return true;
    }
}

