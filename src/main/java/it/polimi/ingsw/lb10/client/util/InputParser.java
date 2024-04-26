package it.polimi.ingsw.lb10.client.util;

import it.polimi.ingsw.lb10.network.requests.Request;
import it.polimi.ingsw.lb10.network.requests.match.ChatRequest;
import it.polimi.ingsw.lb10.network.requests.match.ShowPlayerRequest;

public class InputParser {

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
        if(parsed.length == 3){ // 4

        }else if(parsed.length == 2){


            if(parsed[0].equals("chat") && !parsed[1].isEmpty()){ // 6. <chat <...>> sends a message to chat
                result = new ChatRequest(0, parsed[1]);
            }

            if(parsed[0].equals("show") && !parsed[1].isEmpty()){
                result = new ShowPlayerRequest(0, parsed[1]); // 3. <show <player>> shows requested player board
            }


        }else if(parsed.length == 1){


            if(parsed[0].equals("help")){ //1. <help> prints out all possible commands
                //no request to submit to server
            }


        }else{

        }

        return result;
    }
}

