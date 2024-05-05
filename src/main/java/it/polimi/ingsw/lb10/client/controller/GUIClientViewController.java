package it.polimi.ingsw.lb10.client.controller;

import it.polimi.ingsw.lb10.client.exception.ConnectionErrorException;
import it.polimi.ingsw.lb10.client.view.GUIClientView;
import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.network.response.match.PrivateQuestsResponse;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.GUIResponseHandler;

public class GUIClientViewController extends ClientViewController {

    private static GUIClientViewController instance;
    private GUIClientView view;

    private static final GUIResponseHandler responseHandler = GUIResponseHandler.instance();

    public static GUIClientViewController instance() {
        if (instance == null) instance = new GUIClientViewController();
        return instance;
    }

    // ------------------ SETTERS ------------------ //
    public void setGuiClientView(GUIClientView guiClientVIew) {
        this.view = guiClientVIew;
    }

    // ------------------ GETTERS ------------------ //
    public GUIClientView getView() {return view;}


    // ------------------ METHODS ------------------ //
    @Override
    public Thread asyncReadFromSocket() {
        return new Thread(() -> {
            try {
                while (client.isActive()) {
                    Response response = (Response) socketIn.readObject();
                    response.accept(responseHandler);
                }
            } catch (Exception e) {
                exceptionHandler.handle(e);
                close();
            }
        });
    }

    @Override
    public void login() {

    }

    @Override
    public void joinMatch() {

    }

    public void waitingRoom() {

    }

    @Override
    public void privateQuestSelection(PrivateQuestsResponse response) {

    }


    @Override
    public void game() {

    }



    @Override
    public void initializeConnection() throws ConnectionErrorException {
        //Socket cliSocket;

        //setSocket(cliSocket);
    }


}


