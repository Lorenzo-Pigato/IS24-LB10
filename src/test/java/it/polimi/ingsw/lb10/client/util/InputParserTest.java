package it.polimi.ingsw.lb10.client.util;

import it.polimi.ingsw.lb10.client.Client;
import it.polimi.ingsw.lb10.client.controller.CLIClientViewController;
import it.polimi.ingsw.lb10.network.requests.match.ChatRequest;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class InputParserTest {


    @BeforeAll
    public static void setUp() {
        InputParser.controller.setMatchId(0);
        InputParser.controller.setClient(Client.instance());
        InputParser.controller.getClient().setActive(true);

    }

    @Test
    public void chatCommandTest(){
        setUp();
        ArrayList<String> messages = new ArrayList<String>();
        messages.add("chat hello");
        messages.add("chat hi guys");
        messages.add("chat hi everyone, how are you?");
        for(String message : messages ){
            assertEquals(ChatRequest.class, InputParser.parse(message).getClass());
        }
    }

    @Test
    public void invalidCommandTest(){
        setUp();
        ArrayList<String> messages = new ArrayList<>();
        messages.add("place my card");
        messages.add("chatmessage hi!");
        messages.add("pickcard 2");
        messages.add("flip starting card");
        messages.add("flip ending card");
        messages.add("place 15 cl 90");
        for(String message : messages ){
            assertNull(InputParser.parse(message));
        }
    }
}