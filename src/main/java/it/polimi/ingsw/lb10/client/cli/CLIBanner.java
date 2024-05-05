package it.polimi.ingsw.lb10.client.cli;

import it.polimi.ingsw.lb10.client.cli.ansi.AnsiColor;
import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.Diagonal.BottomLeftDiagonal;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.Diagonal.TopLeftDiagonal;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.LJ.BottomLeft;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.LJ.BottomRight;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.LJ.TopLeft;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.LJ.TopRight;
import it.polimi.ingsw.lb10.server.model.quest.QuestCounter;


/**
 * This class contains the ASCII art for the game's banners
 */
public abstract class CLIBanner {

    public static void displayCodex(int row) {
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
                , AnsiColor.GREEN, 1, row).centerPrint();
    }

    public static void displayCodex() {
        displayCodex(15);
    }

    public static void displayConnection() {
        new CLIString(
                """
                        ┌────────────────────────────────────────────────────────────────────────────────────────────────┐
                        │                                                                                                │
                        │ ███████╗██╗███╗   ██╗██████╗      █████╗     ███████╗███████╗██████╗ ██╗   ██╗███████╗██████╗  │
                        │ ██╔════╝██║████╗  ██║██╔══██╗    ██╔══██╗    ██╔════╝██╔════╝██╔══██╗██║   ██║██╔════╝██╔══██╗ │
                        │ █████╗  ██║██╔██╗ ██║██║  ██║    ███████║    ███████╗█████╗  ██████╔╝██║   ██║█████╗  ██████╔╝ │
                        │ ██╔══╝  ██║██║╚██╗██║██║  ██║    ██╔══██║    ╚════██║██╔══╝  ██╔══██╗╚██╗ ██╔╝██╔══╝  ██╔══██╗ │
                        │ ██║     ██║██║ ╚████║██████╔╝    ██║  ██║    ███████║███████╗██║  ██║ ╚████╔╝ ███████╗██║  ██║ │
                        │ ╚═╝     ╚═╝╚═╝  ╚═══╝╚═════╝     ╚═╝  ╚═╝    ╚══════╝╚══════╝╚═╝  ╚═╝  ╚═══╝  ╚══════╝╚═╝  ╚═╝ │
                        │                                                                                                │
                        └────────────────────────────────────────────────────────────────────────────────────────────────┘
                        """
                , AnsiColor.CYAN, 1, 20).centerPrint();
    }

    public static void display404() {
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

    public static void displayLogin() {
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

    public static void displayError() {
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

    public static void displayError(int col, int row) {
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

    public static void displayServer() {
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

    public static void displayWaitingRoom() {
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

    public static void displayResource(int col, int row, Resource resource) {
        if (resource.equals(Resource.PLANT)) {
            new CLIString(
                    """
                                     ▄▄
                                    ▟▓▒▙
                                   █▓▒▒▒█
                             ▄▄▄▄▂ █▓▒▒▒█ ▂▄▄▄▄
                             █▒▓▓▒▇█▒██▒█▇▒▓▓▒█
                             ▜▒▒▓▓▒▒████▒▒▓▓▒▒▛
                              ▀█▒▒▓▓▒██▒▓▓▒▒█▀
                                ▀█▒▒▒██▒▒▒█▀
                                  ▔▀████▀▔
                                     ██
                            """
                    , AnsiColor.GREEN, col, row).print();
        } else if (resource.equals(Resource.ANIMAL)) {
            new CLIString(
                    """
                                  ▄      ▄
                                 ▟▒▙    ▟▒▙
                                ▟▓▒█    █▓▒▙
                               ▟█▒▒█▙  ▟█▒▒█▙
                               █▓▓▒▒▓▓▓▓▒▒▓▓█
                               ▓█████▒▒█████▓
                               ▜█░▚░█▒▓█░▚░█▛
                                ▀▜█▓█▒▓█▓█▛▀
                                  ▀▜█▒▓█▛▀
                                    ▜██▛
                            """
                    , AnsiColor.CYAN, col, row).print();
        } else if (resource.equals(Resource.MUSHROOM)) {
            new CLIString(
                    """
                                 ▃██████▃
                                ▐█▒▓▒▒▒▒█▌
                                ▟▒▒▓▒▒▒▒▒▙
                               ▃█▒▒▓▒▒▒▒▒█▃
                              ▃█▒▒▒▒▓▓▓▓▓▓█▃
                              ▀▀▀▀▜█▒▒█▛▀▀▀▀
                                  ▐████▌
                                   █▒▒█
                                   █▒▒█
                                   ▜██▛
                            """
                    , AnsiColor.RED, col, row).print();
        } else if (resource.equals(Resource.INSECT)) {
            new CLIString(
                    """
                             ▂▄▄   ▐    ▌   ▄▄▂
                            ▐█▓▓█▄  ▌  ▐  ▄█▓▓█▌
                             ▜█▓▒▒█ █▌▐█ █▒▒▓█▛
                              ▜█▓▒▒█▓██▓█▒▒▓█▛
                               ▜███ ▓██▓ ███▛
                                ▄ ▜ ▄██▄ ▛ ▄
                              ▟███▙▄████▄▟███▙
                             ▐██▒▒▒█▓██▓█▒▒▒██▌
                              ▜██▓█▛ ▜▛ ▜█▓██▛
                               ▜██▛      ▜██▛
                            """
                    , AnsiColor.PURPLE, col, row).print();
        } else if (resource.equals(Resource.POTION)) {
            new CLIString(
                    """ 
                              ▆▆▆▆▆▆▆▆▆▆
                             ▜▌▀▀▀▀▀▀▀▀▐▛
                              ▌        ▐
                              ▜▄▄▄▄▄▄▄▄▛
                            ▟▀▀        ▀▀▙
                            ▌▄▂          █
                            ▌███▄        █
                            ▌█▓▓██▄▂     █
                            ▌█░░░▓▓▓█▙▂  █
                            ██████████████
                            """
                    , AnsiColor.YELLOW, col, row).print();
        } else if (resource.equals(Resource.PERGAMENA)) {
            new CLIString(
                    """
                             ▃▃▃▃▃▃▃▃▃▃▃▃
                            █▓░█▒▒▒▒▒▒▒▒██▙
                            █▓░░█▒▒▒▒▒▒▒▒▒█
                            ▜████▓▓▓███████▌
                                 ▐██▒▒▒▒▒▒▒█▌
                                 ██▒▒▒▒▒▒▒▒█▌
                                ▐███████████▙
                                ▐█░█▒▒▒▒▒▒▒▒▒█▌
                                 ▜██▒▒▒▒▒▒▒▒▒█▌
                                   ▀▀▀▀▀▀▀▀▀▀▀
                            """
                    , AnsiColor.YELLOW, col, row).print();
        } else {
            new CLIString(
                    """
                                       ▟████▙
                                     ▟██░░░░█
                                   ▟██░░▒░░█▛
                                 ▟██░░▒▒░░█▛
                                ▟██░░▒░░█▛
                                █░░▒░░█▛
                               ▟█████▛
                              ▃██▀
                             ▃█▀
                             ▀
                            """
                    , AnsiColor.YELLOW, col, row).print();
        }

    }

    public static void displayNumber(int col, int row, int num, AnsiColor color) {
        switch (num) {
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
                        color, col, row).print();
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
                        color, col, row).print();
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
                        color, col, row).print();
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
                        color, col, row).print();
                break;
        }
    }

    public static void displaySymbol(int col, int row, char ch) {
        switch (ch) {
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
                                ██╗██╗
                                ╚███╔╝
                                ██╔██╗
                                ╚═╝╚═╝
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
                    ((BottomLeftDiagonal) quest).getColor().getAnsi(), col + 1, row).print();
        } else if (quest instanceof TopLeftDiagonal) {
            new CLIString(
                    """
                            ███████           \s
                            █████▒▒█████      \s
                            ╚═══╗█████▒▒█████╗\s
                                ╚════╗███████║\s
                                     ╚═══════╝\s
                            """,
                    ((TopLeftDiagonal) quest).getColor().getAnsi(), col + 1, row).print();
        } else if (quest instanceof BottomRight) {
            new CLIString(
                    """
                            ▒▒█████╗\s
                            ███████║\s
                            ╚══════╝\s
                            """,
                    ((BottomRight) quest).getToeColor().getAnsi(), col + 11, row + 4).print();
            new CLIString(
                    """
                            ███████╗
                            ███████║
                            ╚══════╝
                            ███████╗
                            █████
                            ╚════
                            """,
                    ((BottomRight) quest).getBodyColor().getAnsi(), col + 6, row).print();
        } else if (quest instanceof BottomLeft) {
            new CLIString(
                    """
                            ███████╗
                            ███████║
                            ╚══════╝
                            ███████╗
                              █████║
                              ╔════╝
                            """,
                    ((BottomLeft) quest).getBodyColor().getAnsi(), col + 6, row).print();
            new CLIString(
                    """
                            █████▒▒
                            ███████
                            ╚══════╝
                            """,
                    ((BottomLeft) quest).getToeColor().getAnsi(), col + 1, row + 4).print();
        } else if (quest instanceof TopLeft) {
            new CLIString(
                    """
                            ▒▒█████╗
                            ███████║
                            ╚══════╝
                            ███████╗
                            ███████║
                            ╚══════╝
                            """,
                    ((TopLeft) quest).getBodyColor().getAnsi(), col + 6, row + 1).print();
            new CLIString(
                    """
                            ███████
                            █████
                            ╚════
                            """,
                    ((TopLeft) quest).getToeColor().getAnsi(), col + 1, row).print();
        } else if (quest instanceof TopRight) {
            new CLIString(
                    """
                            ███████╗
                              █████║
                               ════╝
                            """,
                    ((TopRight) quest).getToeColor().getAnsi(), col + 11, row).print();
            new CLIString(
                    """
                            █████▒▒
                            ███████╔
                            ╚══════╝
                            ███████╗
                            ███████║
                            ╚══════╝
                            """,
                    ((TopRight) quest).getBodyColor().getAnsi(), col + 6, row + 1).print();
        } else if (quest instanceof QuestCounter) {
            if (((QuestCounter) quest).getId() != 94) {
                int center = col + 7;
                ((QuestCounter) quest).getActivationCost().keySet()
                        .stream()
                        .filter(res -> ((QuestCounter) quest).getActivationCost().get(res) > 0)
                        .findFirst()
                        .ifPresent(
                                res -> {
                                    CLIBanner.displayNumber(center - 12, row, ((QuestCounter) quest).getActivationCost().get(res), AnsiColor.WHITE);
                                    CLIBanner.displaySymbol(center, row + 1, 'x');
                                    CLIBanner.displayResource(center + 8, row - 2, res);
                                }
                        );

            } else { // Quest 94 requires a feather, a pergamena and a potion, is the only one not included in the previous pattern
                int center = col + 2;
                CLIBanner.displayResource(center - 18, row - 2, Resource.FEATHER);
                CLIBanner.displayResource(center + 15, row - 2, Resource.PERGAMENA);
                CLIBanner.displayResource(center, row - 2, Resource.POTION);
            }
        }
    }

    public static void displayWinner() {
        new CLIString(
                """                     
                        ┌──────────────────────────────────────────────────────────────────────┐
                        │                                                                      │
                        │ ██╗    ██╗   ██╗ ██████╗ ██╗   ██╗    ██╗    ██╗██╗███╗   ██╗    ██╗ │
                        │ ██║    ╚██╗ ██╔╝██╔═══██╗██║   ██║    ██║    ██║██║████╗  ██║    ██║ │
                        │ ██║     ╚████╔╝ ██║   ██║██║   ██║    ██║ █╗ ██║██║██╔██╗ ██║    ██║ │
                        │ ╚═╝      ╚██╔╝  ██║   ██║██║   ██║    ██║███╗██║██║██║╚██╗██║    ╚═╝ │
                        │ ██╗       ██║   ╚██████╔╝╚██████╔╝    ╚███╔███╔╝██║██║ ╚████║    ██╗ │
                        │ ╚═╝       ╚═╝    ╚═════╝  ╚═════╝      ╚══╝╚══╝ ╚═╝╚═╝  ╚═══╝    ╚═╝ │
                        │                                                                      │
                        └──────────────────────────────────────────────────────────────────────┘
                        """,
                AnsiColor.GREEN, 0, 10).centerPrint();

    }

    public static void displayLoser() {
        new CLIString(
                """                     
                        ┌──────────────────────────────────────────────────────────────────┐
                        │                                                                  │
                        │ ██╗   ██╗ ██████╗ ██╗   ██╗    ██╗      ██████╗ ███████╗███████╗ │
                        │ ╚██╗ ██╔╝██╔═══██╗██║   ██║    ██║     ██╔═══██╗██╔════╝██╔════╝ │
                        │  ╚████╔╝ ██║   ██║██║   ██║    ██║     ██║   ██║███████╗█████╗   │
                        │   ╚██╔╝  ██║   ██║██║   ██║    ██║     ██║   ██║╚════██║██╔══╝   │
                        │    ██║   ╚██████╔╝╚██████╔╝    ███████╗╚██████╔╝███████║███████╗ │
                        │    ╚═╝    ╚═════╝  ╚═════╝     ╚══════╝ ╚═════╝ ╚══════╝╚══════╝ │
                        │                                                                  │
                        └──────────────────────────────────────────────────────────────────┘
                        """,
                AnsiColor.RED, 0, 10).centerPrint();

    }
}