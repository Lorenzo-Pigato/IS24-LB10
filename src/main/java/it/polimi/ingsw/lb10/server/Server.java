package it.polimi.ingsw.lb10.server;

import it.polimi.ingsw.lb10.client.cli.CLIBanner;
import it.polimi.ingsw.lb10.client.cli.CLIBox;
import it.polimi.ingsw.lb10.client.cli.CLICommand;
import it.polimi.ingsw.lb10.client.cli.CLIString;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiFormat;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiString;
import it.polimi.ingsw.lb10.network.ClientConnection;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
    private final ServerSocket serverSocket;
    private final int port;
    private final ExecutorService welcomeExecutor;
    private static final ArrayDeque<CLIString> logs = new ArrayDeque<>();
    private static BufferedWriter bufferedWriter;
    private boolean active = true;


    public Server(ServerSocket serverSocket, int port) {
        this.serverSocket = serverSocket;
        this.port = port;
        this.welcomeExecutor = Executors.newCachedThreadPool();
    }

    public int getPort() {
        return port;
    }

    //starts the Server thread listening to all client connections, then sends socket client to connection handlers
    public void run() {
        setPage();
        setLogFile();
        while (active) {
            try {
                Socket clientSocket = serverSocket.accept();
                welcomeExecutor.submit(new ClientConnection(clientSocket));
            } catch (IOException e) {
                displayError();
                CLICommand.setPosition(2, 49);
                AnsiString.print(">> Error: " + e.getMessage(), AnsiColor.RED);
                active = false;
            }
        }
    }

    private void setPage() {
        CLICommand.initialize(80, 50);
        CLIBanner.displayServer();
        CLIBox.draw(1, 15, 80, 30, AnsiColor.CYAN);

        CLICommand.setPosition(2, 14);

        AnsiString.print("Status: ", AnsiColor.CYAN);
        AnsiString.print("ONLINE", AnsiColor.GREEN, AnsiFormat.BOLD);

        AnsiString.print("\t\tServer running on port: ", AnsiColor.CYAN);
        AnsiString.print(this.getPort() + "", AnsiColor.YELLOW, AnsiFormat.BOLD);
    }

    private void displayError() {
        CLICommand.home();
        CLICommand.clearNextLines(14);
        CLIBanner.displayError(17, 2);

        CLICommand.setPosition(2, 14);

        AnsiString.print("Status: ", AnsiColor.CYAN);
        AnsiString.print("OFFLINE", AnsiColor.RED, AnsiFormat.BOLD);
    }

    /**
     * This function is used to display logs on server application
     * Log list is limited to last 28 logs because of window size
     * When overflow occurs, older logs will be replaced by new logs
     *
     * @param log is used to store the log string
     */
    public static void log(String log) {
        saveLog(log);
        if (logs.size() == 28) {
            logs.removeFirst();

            logs.addLast(new CLIString(log.split("\n")[0],
                    AnsiColor.DEFAULT,
                    AnsiFormat.DEFAULT,
                    2, 44, 73)); //add last log entry on line 44

            logs.forEach(l -> {
                l.reposition(2, l.getPosition()[1] - 1);
                l.print();
            });
        } else {
            logs.addLast(new CLIString(log.split("\n")[0],
                    AnsiColor.DEFAULT,
                    AnsiFormat.DEFAULT,
                    2, logs.size() + 16, 73));
            logs.getLast().print();
        }
    }

    private void setLogFile() {
        String logPath = "log_" +
                LocalDateTime.now().getMonth() +
                LocalDateTime.now().getDayOfMonth() +
                ".txt";

        try {
            FileWriter fileWriter = new FileWriter(logPath, true);
            bufferedWriter = new BufferedWriter(fileWriter);
        } catch (IOException e) {
            AnsiString.print(">> Error creating log file\n" + e.getMessage(), AnsiColor.RED);
        }
    }

    private static void saveLog(String log) {
        try {
            bufferedWriter.write(log + "\n");
            bufferedWriter.flush();
        } catch (IOException e) {
            AnsiString.print(">> Error appending log to file\nAborting", AnsiColor.RED);
        }
    }

}
