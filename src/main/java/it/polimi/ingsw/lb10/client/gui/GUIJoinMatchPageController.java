package it.polimi.ingsw.lb10.client.gui;
import it.polimi.ingsw.lb10.client.controller.GUIClientViewController;
import it.polimi.ingsw.lb10.network.requests.preMatch.LobbyToMatchRequest;
import it.polimi.ingsw.lb10.network.requests.preMatch.NewMatchRequest;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.GUIResponseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class GUIJoinMatchPageController implements GUIPageController{

    private int matchSize;
    private final ToggleGroup toggleGroup = new ToggleGroup();
    private final GUIClientViewController controller = GUIClientViewController.instance();
    private boolean matchSizeSelected = false;
    @FXML
    private Text invalidMatchIdError;

    @FXML
    private Text matchIdNotInsertedError;

    @FXML
    private Text numberOfPlayersError;

    @FXML
    private RadioButton twoPlayers;

    @FXML
    private RadioButton threePlayers;

    @FXML
    private RadioButton fourPlayers;

    @FXML
    private TextField matchId;


    @Override
    public String getFXML() {
        return "/fxml/JoinMatchPage.fxml";
    }


    @Override
    public String getCSS() {
        return "/fxml/JoinMatchPage.css";
    }

    @FXML
    private void setMatchSize(ActionEvent event){
        setUp();
        numberOfPlayersError.setVisible(false);
        matchSize = (Integer) ((RadioButton) (event.getSource())).getUserData();
        matchSizeSelected = true;
    }
    @FXML
    private void setUp() {
        twoPlayers.setUserData((2));
        threePlayers.setUserData(3);
        fourPlayers.setUserData(4);
        twoPlayers.setToggleGroup(toggleGroup);
        threePlayers.setToggleGroup(toggleGroup);
        fourPlayers.setToggleGroup(toggleGroup);
    }
    @FXML
    private void newMatch(ActionEvent event){
        resetErrors();
        if(matchSizeSelected){
            controller.send(new NewMatchRequest(matchSize));
            controller.syncReceive().accept(GUIResponseHandler.instance());
            if(!controller.getClient().isNotInMatch()) controller.changeScene(new GUIWaitingPageController());
        }else{
            numberOfPlayersError.setVisible(true);
        }

        if(!controller.getClient().isNotInMatch()){
            Thread socketReader = GUIClientViewController.instance().asyncReadFromSocket();
            socketReader.start();

            controller.changeScene(new GUIWaitingPageController());
        } else {
            invalidMatchIdError.setVisible(true);

        }

    }

    @FXML
    private void resetErrors() {
        invalidMatchIdError.setVisible(false);
        numberOfPlayersError.setVisible(false);
        matchIdNotInsertedError.setVisible(false);
    }

    @FXML
    private void joinMatch(ActionEvent event){
        resetErrors();
        int id = 0;
        if(!matchId.getText().isEmpty()){
            try{
                id = Integer.parseInt(matchId.getText());
                controller.send(new LobbyToMatchRequest(id));
                matchId.clear();
                controller.syncReceive().accept(GUIResponseHandler.instance());
                if(!controller.getClient().isNotInMatch()){
                    Thread socketReader = GUIClientViewController.instance().asyncReadFromSocket();
                    socketReader.start();

                    controller.changeScene(new GUIWaitingPageController());
                }
                else invalidMatchIdError.setVisible(true);
            }catch(NumberFormatException e){
                invalidMatchIdError.setVisible(true);
            }
        }else{
            matchIdNotInsertedError.setVisible(true);
        }

    }

}
