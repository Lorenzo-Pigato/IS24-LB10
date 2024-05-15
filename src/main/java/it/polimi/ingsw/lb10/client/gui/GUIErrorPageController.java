package it.polimi.ingsw.lb10.client.gui;

import it.polimi.ingsw.lb10.client.controller.GUIClientViewController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class GUIErrorPageController implements GUIPageController{
    @FXML
    AnchorPane errorPane;


    @Override
    public String getFXML() {
        return "/fxml/ErrorPage.fxml";
    }

    @Override
    public String getCSS() {
        return "/css/ErrorPage.css";}

    public void setErrorText(String error){
        Text errorText = new Text (error);
        errorText.setLayoutX(558);
        errorText.setLayoutY(434);
        errorPane.getChildren().add(errorText);
        errorText.setStyle("-fx-font-size: 15;");
    }
}
