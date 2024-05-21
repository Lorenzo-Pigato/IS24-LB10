package it.polimi.ingsw.lb10.network.heartbeat;

import it.polimi.ingsw.lb10.network.requests.preMatch.LobbyRequest;
import it.polimi.ingsw.lb10.network.requests.preMatch.PingRequest;
import it.polimi.ingsw.lb10.network.response.PingResponse;
import it.polimi.ingsw.lb10.network.response.PongResponse;
import it.polimi.ingsw.lb10.server.Server;
import it.polimi.ingsw.lb10.server.controller.LobbyController;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerHeartBeatHandler {

    private int counter = 0;
    private  int hashcode;
    private  ScheduledExecutorService es;

    public ServerHeartBeatHandler(int hashcode) {
        this.hashcode = hashcode;
    }


    public synchronized void incrementCounter(){
        counter ++;
    }

    public synchronized void decrementCounter(){counter = 0;}

    public void start(){
        es = Executors.newSingleThreadScheduledExecutor();
        es.scheduleAtFixedRate(() -> {
            LobbyController.send(hashcode, new PingResponse());
            incrementCounter();
            if(counter > 10){
                LobbyController.disconnectClient(hashcode);
            }
        }, 0, 1,  TimeUnit.SECONDS);
    }

    public int getHashcode(){
        return hashcode;
    }

    public synchronized void close(){
        es.shutdownNow();
    }

}

