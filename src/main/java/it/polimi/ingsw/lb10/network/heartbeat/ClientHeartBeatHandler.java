package it.polimi.ingsw.lb10.network.heartbeat;
import it.polimi.ingsw.lb10.client.controller.ClientViewController;
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
        if(instance == null) return new ClientHeartBeatHandler();
        else return instance;
    }

    public static void setController(ClientViewController controller){
        clientViewController = controller;
    }

    public static void start(){
        es = Executors.newSingleThreadScheduledExecutor();
        es.scheduleAtFixedRate(() -> {
            clientViewController.send(new PingRequest());
            incrementCounter();
            if(counter > 10) {
                clientViewController.getClient().setActive(false);
                es.close();
                clientViewController.close();
            }
        }, 0, 1,  TimeUnit.SECONDS);
    }

    public static synchronized void incrementCounter(){
        counter ++;
    }

    public static synchronized void decrementCounter(){
        counter --;
    }

    public static void close(){
        es.close();
    }
}
