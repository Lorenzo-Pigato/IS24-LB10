package it.polimi.ingsw.lb10.client.gui;

import it.polimi.ingsw.lb10.client.Client;
import it.polimi.ingsw.lb10.client.controller.GUIClientViewController;
import it.polimi.ingsw.lb10.client.util.InputVerifier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.Socket;

public class GUIConnectionPage implements GUIPage {
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
                GUIClientViewController.instance().setSocket(new Socket(ip, Integer.parseInt(port)));
                this.notifyAll();
            } catch (Exception e) {
                e.printStackTrace();
                connect.setText("Error");
            }
        }
    }
}
