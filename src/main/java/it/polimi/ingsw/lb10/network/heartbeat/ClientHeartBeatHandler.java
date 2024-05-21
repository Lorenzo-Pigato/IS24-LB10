package it.polimi.ingsw.lb10.network.heartbeat;
import it.polimi.ingsw.lb10.client.controller.ClientViewController;
import it.polimi.ingsw.lb10.client.exception.ConnectionErrorException;
import it.polimi.ingsw.lb10.client.exception.ConnectionTimedOutException;
import it.polimi.ingsw.lb10.client.exception.ExceptionHandler;
import it.polimi.ingsw.lb10.network.requests.preMatch.PingRequest;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClientHeartBeatHandler {

    private static ClientHeartBeatHandler instance;
    private static ClientViewController clientViewController;
    private static int counter = 0;
    static ScheduledExecutorService es;

    public static ClientHeartBeatHandler instance(){
        if(instance == null) {
            instance = new ClientHeartBeatHandler();
        }
        return instance;
    }

    public static void setController(ClientViewController controller){
        clientViewController = controller;
    }

    public static void start(){

        es = Executors.newSingleThreadScheduledExecutor();
        es.scheduleAtFixedRate(() -> {
            try{
                clientViewController.send(new PingRequest());
                incrementCounter();
                if (counter > 10) {ClientHeartBeatHandler.
                    clientViewController.getClient().setActive(false);
                    clientViewController.close();
                    throw new ConnectionTimedOutException();
                }
            }catch(ConnectionTimedOutException e){
                clientViewController.getExceptionHandler().handle(e);
            }
        }, 0, 1,  TimeUnit.SECONDS);
    }

    public static synchronized void incrementCounter(){
        counter ++;
    }

    public static synchronized void decrementCounter(){ counter = 0; }

    public static void stop(){
        es.shutdown();
    }
}
