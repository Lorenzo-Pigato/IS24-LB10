module it.polimi.ingsw.lb10 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires org.jetbrains.annotations;


    opens it.polimi.ingsw.lb10 to javafx.fxml;
    exports it.polimi.ingsw.lb10;
    exports it.polimi.ingsw.lb10.server.model;
    exports it.polimi.ingsw.lb10.server.model.cards;
    exports it.polimi.ingsw.lb10.server.model.cards.decks;
    exports it.polimi.ingsw.lb10.server.model.quest;
    exports it.polimi.ingsw.lb10.server.model.cards.corners;
    exports it.polimi.ingsw.lb10.server.model.DrawType;
    exports it.polimi.ingsw.lb10.server.model.quest.Pattern.Diagonal;
    exports it.polimi.ingsw.lb10.server.model.quest.Pattern;

}