package it.polimi.ingsw.lb10.client.controller;

import it.polimi.ingsw.lb10.client.exception.ConnectionErrorException;
import it.polimi.ingsw.lb10.client.gui.GUIConnectionPage;
import it.polimi.ingsw.lb10.client.gui.GUILoginPage;
import it.polimi.ingsw.lb10.client.gui.GUIPage;
import it.polimi.ingsw.lb10.client.view.GUIClientView;
import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.network.response.match.PrivateQuestsResponse;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.GUIResponseHandler;
import javafx.application.Application;
import javafx.application.Platform;

public class GUIClientViewController extends ClientViewController {

    private static GUIClientViewController instance;
    private GUIClientView view = GUIClientView.instance();
    private GUIPage page;

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
    public GUIClientView getView() {
        return view;
    }

    // ------------------ METHODS ------------------ //
    public void changePage(GUIPage page) {
        this.page = page;
        Platform.runLater(() -> {view.setPage(page);});
    }


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

    public void fxInitialize(){
        Application.launch(GUIClientView.class);
    }

    @Override
    public void initializeConnection() throws ConnectionErrorException {
        fxInitialize();
        changePage(new GUIConnectionPage());
        synchronized(page){
            try{
                page.wait();
            } catch (InterruptedException e){
                exceptionHandler.handle(e);
            }
        }
    }

    @Override
    public void login() {
        if (client.isActive()) {
            changePage(new GUILoginPage());
        }
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

}


