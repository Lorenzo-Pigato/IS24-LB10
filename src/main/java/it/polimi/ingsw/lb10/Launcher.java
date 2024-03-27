package it.polimi.ingsw.lb10;

import it.polimi.ingsw.lb10.client.view.LauncherView;

public class Launcher {
    public static void main(String[] args) {
        String mode = LauncherView.runLauncherPage();

        if(mode.split(":")[0].equals("client")) ClientLauncher.main(mode.split(":"));
        else ServerLauncher.main(null);
    }
}
