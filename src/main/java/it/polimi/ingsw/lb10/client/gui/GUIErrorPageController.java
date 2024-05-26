package it.polimi.ingsw.lb10.client.gui;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

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
