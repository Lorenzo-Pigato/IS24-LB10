package it.polimi.ingsw.lb10.client.gui;

import it.polimi.ingsw.lb10.client.controller.ClientViewController;
import it.polimi.ingsw.lb10.client.controller.GUIClientViewController;
import it.polimi.ingsw.lb10.client.util.InputVerifier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.Socket;

public class GUIConnectionPageController implements GUIPageController {
    private final GUIClientViewController controller = GUIClientViewController.instance();

    @Override
    public String getFXML() {
        return "/fxml/ConnectionPage.fxml";
    }

    @Override
    public String getCSS() {
        return "/css/ConnectionPage.css";
    }


    @FXML
    private Label connect;
    @FXML
    private TextField serverIP;
    @FXML
    private TextField serverPort;

    public synchronized void submitIpValidation(ActionEvent event) {
        String ip = serverIP.getText();
        String port = serverPort.getText();

        if(InputVerifier.isNotValidIP(ip) && InputVerifier.isNotValidPort(port))
            connect.setText("Invalid IP and Port");
        else if (InputVerifier.isNotValidIP(ip)) {
            connect.setText("Invalid IP");
        } else if (InputVerifier.isNotValidPort(port)) {
            connect.setText("Invalid Port");
        } else {
            try {
                controller.setSocket(new Socket(ip, Integer.parseInt(port)));
                controller.setUp();
                controller.setHash();
                controller.changeScene(new GUILoginPageController());
            } catch (Exception e) {
                connect.setText("Error");
            }
        }
    }
}
