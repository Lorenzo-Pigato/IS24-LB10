package it.polimi.ingsw.lb10.client.controller;

import it.polimi.ingsw.lb10.client.Client;
import it.polimi.ingsw.lb10.client.exception.ConnectionErrorException;
import it.polimi.ingsw.lb10.client.exception.ExceptionHandler;
import it.polimi.ingsw.lb10.client.exception.GUIExceptionHandler;
import it.polimi.ingsw.lb10.client.gui.GUIConnectionPageController;
import it.polimi.ingsw.lb10.client.gui.GUIPageController;
import it.polimi.ingsw.lb10.network.heartbeat.ClientHeartBeatHandler;
import it.polimi.ingsw.lb10.server.model.Player;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.GUIResponseHandler;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


public class GUIClientViewController extends ClientViewController {

    private static GUIClientViewController instance;
    private GUIPageController page;
    private Stage stage;
    private Scene scene;
    private final ExceptionHandler exceptionHandler = new GUIExceptionHandler(this);
    private ArrayList<Player> players;
    private boolean boundsSocket = false;

    public boolean isBoundsSocket() {
        return boundsSocket;
    }
    public void setBoundsSocket(boolean boundsSocket) {this.boundsSocket = boundsSocket;}

    public static GUIClientViewController instance() {
        if (instance == null) instance = new GUIClientViewController();
        return instance;
    }

    public void setLobbySize(){
        stage.setHeight(900);
        stage.setWidth(600);
    }

    public synchronized void changeScene(GUIPageController page) {

        FXMLLoader fxmlLoader = new FXMLLoader(GUIClientViewController.class.getResource(page.getFXML()));

        try{
            Parent root = fxmlLoader.load();
            scene.setRoot(root);
            stage.show();
        }catch (IOException e){
            exceptionHandler.handle(e);
        }
        setPage(fxmlLoader.getController());
    }

    public synchronized void initialize(Stage stage) {
        setClient(new Client());
        setExceptionHandler(new GUIExceptionHandler(this));
        setResponseHandler(GUIResponseHandler.instance());
        this.stage = stage;
        stage.setResizable(false);
        stage.setTitle("Codex Naturalis");
        stage.setWidth(600);
        stage.setHeight(900);
        stage.getIcons().add(new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/images/icon.png"))));
        stage.setOnCloseRequest(_ -> {
            client.setActive(false);
            Platform.exit();
            System.exit(0);
        });

        setPage(new GUIConnectionPageController());
        FXMLLoader fxmlLoader = new FXMLLoader(GUIClientViewController.class.getResource(page.getFXML()));

        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            exceptionHandler.handle(e);
        }
        stage.setScene(scene);
        stage.show();
    }
    public synchronized void setPage(GUIPageController page){
        this.page = page;
    }
    public synchronized  GUIPageController getPage(){return page;}

    public void setPlayers(ArrayList<Player> players){this.players = players;}
    public ArrayList<Player> getPlayers(){return players;}

    @Override
    public synchronized void initializeConnection() {

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
    public void game() {

    }

    /**
     * This method closes all Socket streams used to communicate
     */
    @Override
    public void close() {
        if (!socket.isClosed()) {
            try{
                client.setActive(false);
                if(!asyncSocketReader.isInterrupted()) asyncSocketReader.interrupt();
                ClientHeartBeatHandler.stop();
                socket.close();
            } catch (IOException e) {
                client.setActive(false);
            }
        }
    }

    @Override
    public void quit() {

    }

    public void setGameSize() {
        stage.setHeight(930);
        stage.setWidth(1600);
    }
}


