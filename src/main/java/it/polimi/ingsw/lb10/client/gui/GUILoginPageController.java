package it.polimi.ingsw.lb10.client.gui;


import it.polimi.ingsw.lb10.client.Client;
import it.polimi.ingsw.lb10.client.controller.GUIClientViewController;
import it.polimi.ingsw.lb10.network.requests.preMatch.LoginRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;


public class GUILoginPageController implements GUIPageController , Initializable {

    private final GUIClientViewController controller = GUIClientViewController.instance();
    private final Client client = controller.getClient();
    private String clientUsername;

    @FXML
    private AnchorPane main;
    @FXML
    private Label login;
    @FXML
    private TextField username;
    @FXML
    private Text errorMessage;
    @FXML
    private AnchorPane errorAnchorPane;


    @Override
    public String getFXML() {
        return "/fxml/LoginPage.fxml";
    }

    @Override
    public String getCSS() {
        return "/css/LoginPage.css";
    }



    public void login(ActionEvent event){
        String playerUsername;
            try {
                playerUsername = username.getText();
                clientUsername = playerUsername;
                resetErrorPane();
                if (playerUsername.length() < 2 || playerUsername.length() > 15) {
                    errorAnchorPane.setVisible(true);
                    if (playerUsername.length() < 2){
                        errorMessage.setText("Username too short");
                        errorMessage.setVisible(true);
                    }
                    else{
                        errorMessage.setText("Username too long");
                        errorMessage.setVisible(true);
                    }
                }else{
                    resetErrorPane();
                    controller.send(new LoginRequest(playerUsername));
                }
            } catch (NullPointerException e) {
                controller.close();
            }

            username.clear();
    }

    private void resetErrorPane(){
        errorAnchorPane.setVisible(false);
        errorAnchorPane.getChildren().forEach(n -> n.setVisible(false));
    }

    public void logClient(boolean logged){
        if(logged){
            controller.getClient().setUsername(clientUsername);
            controller.changeScene(new GUIJoinMatchPageController());
        }else{
            errorAnchorPane.setVisible(true);
            errorMessage.setText("Username already taken");
            errorMessage.setVisible(true);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Thread socketReader = GUIClientViewController.instance().asyncReadFromSocket();
        socketReader.start();
    }
}
