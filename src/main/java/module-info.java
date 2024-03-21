module it.polimi.ingsw.lb10 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;


    opens it.polimi.ingsw.lb10 to javafx.fxml;
    exports it.polimi.ingsw.lb10;
    exports it.polimi.ingsw.lb10.server.model;
}