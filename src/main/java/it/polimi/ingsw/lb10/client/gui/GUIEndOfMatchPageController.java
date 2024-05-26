package it.polimi.ingsw.lb10.client.gui;

import it.polimi.ingsw.lb10.client.controller.GUIClientViewController;
import it.polimi.ingsw.lb10.server.model.Player;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;

public class GUIEndOfMatchPageController implements GUIPageController, Initializable {

    @FXML
    private Text playerOne;

    @FXML
    private Text playerTwo;

    @FXML
    private Text playerThree;

    @FXML
    private Text playerFour;

    @FXML
    private Group firstBox;

    @FXML
    private Group secondBox;

    @FXML
    private Group thirdBox;

    @FXML
    private Group fourthBox;

    private static final ArrayList<Group> groups = new ArrayList<>();
    private static final ArrayList<Text> texts = new ArrayList<>();

    @Override
    public String getFXML() {
        return "/fxml/EndOfMatchPage.fxml";
    }

    @Override
    public String getCSS() {
        return "/css/EndOfMatchPage.css";
    }


    public void setScoreboard(ArrayList<Player> players){
        players.sort(Comparator.comparingInt(Player::getPoints).reversed());

        for(Player player : players) {
            texts.get(players.indexOf(player)).setText(player.getUsername() + " - " + player.getPoints());
            groups.get(players.indexOf(player)).setVisible(true);
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GUIClientViewController.instance().setPage(this);

        groups.add(firstBox);
        groups.add(secondBox);
        groups.add(thirdBox);
        groups.add(fourthBox);

        texts.add(playerOne);
        texts.add(playerTwo);
        texts.add(playerThree);
        texts.add(playerFour);

        for (Group group : groups) {
            group.setVisible(false);
        }
    }
}
