package it.polimi.ingsw.lb10.client.cli;

import it.polimi.ingsw.lb10.client.cli.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiFormat;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.Diagonal.BottomLeftDiagonal;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.Diagonal.TopLeftDiagonal;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.LJ.BottomLeft;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.LJ.BottomRight;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.LJ.TopLeft;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.LJ.TopRight;
import it.polimi.ingsw.lb10.server.model.quest.Quest;

/**
 * This class contains the ASCII art for the game's banners
 */
public abstract class CLIBanner {

    public static void displayCodex(int row){
        new CLIString(
                """
                                         ░███░                                         \s
                                 ░██░░ ░████████████░                                  \s
                              ░███░ ░█░ ██████████░                                    \s
                            ░████░ ░██     ░░█░░         ░██░                          \s
                          ░█████░ ░███░░                 ░█████░░                      \s
                         ░██████  ░█████░          ░█░    ░░█████░       ░█░   ░░░   ░ ░
                         ░█████░  ░██████░      ░██████░   ░█░░████░  ░█████░░░███░░███░
                        ░██████░  ░███████░    ░██░ ░███░░███  ░███░ ██░░███░░ ░█░██░░░\s
                        ░██████░   ░███████░  ░███░  ███░░███   ███░███░ ░██░   ████░░ \s
                        ░██████░    ░███████  ░███░  ███░░███   ███░███░ ██   ░██████░ \s
                        ░███████     ░█████░  ░███░  ███░░███   ███░█████░      ████░  \s
                         ███████░      ░███░   ████░ ███░░████░ ███░░███░      ░████░  ░
                         ░███████░      ██░    ░██████░   ░██████░   █████░░░███░░████░\s
                          ░████████░░█░░█░       ░█░        ░█░░      ░██░  ░░░░  ░██░ \s
                           ░█████████░░░░                                              \s
                            ░███████████░░     ░░██░                                   \s
                              ░░█████████████████░                                     \s
                                  ░██████████░░                                        \s
                        """
        ,AnsiColor.GREEN, 1, row).centerPrint();
    }

    public static void displayCodex(){
        displayCodex(15);
    }

    public static void displayWolf(int col, int row) {
        new CLIString(
                """
                               ░        ▓░    \s
                              ▒██      ░▓█░   \s
                             ░▒░█░     ▒▒░█   \s
                             █░░░█    ░█░░▒▒  \s
                            ▒▒▒██▒▒░░░▒▒██░█░ \s
                            █▓░░░░░░░░░░░░▒▓░ \s
                           ░█░█▓▓▓█░░░█▓▓▓▒▒▒ \s
                            ▒▓▓▓░▒▓▒░▓█░░▓▓▓░ \s
                             ░▒▓▓█▓▒░▓▓█▓█░   \s
                                ▒▓▓▒░▓▓█░     \s
                                 ░▓▒░▓█       \s
                                  █▓▓▓░       \s
                                   ██░        \s
                        """
                , AnsiColor.CYAN, col, row).print();
    }

    public static void displayMushroom(int col, int row) {
        new CLIString(
                """
                                  ░░░         \s
                                █▓▒▒▒██░      \s
                               ▓█▓▒▒▒▒▒▓░     \s
                              ░▓█▓▒▒▒▒▒██     \s
                              ░▓█▓▒▒▒▒▒██     \s
                             ▒▓██▓▒▒▒▒▒▒█▒    \s
                           ░▓████▓▓█▒▒▒▒▒█▓▒  \s
                         ░▓▓████████████████▓▒\s
                         ░▒▒▒▒▒▒▒█▓▓▓▓▒▒▒▒▒▒▒░\s
                                 ░▓▓▓▓        \s
                                 ▒▓██▓        \s
                                 ▒███▓        \s
                                  ▒█▒         \s
                        """
                , AnsiColor.RED, col, row).print();
    }

    public static void displayButterfly(int col, int row) {
        new CLIString(
                """
                         ▒█░                ▓█\s
                        ▒██▓░             ▓████
                        ░▓█████▓░ ▒ ░░░███████▒
                         ▒███▓▓▓██ █░█▓▓▓▓▓██▓\s
                          ▓█▓▓█▓██▓█▓██▓██▓█▓░\s
                           ▒▓▓████▓▓▓█████▓█  \s
                              ░▓██▓▓▓██▓▒░    \s
                               ░▓▓▓▓▓▓▓▒      \s
                            ░██████▓▓▓███▓▒   \s
                          █▓██████░▓▒▒▓██████ \s
                         ▒██▓▓███  █░ ▒███▓██▓\s
                          █▓▓██▓       ████▓▓░\s
                             ░           ░░   \s
                        """
                , AnsiColor.PURPLE, col, row).print();
    }

    public static void displayLeaf(int col, int row) {
        new CLIString(

                """
                                    ░     \s
                                 ░██▓         \s
                                ░█▒▒▒█        \s
                         ░░     █▒██▒██     ░░\s
                         ▓████░░█▒█▓▒▒▓░▒▓███▒\s
                         ▓█▒▒▒█▓█▒█▓▒█▓█▒▒▒▒█▒\s
                         ██▒▒▒▒▒█▓█▓█▓█▒▒▒▒▒▓░\s
                          ▓█▒▒▒▒▒█▓▓▓▒▒▒▒▒▒██ \s
                           ██▒▒▒▒▒█▓█▒▒▒▒▒█▒  \s
                            ░▓█▒▒▒█▓▒▒▒▒█▓    \s
                              ░▒███▓███▒      \s
                                  ░█          \s
                                  ▓░          \s
                        """
                , AnsiColor.GREEN, col, row).print();
    }

    public static void display404(){
        new CLIString(
                """
                        ┌───────────────────────────┐
                        │                           │
                        │ ██╗  ██╗ ██████╗ ██╗  ██╗ │
                        │ ██║  ██║██╔═████╗██║  ██║ │
                        │ ███████║██║██╔██║███████║ │
                        │ ╚════██║████╔╝██║╚════██║ │
                        │      ██║╚██████╔╝     ██║ │
                        │      ╚═╝ ╚═════╝      ╚═╝ │
                        │                           │
                        └───────────────────────────┘
                        """
                , AnsiColor.CYAN, 1, 20).centerPrint();
    }

    public static void displayLogin(){
        new CLIString(
                """
                        ┌─────────────────────────────────────────┐
                        │                                         │
                        │ ██╗      ██████╗  ██████╗ ██╗███╗   ██╗ │
                        │ ██║     ██╔═══██╗██╔════╝ ██║████╗  ██║ │
                        │ ██║     ██║   ██║██║  ███╗██║██╔██╗ ██║ │
                        │ ██║     ██║   ██║██║   ██║██║██║╚██╗██║ │
                        │ ███████╗╚██████╔╝╚██████╔╝██║██║ ╚████║ │
                        │ ╚══════╝ ╚═════╝  ╚═════╝ ╚═╝╚═╝  ╚═══╝ │
                        │                                         │
                        └─────────────────────────────────────────┘
                        """
                , AnsiColor.GREEN, 1, 20).centerPrint();
    }

    public static void displayError(){
        new CLIString(
                """
                        ┌───────────────────────────────────────────┐
                        │                                           │
                        │ ███████╗██████╗ ██████╗  ██████╗ ██████╗  │
                        │ ██╔════╝██╔══██╗██╔══██╗██╔═══██╗██╔══██╗ │
                        │ █████╗  ██████╔╝██████╔╝██║   ██║██████╔╝ │
                        │ ██╔══╝  ██╔══██╗██╔══██╗██║   ██║██╔══██╗ │
                        │ ███████╗██║  ██║██║  ██║╚██████╔╝██║  ██║ │
                        │ ╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝ ╚═╝  ╚═╝ │
                        │                                           │
                        └───────────────────────────────────────────┘
                        """,
                AnsiColor.RED, 1, 20).centerPrint();

    }

    public static void displayError(int col, int row){
        new CLIString(
                """
                        ┌───────────────────────────────────────────┐
                        │                                           │
                        │ ███████╗██████╗ ██████╗  ██████╗ ██████╗  │
                        │ ██╔════╝██╔══██╗██╔══██╗██╔═══██╗██╔══██╗ │
                        │ █████╗  ██████╔╝██████╔╝██║   ██║██████╔╝ │
                        │ ██╔══╝  ██╔══██╗██╔══██╗██║   ██║██╔══██╗ │
                        │ ███████╗██║  ██║██║  ██║╚██████╔╝██║  ██║ │
                        │ ╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝ ╚═╝  ╚═╝ │
                        │                                           │
                        └───────────────────────────────────────────┘
                        """,
                AnsiColor.RED, col, row).print();

    }

    public static void displayServer(){
        new CLIString(
                """
                        ┌───────────────────────────────────────────────────┐
                        │                                                   │
                        │ ███████╗███████╗██████╗ ██╗   ██╗███████╗██████╗  │
                        │ ██╔════╝██╔════╝██╔══██╗██║   ██║██╔════╝██╔══██╗ │
                        │ ███████╗█████╗  ██████╔╝██║   ██║█████╗  ██████╔╝ │
                        │ ╚════██║██╔══╝  ██╔══██╗╚██╗ ██╔╝██╔══╝  ██╔══██╗ │
                        │ ███████║███████╗██║  ██║ ╚████╔╝ ███████╗██║  ██║ │
                        │ ╚══════╝╚══════╝╚═╝  ╚═╝  ╚═══╝  ╚══════╝╚═╝  ╚═╝ │
                        │                                                   │
                        └───────────────────────────────────────────────────┘
                        """,
                AnsiColor.CYAN, 13, 2).print();
    }

    public static void displayWaitingRoom(){
        new CLIString(
                """
                        ┌───────────────────────────────────────────────────────────────────────────────────────────────┐
                        │                                                                                               │
                        │ ██╗    ██╗ █████╗ ██╗████████╗██╗███╗   ██╗ ██████╗     ██████╗  ██████╗  ██████╗ ███╗   ███╗ │
                        │ ██║    ██║██╔══██╗██║╚══██╔══╝██║████╗  ██║██╔════╝     ██╔══██╗██╔═══██╗██╔═══██╗████╗ ████║ │
                        │ ██║ █╗ ██║███████║██║   ██║   ██║██╔██╗ ██║██║  ███╗    ██████╔╝██║   ██║██║   ██║██╔████╔██║ │
                        │ ██║███╗██║██╔══██║██║   ██║   ██║██║╚██╗██║██║   ██║    ██╔══██╗██║   ██║██║   ██║██║╚██╔╝██║ │
                        │ ╚███╔███╔╝██║  ██║██║   ██║   ██║██║ ╚████║╚██████╔╝    ██║  ██║╚██████╔╝╚██████╔╝██║ ╚═╝ ██║ │
                        │  ╚══╝╚══╝ ╚═╝  ╚═╝╚═╝   ╚═╝   ╚═╝╚═╝  ╚═══╝ ╚═════╝     ╚═╝  ╚═╝ ╚═════╝  ╚═════╝ ╚═╝     ╚═╝ │
                        │                                                                                               │
                        └───────────────────────────────────────────────────────────────────────────────────────────────┘
                        """,
                AnsiColor.CYAN, 13, 20).centerPrint();
    }

    public static void displayChooseQuest() {
        new CLIString(
                """
                        ┌───────────────────────────────────────────────────────────────────────────────────────────────────────────────┐
                        │                                                                                                               │
                        │  ██████╗██╗  ██╗ ██████╗  ██████╗ ███████╗███████╗     █████╗      ██████╗ ██╗   ██╗███████╗███████╗████████╗ │
                        │ ██╔════╝██║  ██║██╔═══██╗██╔═══██╗██╔════╝██╔════╝    ██╔══██╗    ██╔═══██╗██║   ██║██╔════╝██╔════╝╚══██╔══╝ │
                        │ ██║     ███████║██║   ██║██║   ██║███████╗█████╗      ███████║    ██║   ██║██║   ██║█████╗  ███████╗   ██║    │
                        │ ██║     ██╔══██║██║   ██║██║   ██║╚════██║██╔══╝      ██╔══██║    ██║▄▄ ██║██║   ██║██╔══╝  ╚════██║   ██║    │
                        │ ╚██████╗██║  ██║╚██████╔╝╚██████╔╝███████║███████╗    ██║  ██║    ╚██████╔╝╚██████╔╝███████╗███████║   ██║    │
                        │  ╚═════╝╚═╝  ╚═╝ ╚═════╝  ╚═════╝ ╚══════╝╚══════╝    ╚═╝  ╚═╝     ╚══▀▀═╝  ╚═════╝ ╚══════╝╚══════╝   ╚═╝    │
                        │                                                                                                               │
                        └───────────────────────────────────────────────────────────────────────────────────────────────────────────────┘
                        """,
        AnsiColor.CYAN, 13, 10).centerPrint();
    }

    public static void displayNumber(int col, int row, int num){
        switch (num){
            case 1:
                new CLIString(
                        """
                                 ██╗
                                ███║
                                ╚██║
                                 ██║
                                 ██║
                                 ╚═╝
                                """,
                AnsiColor.WHITE, col, row).print();
                break;
            case 2:
                new CLIString(
                        """
                                ██████╗\s
                                ╚════██╗
                                 █████╔╝
                                ██╔═══╝\s
                                ███████╗
                                ╚══════╝
                                """,
                AnsiColor.WHITE, col, row).print();
                break;
            case 3:
                new CLIString(
                        """
                                ██████╗\s
                                ╚════██╗
                                 █████╔╝
                                 ╚═══██╗
                                ██████╔╝
                                ╚═════╝\s
                                """,
                AnsiColor.WHITE, col, row).print();
                break;
            case 4:
                new CLIString(
                        """
                                ██╗  ██╗
                                ██║  ██║
                                ███████║
                                ╚════██║
                                     ██║
                                     ╚═╝
                                """,
                AnsiColor.WHITE, col, row).print();
                break;
        }
    }

    public static void printSymbol(int col, int row, char ch){
        switch (ch){
            case '=':
                new CLIString(
                        """
                                ███████╗
                                ╚══════╝
                                ███████╗
                                ╚══════╝
                                """,
                AnsiColor.WHITE, col, row).print();
                break;
            case 'x':
                new CLIString(
                        """
                                ██╗  ██╗
                                ╚██╗██╔╝
                                 ╚███╔╝\s
                                 ██╔██╗\s
                                ██╔╝ ██╗
                                ╚═╝  ╚═╝
                                """,
                AnsiColor.WHITE, col, row).print();
                break;
            case 'p':
                new CLIString(
                        """
                                ██████╗ ████████╗
                                ██╔══██╗╚══██╔══╝
                                ██████╔╝   ██║  \s
                                ██╔═══╝    ██║  \s
                                ██║        ██║  \s
                                ╚═╝        ╚═╝  \s
                                """,
                AnsiColor.WHITE, col, row).print();
                break;
        }
    }

    public static void displayQuest(Object quest, int col, int row) {
        if (quest instanceof BottomLeftDiagonal) {
            new CLIString(
                    """
                                      ███████╗\s
                                 █████▒▒█████║\s
                            █████▒▒█████╔════╝\s
                            ███████╔════╝     \s
                            ╚══════╝
                            """,
                    ((BottomLeftDiagonal) quest).getColor().getAnsi(), col, row).print();
        }
        else if(quest instanceof TopLeftDiagonal) {
            new CLIString(
                    """
                            ███████           \s
                            █████▒▒█████      \s
                            ╚═══╗█████▒▒█████╗\s
                                ╚════╗███████║\s
                                     ╚═══════╝\s
                            """,
                    ((TopLeftDiagonal) quest).getColor().getAnsi(), col, row).print();
        }
        else if(quest instanceof BottomRight){
            new CLIString(
                    """
                             ▒▒█████╗\s
                            ╗███████║\s
                            ╚═══════╝\s
                            """,
                    ((BottomRight) quest).getToeColor().getAnsi(), col+6, row+4).print();
            new CLIString(
                    """
                            ███████╗
                            ███████║
                            ╚══════╝
                            ███████╗
                            █████
                            ╚═══
                            """,
                    ((BottomRight) quest).getBodyColor().getAnsi(), col+2, row).print();
        }
        else if(quest instanceof BottomLeft){
            new CLIString(
                    """
                            ███████╗
                            ███████║
                            ╚══════╝
                            ███████╗
                              █████║
                              ╔════╝
                            """,
                    ((BottomLeft) quest).getBodyColor().getAnsi(), col+7, row).print();
            new CLIString(
                    """
                            █████▒▒
                            ███████
                            ╚══════╝
                            """,
                    ((BottomLeft) quest).getToeColor().getAnsi(), col+2, row+4).print();
        }
        else if(quest instanceof TopLeft){
            new CLIString(
                    """
                            ▒▒█████╗
                            ███████║
                            ╚══════╝
                            ███████╗
                            ███████║
                            ╚══════╝
                            """,
                    ((TopLeft) quest).getBodyColor().getAnsi(), col+7, row+1).print();
            new CLIString(
                    """
                            ███████
                            █████
                            ╚════
                            """,
                    ((TopLeft) quest).getToeColor().getAnsi(), col+2, row).print();
        }
        else if(quest instanceof TopRight){
            new CLIString(
                    """
                            ███████╗
                              █████║
                               ════╝
                            """,
                    ((TopRight) quest).getToeColor().getAnsi(), col+7, row).print();
            new CLIString(
                    """
                            █████▒▒
                            ███████╔
                            ╚══════╝
                            ███████╗
                            ███████║
                            ╚══════╝
                            """,
                    ((TopRight) quest).getBodyColor().getAnsi(), col+2, row+1).print();
        }
    }
}