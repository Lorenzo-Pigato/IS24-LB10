package it.polimi.ingsw.lb10;

import it.polimi.ingsw.lb10.client.view.LauncherView;

/**
 * This class is used to launch the application
 * It will ask the user to choose between server and client
 * If client is chosen, the user will be asked for a graphic or command line interface
 * If server is chosen, the user will be asked for a port to start the server
 */
public class Launcher {
    public static void main(String[] args) {
        // LauncherView returns the String "mode", which is formatted as client:ui or server:port
        String mode = LauncherView.runLauncherPage();

        if(mode.split(":")[0].equals("client")) ClientLauncher.main(mode.split(":"));
        else ServerLauncher.main(mode.split(":"));
    }
}
