package it.polimi.ingsw.lb10.client.gui;

import it.polimi.ingsw.lb10.client.controller.GUIClientViewController;
import it.polimi.ingsw.lb10.network.requests.QuitRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class GUIWaitingPageController implements GUIPageController, Initializable {

        @FXML
        private Text matchId;

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            matchId.setText(String.valueOf(GUIClientViewController.instance().getMatchId()));
        }

        @Override
        public String getFXML() {
            return "/fxml/WaitingPage.fxml";
        }

        @Override
        public String getCSS() {
            return "/css/WaitingPage.css";
        }

}
