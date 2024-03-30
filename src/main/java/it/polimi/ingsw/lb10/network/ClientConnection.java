package it.polimi.ingsw.lb10.network;

import it.polimi.ingsw.lb10.network.requests.Request;
import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.view.RemoteView;
import it.polimi.ingsw.lb10.server.visitors.requestDispatch.RequestHandler;
import it.polimi.ingsw.lb10.server.visitors.responseSender.ResponseSender;
import it.polimi.ingsw.lb10.util.Observable;
import it.polimi.ingsw.lb10.server.Server;
import it.polimi.ingsw.lb10.server.controller.MatchController;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnection extends Observable<Request> implements Runnable {

    private Socket socket;
    private ObjectInputStream input;
    private Boolean active = true;
    private Server server;
    private MatchController matchController;
    private final RemoteView remoteView;
    private final RequestHandler requestHandler ;


    public ClientConnection(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
        this.remoteView = new RemoteView(socket);
        requestHandler = new RequestHandler(remoteView);
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean isActive() {
        return active;
    }

    public void close(){
        try{
            input.close();
        }catch(IOException e){
            close();
        }
    }

    public void run(){
        while(isActive()){
            try{
                Request request = (Request) (input.readObject());
                request.accept(requestHandler); /***/
                /** c'è un chiaro problema nella reazione alle richieste :
                 * non si puo distinguere in questo caso tra richieste preMatch( da rifilare al lobbyController) e richieste inMatch( da rifilare al matchController specifico)
                 * volendo essere in grado di iscrivere le richieste all'interno della BlockingQueue del controller del match per poterle servire velocemente con un thread,
                 * serve che il controller cambi dinamicamente all'interno della client connection
                 * Connessione -> Login -> Scelta partita -> [cambio di controller] -> Gestione richieste
                 * a questo proposito si dovranno fare due modifiche al codice :
                 *  1. cavare il RequestHandler e fare diventare i Controller stessi dei RequestHandler, implementando le interfacce
                 *  2. Creare una classe controller generica che venga estesa dai due controller.
                 *  Bisogna ora capire come fare in modo che la ClientConnection cambi stato (Da Lobby a Match)
                 *  tipo request.hasMatch() ???? instanceof???????? boh
                 *
                 * */

            }catch(Exception e){
                System.out.println(e.toString() + "occurred");
                setActive(false);
            }
            finally {
                close();
            }
        }
    }

    /**
     * Sets up the streams to communicate with client
     */
    public void setUp(){
        try{
            input = new ObjectInputStream(socket.getInputStream());
        }catch(IOException e){
            close();
        }


    }
}