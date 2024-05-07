package it.polimi.ingsw.lb10.client.gui;




public class GUILoginPage implements GUIPageController {

    @Override
    public String getFXML() {
        return "/fxml/LoginPage.fxml";
    }

    @Override
    public String getCSS() {
        return "/css/LoginPage.css";
    }

}
