package it.polimi.ingsw.lb10.client.gui;

import it.polimi.ingsw.lb10.client.controller.GUIClientViewController;
import it.polimi.ingsw.lb10.network.requests.match.PrivateQuestSelectedRequest;
import it.polimi.ingsw.lb10.network.requests.match.PrivateQuestsRequest;
import it.polimi.ingsw.lb10.server.model.quest.Quest;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class GUIChooseQuestPageController implements GUIPageController , Initializable{

    private static Quest firstQuestCard;
    private static Quest secondQuestCard;

    @Override
    public String getFXML() {
        return "/fxml/ChooseQuestPage.fxml";
    }

    @Override
    public String getCSS() {
        return "/css/ChooseQuestPage.css";
    }

    @FXML
    private ImageView firstQuest;

    @FXML
    private ImageView secondQuest;

    @FXML
    private static Rectangle firstQuestContainer;

    @FXML
    private static Rectangle secondQuestContainer;


    public static void setQuests(Quest firstQuestCard, Quest secondQuestCard){
        GUIChooseQuestPageController.firstQuestCard = firstQuestCard;
        GUIChooseQuestPageController.secondQuestCard = secondQuestCard;
    }

    @FXML
    private void focusFirstQuest(MouseEvent event){
        firstQuest.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, new Color(0.85, 0.65, 0, 0.75), 10, 0.3, 0,0));
    }

    @FXML
    private void focusSecondQuest(MouseEvent event){
        secondQuest.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, new Color(0.85, 0.65, 0, 0.75), 10, 0.3, 0,0));
    }

    @FXML
    private void unfocusFirstQuest(MouseEvent event){
        firstQuest.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, new Color(0, 0, 0, 0.6), 10, 0, 0,0));
    }

    @FXML
    private void unfocusSecondQuest(MouseEvent event){
        secondQuest.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, new Color(0, 0, 0, 0.6), 10, 0, 0,0));
    }

    @FXML
    private void chooseQuest(MouseEvent event){
        Quest chosen = event.getSource().equals(firstQuest) ? firstQuestCard : secondQuestCard;
        GUIMatchPageController.setPrivateQuest(chosen);

        GUIClientViewController.instance().send(new PrivateQuestSelectedRequest(GUIClientViewController.instance().getMatchId(), chosen));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        firstQuest.setImage(new Image(Objects.requireNonNull(GUIChooseQuestPageController.class.getResourceAsStream("/cards/" + firstQuestCard.getId() + ".png"))));
        secondQuest.setImage(new Image(Objects.requireNonNull(GUIChooseQuestPageController.class.getResourceAsStream("/cards/" + secondQuestCard.getId() + ".png"))));
    }
}
