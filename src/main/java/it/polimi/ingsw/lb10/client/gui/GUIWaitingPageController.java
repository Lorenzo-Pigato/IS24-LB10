package it.polimi.ingsw.lb10.client.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class GUIWaitingPageController implements GUIPageController{

        @Override
        public String getFXML() {
            return "/fxml/WaitingPage.fxml";
        }

        @Override
        public String getCSS() {
            return "/css/WaitingPage.css";
        }

        @FXML
        private Text matchID;

        @FXML
        private Button quitButton;

        public void setMatchID(String matchID) {
            this.matchID.setText(matchID);
        }

        @FXML
        private void quit(ActionEvent event) {
            /// QUIT REQUEST???
        }


}
