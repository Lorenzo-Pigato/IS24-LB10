package it.polimi.ingsw.lb10.client.controller;

import it.polimi.ingsw.lb10.client.exception.ConnectionErrorException;
import it.polimi.ingsw.lb10.client.exception.ExceptionHandler;
import it.polimi.ingsw.lb10.client.exception.GUIExceptionHandler;
import it.polimi.ingsw.lb10.client.gui.GUIConnectionPageController;
import it.polimi.ingsw.lb10.client.gui.GUIPageController;
import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.network.response.match.PrivateQuestsResponse;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.GUIResponseHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class GUIClientViewController extends ClientViewController {

    private static GUIClientViewController instance;
    private GUIPageController page;
    private Stage stage;
    private Scene scene;
    private final ExceptionHandler exceptionHandler = new GUIExceptionHandler(this);

    private static final GUIResponseHandler responseHandler = GUIResponseHandler.instance();

    public static GUIClientViewController instance() {
        if (instance == null) instance = new GUIClientViewController();
        return instance;
    }

    public void changeScene(GUIPageController page) {
        setPage(page);
        FXMLLoader fxmlLoader = new FXMLLoader(GUIClientViewController.class.getResource(page.getFXML()));

        try{
            Parent root = fxmlLoader.load();
            scene.setRoot(root);
            stage.show();
        }catch (IOException e){
            exceptionHandler.handle(e);
        }
    }

    public void initialize(Stage stage) {
        this.stage = stage;
        stage.setResizable(false);
        stage.setTitle("Codex Naturalis");
        stage.setWidth(600);
        stage.setHeight(900);
        stage.getIcons().add(new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/images/icon.png"))));

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
    private void setPage(GUIPageController page){
        this.page = page;
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

    @Override
    public void initializeConnection() throws ConnectionErrorException {

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



}


