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
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;
import it.polimi.ingsw.lb10.server.model.quest.Quest;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.*;

public class GUIMatchPageController implements GUIPageController , Initializable {

    private final GUIClientViewController controller = GUIClientViewController.instance();
    private static Player thisPlayer;
    private static ArrayList<Player> otherPlayers;
    private static Quest privateQuest;
    private static ArrayList<Quest> commonQuests;
    private static final ArrayList<StackPane> handCardStackPanes = new ArrayList<>();
    private int matrixCardId;
    private static Rectangle clickedRectangle = null;
    private PlaceableCard clickedCard;
    private static StartingCard startingCard;
    private AnchorPane boardAnchorPane;
    private final int boardCardDimension = 0xfa;
    private boolean matrixCardValidSelection = false;
    private boolean placeRequestValidating = false;
    private final Map<Position, int[]> placementOffsets = new HashMap<>();

    private static final HashMap<Integer, int[]> boardPositions = new HashMap<>();


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
    private ImageView questCardOne;
    @FXML
    private ImageView questCardTwo;
    @FXML
    private ImageView questCardThree;

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
    private void clearBoard(){boardAnchorPane.getChildren().clear();}

    public void showStartingCard(@NotNull StartingCard startingCard){

        setScrollNotPannable();

        GUIMatchPageController.startingCard = startingCard;

        ImageView startingCardImageView = new ImageView( new Image((Objects.requireNonNull(GUIChooseQuestPageController.class.getResourceAsStream("/cards/" + startingCard.getId() + ".png")))));

        startingCardImageView.setUserData(startingCard);
        startingCardImageView.setPreserveRatio(true);
        startingCardImageView.setFitHeight(boardCardDimension);
        startingCardImageView.setFitWidth(boardCardDimension);

        Button place = new Button();
        place.setPrefHeight(60);
        place.setPrefWidth(120);
        place.setText("Place");
        place.setOnAction(_ ->
                controller.send(new PlaceStartingCardRequest(controller.getMatchId(), startingCard)));

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
        placementOffsetsSetup();
        scoreboardPositionsSetup();

        showStartingCard(startingCard);
    }

    private void placementOffsetsSetup() {
        placementOffsets.put(Position.TOPRIGHT, new int[]{+195, -101});
        placementOffsets.put(Position.BOTTOMLEFT, new int[]{-195, +101});
        placementOffsets.put(Position.BOTTOMRIGHT, new int[]{+195, +101});
        placementOffsets.put(Position.TOPLEFT, new int[]{-195, -101});
    }

    private void questsSetup()  {
        questCardOne.setImage(new Image((Objects.requireNonNull(GUIChooseQuestPageController.class.getResourceAsStream("/cards/" + privateQuest.getId() + ".png")))));
        questCardTwo.setImage(new Image((Objects.requireNonNull(GUIChooseQuestPageController.class.getResourceAsStream("/cards/" + commonQuests.getFirst().getId() + ".png")))));
        questCardThree.setImage(new Image((Objects.requireNonNull(GUIChooseQuestPageController.class.getResourceAsStream("/cards/" + commonQuests.get(1).getId() + ".png")))));
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

    public void updateResources(Map<Resource, Integer> resources){
        for(Resource resource : resources.keySet()){
            Label label = resourceLabels.stream().filter(l -> l.getUserData().equals(resource)).findFirst().orElseThrow(RuntimeException::new);
            label.setText(resources.get(resource).toString());
        }
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

    private void scoreboardPositionsSetup() {}

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

            hooverRectangle.setOnMouseEntered(_ -> hooverRectangle.setOpacity(0.3)); //always highlight when hoovering

            hooverRectangle.setOnMouseExited(_ ->{
                if(!isClicked(hooverRectangle)) hooverRectangle.setOpacity(0); //if rectangle is selected, leave highlight on it, else toggle
            });

            hooverRectangle.setOnMouseClicked(_ -> {
                if(!isClicked(hooverRectangle) && !placeRequestValidating){ //if this rectangle is not the selected one
                    clickedRectangle = hooverRectangle; //set this rectangle as selected
                    clickedCard = (PlaceableCard) hooverRectangle.getParent().getChildrenUnmodifiable()//updaate clickedCard as this specific card
                            .stream()
                            .filter(node -> node.getClass()
                                    .equals(ImageView.class))
                            .map(node-> (ImageView) node)
                            .findFirst()
                            .get()
                            .getUserData();

                    if(matrixCardValidSelection){ //if user already selected a martix card
                        validatePlacing(hooverRectangle);
                    }
                    handCardStackPanes.forEach(stackPane -> stackPane.getChildren().stream().filter(node -> node.getClass().equals(Rectangle.class)).map(node -> ((Rectangle) (node))).forEach(rectangle -> {
                        if(!rectangle.equals(clickedRectangle)) rectangle.setOpacity(0);
                    })); //resets all highlighting, if a new req has been sent, clicked rectangle is null so this function sets all rectangles to non-highlighted
                }else if(!placeRequestValidating){
                    hooverRectangle.setOpacity(0); //rectangle was the selected one, so we reset the state to non-selected
                    clickedCard = null;
                    clickedRectangle = null;
                }
            });
        }
        return hooverRectangles;
    }

    private void validatePlacing(@NotNull Rectangle hooverRectangle) {
        controller.send(new PlaceCardRequest(controller.getMatchId(), clickedCard, (Position)(hooverRectangle.getUserData()), matrixCardId)); //send place request
        placeRequestValidating = true;
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
        cardView.setCursor(Cursor.HAND);

        cardView.setOnMouseClicked(event -> {
            ((PlaceableCard) cardView.getUserData()).swapState();
            cardView.setImage(new Image(getResourcePath(card)));

            setRectanglesOnCard(stackPane, card);

            if(!stackPane.getChildren().stream().filter(node -> node.getClass().equals(Rectangle.class)).filter(node -> node.equals(clickedRectangle)).toList().isEmpty()){
                stackPane.getChildren().stream().filter(node -> node.getClass().equals(Rectangle.class)).forEach(node -> node.setOpacity(0));
                clickedRectangle = null;
            }
        });

        AnchorPane.setTopAnchor(cardView, 0D);
        AnchorPane.setLeftAnchor(cardView, 0D);
        AnchorPane.setBottomAnchor(cardView, 0D);
        AnchorPane.setRightAnchor(cardView, 0D);

        setRectanglesOnCard(stackPane, card);

    }

    private static void setRectanglesOnCard(StackPane stackPane, PlaceableCard card) {
        stackPane.getChildren().stream().filter(node -> node.getClass().equals(Rectangle.class)).forEach(rectangle -> {
            if(card.getStateCardCorners().stream().filter(corner -> !corner.isAvailable()).anyMatch(corner -> corner.getPosition().equals(rectangle.getUserData()))){
                rectangle.setDisable(true);
                rectangle.setOpacity(0);
            }else{
                rectangle.setDisable(false);
            }
        });
    }

    public void placeStartingCard(){
        clearBoard();
        setScrollPannable();
        String retro = startingCard.getStateStartingCard().getClass().equals(FrontStartingCard.class) ? "" : "retro";
        ImageView startingCardImageView = new ImageView(new Image((Objects.requireNonNull(GUIChooseQuestPageController.class.getResourceAsStream("/cards/"+ retro + startingCard.getId() + ".png")))));
        startingCardImageView.setFitHeight(boardCardDimension);
        startingCardImageView.setFitWidth(boardCardDimension);
        startingCardImageView.setPreserveRatio(true);
        AnchorPane.setTopAnchor(startingCardImageView, boardAnchorPane.getPrefHeight()/2 - boardCardDimension/2);
        AnchorPane.setLeftAnchor(startingCardImageView, boardAnchorPane.getPrefWidth()/2 - boardCardDimension/2);
        startingCardImageView.setUserData(startingCard);
        boardAnchorPane.getChildren().add(startingCardImageView);

        setCardHooverEffects(startingCardImageView);
    }

    private void resetAllBoardShadows(){
        boardAnchorPane.getChildren().stream().filter(node -> node.getClass().equals(ImageView.class)).map(node -> (ImageView)(node)).forEach(imageView -> imageView.setEffect(null));
        handCardStackPanes.forEach(stackPane -> stackPane.getChildren().stream().filter(node -> node.getClass().equals(Rectangle.class)).forEach(node -> node.setOpacity(0)));
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

        textFlow.setStyle("-fx-background-color : rgba(255, 251, 230, 0.37)"
                + ";-fx-background-radius : 20px");

        textFlow.setPadding(new Insets(5, 10, 5, 10));
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
        textFlow.setStyle("-fx-background-color : rgba(255, 238, 193, 0.82)"
                + ";-fx-background-radius : 20px");

        textFlow.setPadding(new Insets(5, 10, 5, 10));
        hBox.getChildren().add(textFlow);

        chatVBox.getChildren().add(hBox);
    }


    public void placeCardOnTable(PlaceableCard card){

        placeRequestValidating = false;

        StackPane placedCardStackPane = handCardStackPanes.stream().filter(stackPane -> ((PlaceableCard)(stackPane.getChildren().stream().filter(node -> node.getClass().equals(ImageView.class)).findFirst().get().getUserData())).getId() == card.getId()).findFirst().get();
        placedCardStackPane.getChildren().stream().filter(node -> node.getClass().equals(ImageView.class)).map(node -> (ImageView)(node)).findFirst().get().setImage(null);
        placedCardStackPane.setDisable(true);

        ImageView cardOnTable = new ImageView(new Image(Objects.requireNonNull(GUIMatchPageController.class.getResourceAsStream(getResourcePath(card)))));

        cardOnTable.setFitWidth(boardCardDimension);
        cardOnTable.setFitHeight(boardCardDimension);
        cardOnTable.setPreserveRatio(true);

        setCardHooverEffects(cardOnTable);

        cardOnTable.setLayoutX(Objects.requireNonNull(boardAnchorPane.getChildren().stream().map(node -> (ImageView) (node)).filter(imageView -> ((BaseCard) (imageView.getUserData())).getId() == matrixCardId).findFirst().orElse(null)).getLayoutX() + placementOffsets.get((Position) clickedRectangle.getUserData())[0]);
        cardOnTable.setLayoutY(Objects.requireNonNull(boardAnchorPane.getChildren().stream().map(node -> (ImageView) (node)).filter(imageView -> ((BaseCard) (imageView.getUserData())).getId() == matrixCardId).findFirst().orElse(null)).getLayoutY() + placementOffsets.get((Position) clickedRectangle.getUserData())[1]);

        cardOnTable.setUserData(card);

        boardAnchorPane.getChildren().add(cardOnTable);
        boardAnchorPane.setPrefWidth(Math.max(cardOnTable.getFitHeight(), cardOnTable.getBoundsInParent().getMaxX()));
        boardAnchorPane.setPrefHeight(Math.max(cardOnTable.getFitHeight(), cardOnTable.getBoundsInParent().getMaxY()));

        clickedRectangle = null; //clicked rectangle resets as a new request of placing has been sent
        clickedCard = null; //clicked card resets
        resetAllBoardShadows();
    }

    private void setCardHooverEffects(@NotNull ImageView cardOnTable) {
        cardOnTable.setOnMouseEntered(_ -> {
            cardOnTable.setEffect(new DropShadow());
        });

        cardOnTable.setOnMouseExited(_ -> {
            if(!matrixCardValidSelection){
                cardOnTable.setEffect(null);
            }else if (matrixCardId != ((BaseCard)(cardOnTable.getUserData())).getId()){
                cardOnTable.setEffect(null);
            }
        });

        cardOnTable.setOnMouseClicked(_ -> {
            if(!matrixCardValidSelection){
                matrixCardValidSelection = true;
                matrixCardId = ((BaseCard)(cardOnTable.getUserData())).getId();
                if(clickedCard != null) {
                    validatePlacing(clickedRectangle);
                }
            }else if (matrixCardId == ((BaseCard)cardOnTable.getUserData()).getId()){
                matrixCardValidSelection = false;
            }else{
                matrixCardId = ((BaseCard)(cardOnTable.getUserData())).getId();
            }
        });
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

    public void updateBoard (ArrayList<Player> players) {

    }


}
