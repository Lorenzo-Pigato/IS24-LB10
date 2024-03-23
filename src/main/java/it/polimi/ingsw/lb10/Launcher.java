package it.polimi.ingsw.lb10;

import it.polimi.ingsw.lb10.client.view.LauncherView;

public class Launcher {
    public static void main(String[] args) {
        String mode = LauncherView.runLauncherPage();

        if(mode.split(":")[0].equals("client")) ClientApp.main(mode.split(":"));
        else ServerApp.main(null);
    }
}
