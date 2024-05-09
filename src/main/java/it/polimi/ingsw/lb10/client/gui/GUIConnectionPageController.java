package it.polimi.ingsw.lb10.client.gui;

import it.polimi.ingsw.lb10.client.controller.ClientViewController;
import it.polimi.ingsw.lb10.client.controller.GUIClientViewController;
import it.polimi.ingsw.lb10.client.util.InputVerifier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

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
    private Text unreachableServer;
    @FXML
    private Text invalidIPLabel;
    @FXML
    private Text invalidPortLabel;
    @FXML
    private TextField serverIP;
    @FXML
    private TextField serverPort;

    public synchronized void submitIpValidation(ActionEvent event) {
        String ip = serverIP.getText();
        String port = serverPort.getText();

        unreachableServer.setVisible(false);
        invalidIPLabel.setVisible(false);
        invalidPortLabel.setVisible(false);

        if (InputVerifier.isNotValidIP(ip))
            invalidIPLabel.setVisible(true);

        if (InputVerifier.isNotValidPort(port))
            invalidPortLabel.setVisible(true);

        if (!InputVerifier.isNotValidIP(ip) && !InputVerifier.isNotValidPort(port)) {
            try {
                controller.setSocket(new Socket(ip, Integer.parseInt(port)));
                controller.setUp();
                controller.setHash();
                controller.changeScene(new GUILoginPageController());
            } catch (Exception e) {
                unreachableServer.setVisible(true);
            }
        }
    }
}
