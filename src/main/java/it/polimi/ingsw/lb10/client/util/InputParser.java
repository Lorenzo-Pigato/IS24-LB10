package it.polimi.ingsw.lb10.client.util;

import it.polimi.ingsw.lb10.client.cli.clipages.CLIMatchPage;
import it.polimi.ingsw.lb10.client.controller.CLIClientViewController;
import it.polimi.ingsw.lb10.network.requests.QuitRequest;
import it.polimi.ingsw.lb10.network.requests.Request;
import it.polimi.ingsw.lb10.network.requests.match.ChatRequest;
import it.polimi.ingsw.lb10.network.requests.match.PlaceStartingCardRequest;
import it.polimi.ingsw.lb10.network.requests.match.ShowPlayerRequest;

import java.util.Arrays;

public class InputParser {

    public static CLIClientViewController controller = CLIClientViewController.instance();
    /**
     * This method parses all user inputs via command line, possible inputs :
     * 1. <help> prints out all possible commands
     * 2. <flip <card id>> flips requested card
     * 3. <show <player>> shows requested player board
     * 4. <place <card id> <...>> places card on the board
     * 5. <> card drawing will be procedural
     * 6. <chat <...>> sends a message to chat
     * 7. <quit> quits match
     * @param input user input to be parsed
     */
    public static Request parse(String input){
        Request result = null;

        String[] parsed = input.split(" ");

        if (parsed[0].equalsIgnoreCase( "chat")){
            String message = input.substring(5);
            result = new ChatRequest(controller.getMatchId(), message);
        }else if(parsed.length == 3){ // 4

        }else if(parsed.length == 2){

            if(parsed[0].equals("flip") && controller.getView().getPage().getClass().equals(CLIMatchPage.class)){
                if(parsed[1].equals("1")){
                    controller.flipCard(0);
                    CLIMatchPage.flipCard(0, controller.getHand().get(0));

                }
                else if(parsed[1].equals("2")){
                    controller.flipCard(1);
                    CLIMatchPage.flipCard(1, controller.getHand().get(1));
                }
                else if(parsed[1].equals("3")){
                    controller.flipCard(2);
                    CLIMatchPage.flipCard(2, controller.getHand().get(2));
                }
                else if(parsed[1].equals("s")){
                    controller.flipStarting();
                    CLIMatchPage.StartingTurn.flipStartingCard(controller.getStartingCard());
                }
                else{
                    CLIMatchPage.serverReply("You can flip cards in range [1] to [3] or 'S'");
                }
            }

            if(parsed[0].equalsIgnoreCase("show") && !parsed[1].isEmpty()){
                result = new ShowPlayerRequest(controller.getMatchId(), parsed[1]); // 3. <show <player>> shows requested player board
            }

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

        }


        return result;
    }
}

