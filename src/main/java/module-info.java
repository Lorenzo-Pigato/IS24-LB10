module it.polimi.ingsw.lb10 {
    requires javafx.controls;
    requires javafx.fxml;


    opens it.polimi.ingsw.lb10 to javafx.fxml;
    exports it.polimi.ingsw.lb10;
}