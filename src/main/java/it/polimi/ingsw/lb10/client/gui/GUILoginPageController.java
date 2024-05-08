package it.polimi.ingsw.lb10.client.gui;


import it.polimi.ingsw.lb10.client.Client;
import it.polimi.ingsw.lb10.client.controller.GUIClientViewController;
import it.polimi.ingsw.lb10.network.requests.preMatch.LoginRequest;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.GUIResponseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;


public class GUILoginPageController implements GUIPageController {

    private final GUIClientViewController controller = GUIClientViewController.instance();
    private final Client client = controller.getClient();

    @FXML
    private AnchorPane main;
    @FXML
    private Label login;
    @FXML
    private TextField username;
    @FXML
    private Text usernameAlreadyTaken;
    @FXML
    private Text usernameTooLong;
    @FXML
    private Text usernameTooShort;
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
        if (client.isActive()) {
            try {
                playerUsername = username.getText();
                resetErrorPane();
                if (playerUsername.length() < 2 || playerUsername.length() > 15) {
                    errorAnchorPane.setVisible(true);
                    if (playerUsername.length() < 2) usernameTooShort.setVisible(true);
                    else usernameTooLong.setVisible(true);
                }else{
                    resetErrorPane();
                    controller.send(new LoginRequest(playerUsername));
                    controller.syncReceive().accept(GUIResponseHandler.instance());

                    if (client.isNotLogged()) {
                        errorAnchorPane.setVisible(true);
                        usernameAlreadyTaken.setVisible(true);
                    }

                    if(!client.isNotLogged()){
                        controller.changeScene(new GUIJoinMatchPageController());
                    }
                }


            } catch (NullPointerException e) {
                controller.close();
            }
            username.clear();
        }
    }

    private void resetErrorPane(){
        errorAnchorPane.setVisible(false);
        errorAnchorPane.getChildren().forEach(n -> n.setVisible(false));
    }

}
