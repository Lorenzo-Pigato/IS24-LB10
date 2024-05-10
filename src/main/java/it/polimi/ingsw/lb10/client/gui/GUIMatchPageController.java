package it.polimi.ingsw.lb10.client.gui;

import it.polimi.ingsw.lb10.client.controller.GUIClientViewController;
import it.polimi.ingsw.lb10.network.requests.match.ChatRequest;
import it.polimi.ingsw.lb10.network.requests.match.PlaceCardRequest;
import it.polimi.ingsw.lb10.server.model.Player;
import it.polimi.ingsw.lb10.server.model.cards.Color;
import it.polimi.ingsw.lb10.server.model.cards.PlaceableCard;
import it.polimi.ingsw.lb10.server.model.cards.PlaceableCardState.BackOfTheCard;
import it.polimi.ingsw.lb10.server.model.cards.PlaceableCardState.StateOfTheCard;
import it.polimi.ingsw.lb10.server.model.cards.StartingCard;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;
import it.polimi.ingsw.lb10.server.model.quest.Quest;
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
import javafx.scene.input.MouseEvent;
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
    private static Player thisPlayer;
    private static ArrayList<Player> otherPlayers;
    private static Quest privateQuest;
    private static ArrayList<Quest> commonQuests;
    private ArrayList<AnchorPane> handCardAnchorPanes;
    private int matrixCardId;
    private Rectangle clickedRectangle;
    private static StartingCard startingCard;

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
    @FXML
    private ScrollPane boardScrollPane;

    @Override
    public String getFXML() {
        return "";
    }

    @Override
    public String getCSS() {
        return "";
    }

    public static void setPrivateQuest(Quest privateQuest){GUIMatchPageController.privateQuest = privateQuest;}
    public static void setCommonQuests(ArrayList<Quest> commonQuests){ GUIMatchPageController.commonQuests = commonQuests;}
    public static void setThisPlayer(Player player){GUIMatchPageController.thisPlayer = player;}
    public static void setOtherPlayers (ArrayList<Player> otherPlayers){GUIMatchPageController.otherPlayers = otherPlayers;}

    public void setAndShowStartingCard(StartingCard startingCard){

        setScrollNotDraggable();

        GUIMatchPageController.startingCard = startingCard;

        ImageView startingImageView = new ImageView( new Image((Objects.requireNonNull(GUIChooseQuestPageController.class.getResourceAsStream("/cards/" + startingCard.getId() + ".png")))));

        startingImageView.setUserData(startingCard);
        startingImageView.setFitHeight(80D);
        startingImageView.setFitHeight(130D);
        AnchorPane startingCardAnchorPane = new AnchorPane(startingImageView);
        AnchorPane.setTopAnchor(startingCardAnchorPane, 0D);
        AnchorPane.setLeftAnchor(startingCardAnchorPane, 0D);
        Button flip = new Button();
        flip.setPrefHeight(20);
        flip.setPrefWidth(20);
        flip.setText("Flip");
        flip.setOnAction(event ->  {
            startingImageView.setImage(new Image((Objects.requireNonNull(GUIChooseQuestPageController.class.getResourceAsStream("/cards/retro" + startingCard.getId() + ".png")))));
            startingCard.setFlippedState();
        });

        boardScrollPane.setContent(startingImageView);

    }

    private void setScrollNotDraggable() {
        boardScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        boardScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
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

        ArrayList<Rectangle> hooverRectangles = new ArrayList<>();

        for (Position position : Position.values()){
            Rectangle hooverRectangle = new Rectangle();

            switch (position){
                case TOPLEFT:
                    AnchorPane.setTopAnchor(hooverRectangle, 0D);
                    AnchorPane.setLeftAnchor(hooverRectangle, 0D);
                    break;
                case TOPRIGHT:
                    AnchorPane.setTopAnchor(hooverRectangle, 0D);
                    AnchorPane.setRightAnchor(hooverRectangle, 0D);
                    break;
                case BOTTOMRIGHT:
                    AnchorPane.setBottomAnchor(hooverRectangle, 0D);
                    AnchorPane.setRightAnchor(hooverRectangle, 0D);
                    break;
                case BOTTOMLEFT:
                    AnchorPane.setBottomAnchor(hooverRectangle, 0D);
                    AnchorPane.setLeftAnchor(hooverRectangle, 0D);
                    break;
            }

            hooverRectangle.setUserData(position);

            hooverRectangles.add(hooverRectangle);

            hooverRectangle.setStyle("-fx-background-color: #d6af00");
            hooverRectangle.setVisible(false);

            hooverRectangle.setOnMouseEntered(mouseEvent -> {
                hooverRectangle.setVisible(true);
            });

            hooverRectangle.setOnMouseExited(mouseEvent -> {
                hooverRectangle.setVisible(isClicked(hooverRectangle));
            });

            hooverRectangle.setOnMouseClicked(mouseEvent -> {
                if(!isClicked(hooverRectangle)){
                    hooverRectangle.setVisible(true);
                    clickedRectangle = hooverRectangle;
                }else {
                    hooverRectangle.setVisible(false);
                    clickedRectangle = null;
                }

            });
        }
        return hooverRectangles;
    }

    private  boolean isClicked(Rectangle rec){
        return clickedRectangle == null || rec.equals(clickedRectangle);
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
        cardView.setUserData(card);

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

    /**
     * This method must be assigned to all cards on the table when inserted inside the board.
     * Starting card must have this method assigned.
     * @param event mouse click event
     */
    @FXML
    private void selectCardOnTable(MouseEvent event){
        if(clickedRectangle != null){

            matrixCardId = ((PlaceableCard) event.getSource()).getId();

            controller.send(new PlaceCardRequest(
                    controller.getMatchId(),
                    (PlaceableCard) clickedRectangle.getParent().getChildrenUnmodifiable().stream()
                            .filter(ch -> ch.getClass().equals(ImageView.class))
                            .findFirst()
                            .orElseThrow(RuntimeException::new)
                            .getUserData(),

                    (Position) clickedRectangle.getUserData(),
                    ((PlaceableCard) event.getSource()).getId()
                    )
            );

            clickedRectangle.setVisible(false);
            clickedRectangle = null;
        }
    }

    public void placeCardOnTable(PlaceableCard card, Position position){
        int cardWidth;
        int cardHeight;

        ImageView cardOnTable = new ImageView(new Image(String.valueOf(Objects.requireNonNull(this.getClass()
                .getResourceAsStream(
                        "/cards/" +
                                (card.getCardState() instanceof BackOfTheCard ? "retro" : "") +
                                card.getId() +
                                ".png"
                )
        ))));

        ImageView cardOnBoard = boardScrollPane.getChildrenUnmodifiable().stream()
                .filter(ch -> ch.getClass().equals(ImageView.class))
                .map(ch -> (ImageView) ch)
                .filter(ch -> ch.getUserData().equals(card))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        // ATTENTION! Modify offset values to match the card size!!
        cardOnTable.setLayoutX(cardOnBoard.getLayoutX() + position.getGuiOffsetX());
        cardOnTable.setLayoutY(cardOnBoard.getLayoutY() + position.getGuiOffsetY());

        boardScrollPane.setContent(cardOnTable);

    }

    @FXML
    private void hooverCardOnTable(MouseEvent event){

    }

}
