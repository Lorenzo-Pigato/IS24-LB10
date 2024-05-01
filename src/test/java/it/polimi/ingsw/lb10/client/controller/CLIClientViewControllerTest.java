package it.polimi.ingsw.lb10.client.controller;

import it.polimi.ingsw.lb10.client.Client;
import it.polimi.ingsw.lb10.client.exception.ConnectionErrorException;
import it.polimi.ingsw.lb10.client.exception.ExceptionHandler;
import it.polimi.ingsw.lb10.client.util.InputVerifier;
import it.polimi.ingsw.lb10.client.view.CLIClientView;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class CLIClientViewControllerTest {

     static CLIClientViewController controller = CLIClientViewController.instance();
     static ServerSocket serverSocket;
     static Socket localSocket;
     static ObjectOutputStream outputStream;
     static Client client = Client.instance();

     //@AfterAll
     static void close(){
         try{
             outputStream.close();
             localSocket.close();
             serverSocket.close();
         }catch (Exception e){
             ExceptionHandler.handle(e, new CLIClientView());
         }

     }


    @BeforeAll
    static void initialize() {
        controller = CLIClientViewController.instance();
        controller.setCliClientView(new CLIClientView());
        try {
            serverSocket = new ServerSocket(3557);
            Thread listener = new Thread(new Runnable() {
                @Override
                public void run() {

                    try {

                        localSocket = serverSocket.accept();
                        outputStream = new ObjectOutputStream(localSocket.getOutputStream());
                        outputStream.flush();


                    } catch (Exception e) {ExceptionHandler.handle(e, new CLIClientView());}
                }
            });
            listener.start();
            Thread.sleep(1000);
        } catch (Exception e) {}

    }




    @ParameterizedTest
    @ValueSource(strings = {"128.0.-3.3", "280.0.213.230", "255.255.255.abs", "random", "-2.-3.42.43", "1.4", ""})

    public void testNotValidIp(String ip){

        assertTrue(InputVerifier.isNotValidIP(ip));
    }

    @ParameterizedTest
    @ValueSource(strings = {"127.0.0.1", "255.255.255.255", "10.1.1.2", "35.3.42.5", "255.0.5.3", "1.1.1.1", "10.4.3.2"})

    public void testValidIp(String ip){

        assertFalse(InputVerifier.isNotValidIP(ip));

    }
    @ParameterizedTest
    @ValueSource(strings = {"1025", "2004", "65535"})
    public void testValidPort(String port){

        assertFalse(InputVerifier.isNotValidPort(port));

    }

    @ParameterizedTest
    @ValueSource(strings = {"1020", "0", "65536", "-1"})
    public void testNotValidPort(String port){

        assertTrue(InputVerifier.isNotValidPort(port));

    }
}