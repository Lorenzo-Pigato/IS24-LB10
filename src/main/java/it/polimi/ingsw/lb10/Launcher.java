package it.polimi.ingsw.lb10;

import it.polimi.ingsw.lb10.client.view.LauncherView;

public class Launcher {
    public static void main(String[] args) {
        String result = LauncherView.runLauncherPage();

        if(result.equals("client")) ClientApp.main(null);
        else ServerApp.main(null);
    }
}
