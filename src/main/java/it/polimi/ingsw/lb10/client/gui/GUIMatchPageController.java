package it.polimi.ingsw.lb10.client.gui;

import it.polimi.ingsw.lb10.client.controller.GUIClientViewController;
import it.polimi.ingsw.lb10.network.requests.match.ChatRequest;
import it.polimi.ingsw.lb10.network.requests.match.PlaceCardRequest;
import it.polimi.ingsw.lb10.network.requests.match.PlaceStartingCardRequest;
import it.polimi.ingsw.lb10.server.model.Player;
import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.Color;
import it.polimi.ingsw.lb10.server.model.cards.PlaceableCard;
import it.polimi.ingsw.lb10.server.model.cards.PlaceableCardState.BackOfTheCard;
import it.polimi.ingsw.lb10.server.model.cards.StartingCard;
import it.polimi.ingsw.lb10.server.model.cards.StartingCardState.FrontStartingCard;
import it.polimi.ingsw.lb10.server.model.cards.*;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;
import it.polimi.ingsw.lb10.server.model.quest.Quest;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class GUIMatchPageController implements GUIPageController , Initializable {

    private final GUIClientViewController controller = GUIClientViewController.instance();
    private static Player thisPlayer;
    private static ArrayList<Player> otherPlayers;
    private static Quest privateQuest;
    private static ArrayList<Quest> commonQuests;
    private static final ArrayList<StackPane> handCardStackPanes = new ArrayList<>();
    private int matrixCardId;
    private Rectangle clickedRectangle = null;
    private static StartingCard startingCard;
    private AnchorPane boardAnchorPane;

    @FXML
    private Button chatButton;
    @FXML
    private ScrollPane chatScrollPane;
    @FXML
    private VBox chatVBox;
    @FXML
    private TextField chatText;
    @FXML
    private StackPane handCardOnePane;
    @FXML
    private StackPane handCardTwoPane;
    @FXML
    private StackPane handCardThreePane;
    @FXML
    private AnchorPane resourcePane;
    @FXML
    private ScrollPane boardScrollPane;
    @FXML
    private Label plantResourceLabel;
    @FXML
    private Label mushroomResourceLabel;
    @FXML
    private Label animalResourceLabel;
    @FXML
    private Label insectResourceLabel;
    @FXML
    private Label pergamenaResourceLabel;
    @FXML
    private Label potionResourceLabel;
    @FXML
    private Label featherResourceLabel;
    @FXML
    private VBox questsVBox;

    private ArrayList<Label> resourceLabels;


    @Override
    public String getFXML() {
        return "/fxml/matchPage.fxml";
    }

    @Override
    public String getCSS() {
        return "/css/matchPage.css";
    }

    public static void setPrivateQuest(Quest privateQuest){GUIMatchPageController.privateQuest = privateQuest;}
    public static void setCommonQuests(ArrayList<Quest> commonQuests){ GUIMatchPageController.commonQuests = commonQuests;}
    public static void setThisPlayer(Player player){GUIMatchPageController.thisPlayer = player;}
    public static void setOtherPlayers (ArrayList<Player> otherPlayers){GUIMatchPageController.otherPlayers = otherPlayers;}
    public static void setStartingCard(StartingCard startingCard){GUIMatchPageController.startingCard = startingCard;}
    private void clearBoard(){boardAnchorPane.getChildren().removeAll();}

    public void showStartingCard(@NotNull StartingCard startingCard){

        setScrollNotPannable();

        GUIMatchPageController.startingCard = startingCard;

        ImageView startingCardImageView = new ImageView( new Image((Objects.requireNonNull(GUIChooseQuestPageController.class.getResourceAsStream("/cards/" + startingCard.getId() + ".png")))));

        startingCardImageView.setUserData(startingCard);
        startingCardImageView.setPreserveRatio(true);
        startingCardImageView.setFitHeight(200D);
        startingCardImageView.setFitWidth(200D);

        Button place = new Button();
        place.setPrefHeight(60);
        place.setPrefWidth(120);
        place.setText("Place");
        place.setOnAction(event -> {
            controller.send(new PlaceStartingCardRequest(controller.getMatchId(), startingCard));
        });

        Button flip = new Button();
        flip.setPrefHeight(60);
        flip.setPrefWidth(120);
        flip.setText("Flip");
        flip.setOnAction(event ->  {
            if(startingCard.getStateStartingCard().getClass().equals(FrontStartingCard.class)) startingCardImageView.setImage(new Image((Objects.requireNonNull(GUIChooseQuestPageController.class.getResourceAsStream("/cards/retro" + startingCard.getId() + ".png")))));
            else startingCardImageView.setImage(new Image((Objects.requireNonNull(GUIChooseQuestPageController.class.getResourceAsStream("/cards/" + startingCard.getId() + ".png")))));
            startingCard.swapState();
        });

        VBox buttonsBox = new VBox();
        buttonsBox.getChildren().addAll(flip, place);
        VBox.setMargin(flip, new Insets(0, 200, 10, 20));
        VBox.setMargin(place, new Insets(10, 200, 0, 20));

        HBox hBox = new HBox(startingCardImageView, buttonsBox);
        AnchorPane.setTopAnchor(hBox, boardAnchorPane.getPrefHeight()/2 - startingCardImageView.getFitHeight()/2 - 20);
        AnchorPane.setLeftAnchor(hBox, boardAnchorPane.getPrefWidth()/2 - startingCardImageView.getFitWidth()/2 - 20);

        boardAnchorPane.getChildren().addAll(hBox);

    }

    private void setScrollNotPannable() {
        boardScrollPane.setPannable(false);
    }

    private void setScrollPannable(){
        boardScrollPane.setPannable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller.setPage(this);

        chatVBox.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                chatScrollPane.setVvalue((Double) newValue);
            }
        });

        boardPanesSetup();
        handPanesSetup();
        resourcePaneSetup();
        questsSetup();

        showStartingCard(startingCard);
    }

    private void questsSetup()  {
        Insets standardInsets = new Insets(10, chatVBox.getPrefWidth()/2 - 40, 20, chatVBox.getPrefWidth()/2- 40);
        Label privateQuests = new Label("Private Quests");
        setStandardFont(privateQuests);
        VBox.setMargin(privateQuests, standardInsets);
        Label publicQuests = new Label("Private Quests");
        setStandardFont(publicQuests);
        VBox.setMargin(publicQuests, standardInsets);

        questsVBox.getChildren().add(privateQuests);

        ImageView privateQuestImageView = new ImageView(new Image(Objects.requireNonNull(GUIChooseQuestPageController.class.getResourceAsStream("/cards/" + privateQuest.getId() + ".png"))));

        VBox.setMargin(privateQuestImageView, standardInsets);

        questsVBox.getChildren().add(privateQuestImageView);

        ImageView publicQuestOne = new ImageView(new Image(Objects.requireNonNull(GUIChooseQuestPageController.class.getResourceAsStream("/cards/" + commonQuests.getFirst().getId() + ".png"))));
        VBox.setMargin(publicQuestOne, standardInsets);

        ImageView publicQuestTwo = new ImageView(new Image(Objects.requireNonNull(GUIChooseQuestPageController.class.getResourceAsStream("/cards/" + commonQuests.get(1).getId() + ".png"))));
        VBox.setMargin(publicQuestTwo, standardInsets);

        questsVBox.getChildren().add(publicQuests);
        questsVBox.getChildren().add(publicQuestOne);
        questsVBox.getChildren().add(publicQuestTwo);

    }

    private void setStandardFont(Label label) {
        try{
            label.setFont(Font.loadFont(Objects.requireNonNull(getClass().getResource("/fonts/AlegreyaSC-ExtraBold.ttf")).openStream(), 20));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void resourcePaneSetup() {
        resourceLabels = new ArrayList<>();
        plantResourceLabel.setUserData(Resource.PLANT);
        resourceLabels.add(plantResourceLabel);
        animalResourceLabel.setUserData(Resource.ANIMAL);
        resourceLabels.add(animalResourceLabel);
        mushroomResourceLabel.setUserData(Resource.MUSHROOM);
        resourceLabels.add(mushroomResourceLabel);
        insectResourceLabel.setUserData(Resource.INSECT);
        resourceLabels.add(insectResourceLabel);
        featherResourceLabel.setUserData(Resource.FEATHER);
        resourceLabels.add(featherResourceLabel);
        pergamenaResourceLabel.setUserData(Resource.PERGAMENA);
        resourceLabels.add(pergamenaResourceLabel);
        potionResourceLabel.setUserData(Resource.POTION);
        resourceLabels.add(potionResourceLabel);
    }

    private void handPanesSetup() {
        handCardStackPanes.add(handCardOnePane);
        handCardStackPanes.add(handCardTwoPane);
        handCardStackPanes.add(handCardThreePane);

        handCardStackPanes.forEach(pane -> {
            ImageView disabledView = new ImageView();
            disabledView.setPreserveRatio(true);
            disabledView.setFitHeight(pane.getPrefHeight());
            disabledView.setFitWidth(pane.getPrefWidth());
            pane.setDisable(true);
            pane.getChildren().add(disabledView);
            ArrayList<Rectangle> hooverRectangles = rectangles();
            pane.getChildren().addAll(hooverRectangles);
        });

    }

    private void boardPanesSetup() {

        boardAnchorPane = new AnchorPane();
        boardScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        boardScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        boardScrollPane.setPrefHeight(664);
        boardScrollPane.setPrefWidth(1181);
        boardAnchorPane.setPrefSize(boardScrollPane.getPrefWidth(), boardScrollPane.getPrefHeight());

        boardScrollPane.setContent(boardAnchorPane);
        boardScrollPane.setStyle("-fx-background-color: transparent");
        boardAnchorPane.setVisible(true);
    }

    private @NotNull ArrayList<Rectangle> rectangles() {

        ArrayList<Rectangle> hooverRectangles = new ArrayList<>();

        for (Position position : Position.values()){
            Rectangle hooverRectangle = new Rectangle();

            hooverRectangleStyleSetup(hooverRectangle);

            switch (position){
                case TOPLEFT:
                    StackPane.setAlignment(hooverRectangle, Pos.TOP_LEFT);
                    break;
                case TOPRIGHT:
                    StackPane.setAlignment(hooverRectangle, Pos.TOP_RIGHT);
                    break;
                case BOTTOMRIGHT:
                    StackPane.setAlignment(hooverRectangle, Pos.BOTTOM_RIGHT);
                    break;
                case BOTTOMLEFT:
                    StackPane.setAlignment(hooverRectangle, Pos.BOTTOM_LEFT);
                    break;
            }

            hooverRectangle.setUserData(position);

            hooverRectangles.add(hooverRectangle);
            hooverRectangle.setDisable(false);



            hooverRectangle.setOnMouseEntered(_ -> hooverRectangle.setOpacity(0.3));

            hooverRectangle.setOnMouseExited(_ ->{
                if(!isClicked(hooverRectangle)) hooverRectangle.setOpacity(0);
            });

            hooverRectangle.setOnMouseClicked(_ -> {
                if(!isClicked(hooverRectangle)){
                    clickedRectangle = hooverRectangle;
                    handCardStackPanes.forEach(stackPane -> stackPane.getChildren().stream().filter(node -> node.getClass().equals(Rectangle.class)).map(node -> ((Rectangle) (node))).forEach(rectangle -> {
                        if(!rectangle.equals(clickedRectangle)) rectangle.setOpacity(0);}));
                }else {
                    clickedRectangle = null;
                }

            });
        }
        return hooverRectangles;
    }

    private static void hooverRectangleStyleSetup(Rectangle hooverRectangle) {
        hooverRectangle.setWidth(58);
        hooverRectangle.setHeight(65);
        hooverRectangle.setEffect(new GaussianBlur(1));
        hooverRectangle.setArcHeight(20);
        hooverRectangle.setArcWidth(20);
        hooverRectangle.setStroke(javafx.scene.paint.Color.rgb(218,241,0));
        hooverRectangle.setStrokeWidth(2);
        hooverRectangle.setFill(javafx.scene.paint.Color.rgb(255, 253, 0));
        hooverRectangle.setVisible(true);
        hooverRectangle.setOpacity(0);
    }

    private  boolean isClicked(Rectangle rec){
        return rec.equals(clickedRectangle);
    }

    public static void insertCardOnHand(PlaceableCard card){
        Optional<StackPane> emptyPane = handCardStackPanes.stream().filter(Node::isDisabled).findFirst();
        emptyPane.ifPresent(stackPane -> {
            if(!stackPane.isVisible()) stackPane.setVisible(true);
            addHandCard(stackPane, card);
        });

    }

    private static void addHandCard(StackPane stackPane, PlaceableCard card) {
        stackPane.setDisable(false);

        Image cardImage = new Image(Objects.requireNonNull(GUIMatchPageController.class.getResourceAsStream(getResourcePath(card))));
        ImageView cardView = (ImageView) stackPane.getChildren().stream().filter(ch -> ch.getClass().equals(ImageView.class)).findFirst().orElseThrow(RuntimeException::new);

        cardView.setImage(cardImage);
        cardView.setUserData(card);
        AnchorPane.setTopAnchor(cardView, 0D);
        AnchorPane.setLeftAnchor(cardView, 0D);
        AnchorPane.setBottomAnchor(cardView, 0D);
        AnchorPane.setRightAnchor(cardView, 0D);

        stackPane.getChildren().stream().filter(node -> node.getClass().equals(Rectangle.class)).forEach(rectangle -> {
            if(card.getStateCardCorners().stream().filter(corner -> !corner.isAvailable()).anyMatch(corner -> corner.getPosition().equals(rectangle.getUserData()))){
                rectangle.setDisable(true);
                rectangle.setOpacity(0);
            }else{
                rectangle.setDisable(false);
            }
        });

    }

    @FXML
    private void send(ActionEvent event){
        if(!chatText.getText().isEmpty()){
            controller.send(new ChatRequest(GUIClientViewController.instance().getMatchId(), chatText.getText()));
            chatText.clear();
        }
    }

    public void newMessage(String username, String message){
        Player senderPlayer = otherPlayers.stream().filter(p -> p.getUsername().equals(username)).findFirst().orElse(new Player(0, "Server"));

        if(senderPlayer.getUserHash() == 0) senderPlayer.setColor(Color.GREEN);

        if(username.equals(controller.getClient().getUsername())){
            showLocalMessage(message, thisPlayer);
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

        String path = getResourcePath(card);

        ImageView cardOnTable = new ImageView(new Image(
                String.valueOf(Objects
                      .requireNonNull(this.getClass()
                      .getResourceAsStream(path + ".png"))))
        );

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

    private static @NotNull String getResourcePath(PlaceableCard card) {
        String path = "/cards/";
        if(card.getCardState() instanceof BackOfTheCard && (card instanceof GoldenCard || card instanceof ResourceCard))
        {
            path += "retro";
            if(card instanceof GoldenCard) path += "Golden";
            else path += "Resource";

            path += card.getResource().getResourceString();
        }
        else if(card.getCardState() instanceof BackOfTheCard) path += "retro" + card.getId();

        else path += card.getId();
        return path + ".png";
    }

    @FXML
    private void hooverCardOnTable(MouseEvent event){

    }

}
