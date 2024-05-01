package it.polimi.ingsw.lb10.client.util;

import it.polimi.ingsw.lb10.client.cli.clipages.CLIMatchPage;
import it.polimi.ingsw.lb10.client.controller.CLIClientViewController;
import it.polimi.ingsw.lb10.network.requests.QuitRequest;
import it.polimi.ingsw.lb10.network.requests.Request;
import it.polimi.ingsw.lb10.network.requests.match.*;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;

public class InputParser {

    public static CLIClientViewController controller = CLIClientViewController.instance();
    /**
     * This method parses all user inputs via command line, possible inputs :
     * 1. <help> prints out all possible commands
     * 2. <flip <hand card id> <tl, tr, bl, br> <matrix card id>> flips requested card
     * 3. <show <player>> shows requested player board
     * 4. <place <card id> <...>> places card on the board
     * 5. <> card drawing will be procedural
     * 6. <chat <...>> sends a message to chat
     * 7. <quit> quits match
     * @param input user input to be parsed
     */
    public static Request parse(String input){
        Request result = null;
        String errorMessage = "";

        String[] parsed = input.split(" ");

        if (parsed[0].equalsIgnoreCase( "chat") && !parsed[1].isEmpty()) {
            String message = input.substring(5);
            result = new ChatRequest(controller.getMatchId(), message);

        }else if(parsed[0].equalsIgnoreCase("place") && isValidHandCard(parsed[1]) && isValidPosition(parsed[2] )&& isValidMatrixCard(parsed[3])){
            result = new PlaceCardRequest(controller.getMatchId(), controller.getHand().get(Integer.parseInt(parsed[1])), parsePosition(parsed[2]),  Integer.parseInt(parsed[3]));

        }else if(parsed.length == 3){ // 4

        }else if(parsed.length == 2) {
            //PICK
            if(parsed[0].equalsIgnoreCase("pick") && "12345".contains(parsed[1]) && parsed[1].length() == 1){ //1, 2 ->golden, 3, 4-> res , 5 -> goldenDeck, 6 -> resourceDeck
                switch (parsed[1]){
                    case "1" -> result = new DrawGoldenFromTableRequest(controller.getMatchId(), 0);
                    case "2" -> result = new DrawGoldenFromTableRequest(controller.getMatchId(), 1);
                    case "3" -> result = new DrawResourceFromTableRequest(controller.getMatchId(), 0);
                    case "4" -> result = new DrawResourceFromTableRequest(controller.getMatchId(), 1);
                    case "5" -> result = new DrawGoldenFromDeckRequest(controller.getMatchId());
                    case "6" -> result = new DrawResourceFromDeckRequest(controller.getMatchId());
                }
            //FLIP
            }else if(parsed[0].equals("flip") && controller.getView().getPage().getClass().equals(CLIMatchPage.class)){
                switch (parsed[1]) {
                    case "1" -> {
                        controller.flipCard(0);
                        CLIMatchPage.flipCard(0, controller.getHand().get(0));
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
                        controller.flipStarting();
                        CLIMatchPage.StartingTurn.flipStartingCard(controller.getStartingCard());
                    }
                    default -> CLIMatchPage.serverReply("You can flip cards in range [1] to [3] or 'S'");
                }
            }
            //SHOW
            if(parsed[0].equalsIgnoreCase("show") && !parsed[1].isEmpty()){
                result = new ShowPlayerRequest(controller.getMatchId(), parsed[1]); // 3. <show <player>> shows requested player board
            }

            //PLACE
            if(parsed[0].equalsIgnoreCase("place") && parsed[1].equalsIgnoreCase("s") && !controller.startingCardHasBeenPlaced()){
                result = new PlaceStartingCardRequest(controller.getMatchId(), controller.getStartingCard());
                controller.setStartingCardHasBeenPlaced(true);
            }

        }else if(parsed.length == 1) {

            if (parsed[0].equalsIgnoreCase("help")) { //1. <help> prints out all possible commands
                //no request to submit to server
            }else if (parsed[0].equalsIgnoreCase("quit")) {
                result = new QuitRequest();
            }
        }else{
            CLIMatchPage.serverReply("bad command, type <help> to see all commands");
        }

        return result;
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

    private static boolean isValidMatrixCard(String input){
        try{
            Integer.parseInt(input);
        }catch(NumberFormatException e){
            return false;
        }
        return true;
    }
}

