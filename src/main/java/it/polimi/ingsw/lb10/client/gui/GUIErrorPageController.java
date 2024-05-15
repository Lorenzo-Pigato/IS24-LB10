package it.polimi.ingsw.lb10.client.gui;

import it.polimi.ingsw.lb10.client.controller.GUIClientViewController;
import it.polimi.ingsw.lb10.client.view.GUIClientView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class GUIErrorPageController implements GUIPageController, Initializable {

    @FXML
    private Text errorText;

    @Override
    public String getFXML() {
        return "/fxml/ErrorPage.fxml";
    }

    @Override
    public String getCSS() {
        return "/css/ErrorPage.css";}

    public void setErrorText(String error){
        errorText.setText(error);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GUIClientViewController.instance().setPage(this);
    }
}
