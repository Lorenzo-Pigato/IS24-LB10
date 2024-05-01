package it.polimi.ingsw.lb10.client.gui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class client extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Codex");
        stage.setHeight(480);
        stage.setWidth(640);
        stage.setResizable(false);
        stage.getIcons().add(new Image("icon.png"));

        Group root = new Group();
        //Parent root = FXMLLoader.load(getClass().getResource("ConnectionPage.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}
