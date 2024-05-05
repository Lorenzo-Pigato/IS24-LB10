module it.polimi.ingsw.lb10 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires org.jetbrains.annotations;
    requires java.smartcardio;
    requires net.bytebuddy;


    opens it.polimi.ingsw.lb10 to javafx.fxml;
    exports it.polimi.ingsw.lb10;
    exports it.polimi.ingsw.lb10.server.model;
    exports it.polimi.ingsw.lb10.server.model.cards;
    exports it.polimi.ingsw.lb10.server.model.cards.decks;
    exports it.polimi.ingsw.lb10.server.model.quest;
    exports it.polimi.ingsw.lb10.server.model.cards.corners;
    exports it.polimi.ingsw.lb10.client.cli.ansi;
    exports it.polimi.ingsw.lb10.server.model.quest.Pattern.Diagonal;
    exports it.polimi.ingsw.lb10.server.model.quest.Pattern.LJ;
    exports it.polimi.ingsw.lb10.network.requests;
    exports it.polimi.ingsw.lb10.server.model.cards.StartingCardState;
    exports it. polimi.ingsw.lb10.server.model.cards.PlaceableCardState;

}