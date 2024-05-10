package it.polimi.ingsw.lb10.client.gui;

import it.polimi.ingsw.lb10.client.controller.GUIClientViewController;
import it.polimi.ingsw.lb10.network.requests.match.ChatRequest;
import it.polimi.ingsw.lb10.server.model.Player;
import it.polimi.ingsw.lb10.server.model.cards.Color;
import it.polimi.ingsw.lb10.server.model.cards.PlaceableCard;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.jetbrains.annotations.NotNull;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class GUIMatchPageController implements GUIPageController , Initializable {
    private final GUIClientViewController controller = GUIClientViewController.instance();
    private Player thisPlayer;
    private ArrayList<Player> otherPlayers;
    private ArrayList<AnchorPane> handCardAnchorPanes;
    private int matrixCardId;
    private Rectangle clickedRectangle;

    @FXML
    private Button chatButton;
    @FXML
    private ScrollPane chatScrollPane;
    @FXML
    private VBox chatVBox;
    @FXML
    private TextField chatText;
    @FXML
    private AnchorPane handCardOnePane;
    @FXML
    private AnchorPane handCardTwoPane;
    @FXML
    private AnchorPane handCardThreePane;



    /**
     * @return 
     */
    @Override
    public String getFXML() {
        return "";
    }

    /**
     * @return 
     */
    @Override
    public String getCSS() {
        return "";
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chatVBox.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                chatScrollPane.setVvalue((Double) newValue);
            }
        });

        handCardAnchorPanes.add(handCardOnePane);
        handCardAnchorPanes.add(handCardTwoPane);
        handCardAnchorPanes.add(handCardThreePane);
        initializePanes();
    }

    private void initializePanes() {
        handCardAnchorPanes.forEach(pane -> {
            pane.setVisible(false);
            ImageView disabledView = new ImageView();
            disabledView.setDisable(true);
            pane.getChildren().add(disabledView);
            ArrayList<Rectangle> hooverRectangles = rectangles();

            for (int i = 0; i < 4; i++){
                pane.getChildren().add(hooverRectangles.get(i));
            }
        });
    }

    private @NotNull ArrayList<Rectangle> rectangles() {
        Rectangle hooverRectangleTopLeft = new Rectangle();
        AnchorPane.setTopAnchor(hooverRectangleTopLeft, 0D);
        AnchorPane.setLeftAnchor(hooverRectangleTopLeft, 0D);

        Rectangle hooverRectangleTopRight = new Rectangle();
        AnchorPane.setTopAnchor(hooverRectangleTopRight, 0D);
        AnchorPane.setRightAnchor(hooverRectangleTopRight, 0D );

        Rectangle hooverRectangleBottomRight = new Rectangle();
        AnchorPane.setBottomAnchor(hooverRectangleBottomRight, 0D);
        AnchorPane.setRightAnchor(hooverRectangleBottomRight, 0D );

        Rectangle hooverRectangleBottomLeft = new Rectangle();
        AnchorPane.setBottomAnchor(hooverRectangleBottomLeft, 0D);
        AnchorPane.setLeftAnchor(hooverRectangleBottomLeft, 0D );

        ArrayList<Rectangle> hooverRectangles = new ArrayList<>();

        hooverRectangles.add(hooverRectangleBottomRight);
        hooverRectangles.add(hooverRectangleTopRight);
        hooverRectangles.add(hooverRectangleBottomLeft);
        hooverRectangles.add(hooverRectangleTopLeft);

        hooverRectangles.forEach(hr -> {

            hr.setStyle("-fx-background-color: #d6af00");
            hr.setVisible(false);

            hr.setOnMouseEntered(mouseEvent -> {
                hr.setVisible(true);
            });

            hr.setOnMouseExited(mouseEvent -> {
                hr.setVisible(isClicked(hr));
            });

            hr.setOnMouseClicked(mouseEvent -> {
                if(!isClicked(hr)){
                    hr.setVisible(true);
                    clickedRectangle = hr;
                }
            });
        });

        return hooverRectangles;
    }
    private  boolean isClicked(Rectangle rec){
        return clickedRectangle == null || rec.equals(clickedRectangle);
    }
    public void setThisPlayer(Player thisPlayer) {
        this.thisPlayer = thisPlayer;
    }

    public void setOtherPlayers (ArrayList<Player> otherPlayers){
        this.otherPlayers = otherPlayers;
    }

    public void insertCardOnHand(PlaceableCard card){
        Optional<AnchorPane> emptyPane = handCardAnchorPanes.stream().filter(Node::isDisabled).findFirst();
        emptyPane.ifPresent(anchorPane -> addHandCard(anchorPane, card));
    }

    private void addHandCard(AnchorPane anchorPane, PlaceableCard card) {
        anchorPane.setDisable(false);
        Image cardImage = new Image(String.valueOf(Objects.requireNonNull(this.getClass().getResourceAsStream("/cards/" + card.getId() + ".png"))));
        ImageView cardView = (ImageView) anchorPane.getChildren().stream().filter(ch -> ch.getClass().equals(ImageView.class)).findAny().orElseThrow(RuntimeException::new);
        cardView.setImage(cardImage);
        AnchorPane.setTopAnchor(cardView, 0D);
        AnchorPane.setLeftAnchor(cardView, 0D);
        AnchorPane.setBottomAnchor(cardView, 0D);
        AnchorPane.setRightAnchor(cardView, 0D);
    }

    @FXML
    private void send(){
        if(!chatText.getText().isEmpty()){
            controller.send(new ChatRequest(GUIClientViewController.instance().getMatchId(), chatText.getText()));
            chatText.clear();
        }
    }

    public void newMessage(String username, String message){
        Player senderPlayer = controller.getPlayers().stream().filter(p -> p.getUsername().equals(username)).findFirst().orElse(new Player(0, "Server"));

        if(senderPlayer.getUserHash() == 0) senderPlayer.setColor(Color.GREEN);

        if(senderPlayer.getUsername().equals(controller.getClient().getUsername())){
            showLocalMessage(message, senderPlayer);
        }else{
            showOuterMessage(message, senderPlayer);
        }

    }

    private void showOuterMessage(String message, Player senderPlayer) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5, 5, 5, 10));

        Text text = new Text(message);
        Text username = new Text(senderPlayer.getUsername() + " : ");
        username.setStyle("-fx-fill : " + senderPlayer.getColor().getCssString());
        text.setStyle("-fx-fill : black");

        TextFlow textFlow = new TextFlow(username);

        textFlow.setStyle("-fx-background-color : rgba(83,61,35,0.37)"
                + ";-fx-background-radius : 20px");

        textFlow.getChildren().add(text);

        hBox.getChildren().add(textFlow);
        chatVBox.getChildren().add(hBox);
    }

    private void showLocalMessage(String message, Player senderPlayer) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setPadding(new Insets(5, 5, 5, 10));

        Text text = new Text(message);
        Text username = new Text(senderPlayer.getUsername() + " : ");

        text.setFill(javafx.scene.paint.Color.color(0, 0, 0));
        username.setStyle("-fx-fill : " + senderPlayer.getColor().getCssString().toLowerCase());

        TextFlow textFlow = new TextFlow(username);
        textFlow.getChildren().add(text);
        textFlow.setStyle("-fx-background-color : rgb(220,191,145)"
                + ";-fx-background-radius : 20px");

        textFlow.setPadding(new Insets(5, 10, 5, 10));
        hBox.getChildren().add(textFlow);
        chatVBox.getChildren().add(hBox);
    }


}
