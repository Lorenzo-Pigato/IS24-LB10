package it.polimi.ingsw.lb10.client.gui;

import it.polimi.ingsw.lb10.client.controller.ClientViewController;
import it.polimi.ingsw.lb10.client.controller.GUIClientViewController;
import it.polimi.ingsw.lb10.network.requests.match.PrivateQuestSelectedRequest;
import it.polimi.ingsw.lb10.network.requests.match.PrivateQuestsRequest;
import it.polimi.ingsw.lb10.server.model.quest.Quest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class GUIChooseQuestPageController implements GUIPageController, Initializable {

    private static Quest firstQuestCard;
    private static Quest secondQuestCard;

    private Image firstQuestImage;
    private Image secondQuestImage;

    @Override
    public String getFXML() {
        return "/fxml/ChooseQuestPage.fxml";
    }

    @Override
    public String getCSS() {
        return "/css/ChooseQuestPage.css";
    }

    @FXML
    private static ImageView firstQuest;

    @FXML
    private static ImageView secondQuest;

    @FXML
    private static Rectangle firstQuestContainer;

    @FXML
    private static Rectangle secondQuestContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GUIClientViewController.instance().send(new PrivateQuestsRequest(GUIClientViewController.instance().getMatchId()));
    }

    public static void setQuests(Quest firstQuestCard, Quest secondQuestCard){
        GUIChooseQuestPageController.firstQuestCard = firstQuestCard;
        GUIChooseQuestPageController.secondQuestCard = secondQuestCard;

        firstQuest.setImage(new Image(Objects.requireNonNull(GUIChooseQuestPageController.class.getResourceAsStream("/cards/" + firstQuestCard.getId() + ".png"))));
        secondQuest.setImage(new Image(Objects.requireNonNull(GUIChooseQuestPageController.class.getResourceAsStream("/cards/" + secondQuestCard.getId() + ".png"))));
    }

    @FXML
    private void focusFirstQuest(ActionEvent event){
        firstQuest.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, new Color(205, 167, 0, 1), 10, 10, 0,0));
    }

    @FXML
    private void focusSecondQuest(ActionEvent event){
        secondQuest.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, new Color(205, 167, 0, 1), 10, 10, 0,0));
    }

    @FXML
    private void unfocusFirstQuest(ActionEvent event){
        firstQuest.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, new Color(0, 0, 0, 0.5), 5, 5, 0,0));
    }

    @FXML
    private void unfocusSecondQuest(ActionEvent event){
        secondQuest.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, new Color(0, 0, 0, 0.5), 5, 5, 0,0));
    }

    @FXML
    private void chooseQuest(ActionEvent event){
        Quest chosen = event.getSource().equals(firstQuest) ? firstQuestCard : secondQuestCard;
        GUIClientViewController.instance().send(new PrivateQuestSelectedRequest(GUIClientViewController.instance().getMatchId(), chosen));

        //to be completed
    }


}
