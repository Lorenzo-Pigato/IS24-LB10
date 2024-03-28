package it.polimi.ingsw.lb10.client.clidesign;

import it.polimi.ingsw.lb10.client.clidesign.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.clidesign.ansi.AnsiString;

/**
 * This class contains the ASCII art for the game's banners
 */
public abstract class CLIBanner {

    public static void displayCodex(int row){
        new CLIString(
            "                 ░███░                                          \n" +
            "         ░██░░ ░████████████░                                   \n" +
            "      ░███░ ░█░ ██████████░                                     \n" +
            "    ░████░ ░██     ░░█░░         ░██░                           \n" +
            "  ░█████░ ░███░░                 ░█████░░                       \n" +
            " ░██████  ░█████░          ░█░    ░░█████░       ░█░   ░░░   ░ ░\n" +
            " ░█████░  ░██████░      ░██████░   ░█░░████░  ░█████░░░███░░███░\n" +
            "░██████░  ░███████░    ░██░ ░███░░███  ░███░ ██░░███░░ ░█░██░░░ \n" +
            "░██████░   ░███████░  ░███░  ███░░███   ███░███░ ░██░   ████░░  \n" +
            "░██████░    ░███████  ░███░  ███░░███   ███░███░ ██   ░██████░  \n" +
            "░███████     ░█████░  ░███░  ███░░███   ███░█████░      ████░   \n" +
            " ███████░      ░███░   ████░ ███░░████░ ███░░███░      ░████░  ░\n" +
            " ░███████░      ██░    ░██████░   ░██████░   █████░░░███░░████░ \n" +
            "  ░████████░░█░░█░       ░█░        ░█░░      ░██░  ░░░░  ░██░  \n" +
            "   ░█████████░░░░                                               \n" +
            "    ░███████████░░     ░░██░                                    \n" +
            "      ░░█████████████████░                                      \n" +
            "          ░██████████░░                                         \n"
        ,AnsiColor.GREEN, 1, row).centerPrint();
    }

    public static void displayCodex(){
        displayCodex(15);
    }

    public static void displayWolf(int col, int row) {
        new CLIString(
                "       ░        ▓░     \n" +
                        "      ▒██      ░▓█░    \n" +
                        "     ░▒░█░     ▒▒░█    \n" +
                        "     █░░░█    ░█░░▒▒   \n" +
                        "    ▒▒▒██▒▒░░░▒▒██░█░  \n" +
                        "    █▓░░░░░░░░░░░░▒▓░  \n" +
                        "   ░█░█▓▓▓█░░░█▓▓▓▒▒▒  \n" +
                        "    ▒▓▓▓░▒▓▒░▓█░░▓▓▓░  \n" +
                        "     ░▒▓▓█▓▒░▓▓█▓█░    \n" +
                        "        ▒▓▓▒░▓▓█░      \n" +
                        "         ░▓▒░▓█        \n" +
                        "          █▓▓▓░        \n" +
                        "           ██░         \n"
                , AnsiColor.CYAN, col, row).print();
    }

    public static void displayMushroom(int col, int row) {
        new CLIString(
                "          ░░░          \n" +
                        "        █▓▒▒▒██░       \n" +
                        "       ▓█▓▒▒▒▒▒▓░      \n" +
                        "      ░▓█▓▒▒▒▒▒██      \n" +
                        "      ░▓█▓▒▒▒▒▒██      \n" +
                        "     ▒▓██▓▒▒▒▒▒▒█▒     \n" +
                        "   ░▓████▓▓█▒▒▒▒▒█▓▒   \n" +
                        " ░▓▓████████████████▓▒ \n" +
                        " ░▒▒▒▒▒▒▒█▓▓▓▓▒▒▒▒▒▒▒░ \n" +
                        "         ░▓▓▓▓         \n" +
                        "         ▒▓██▓         \n" +
                        "         ▒███▓         \n" +
                        "          ▒█▒          \n"
                , AnsiColor.RED, col, row).print();
    }

    public static void displayButterfly(int col, int row) {
        new CLIString(
                  " ▒█░                ▓█ \n" +
                        "▒██▓░             ▓████\n" +
                        "░▓█████▓░ ▒ ░░░███████▒\n" +
                        " ▒███▓▓▓██ █░█▓▓▓▓▓██▓ \n" +
                        "  ▓█▓▓█▓██▓█▓██▓██▓█▓░ \n" +
                        "   ▒▓▓████▓▓▓█████▓█   \n" +
                        "      ░▓██▓▓▓██▓▒░     \n" +
                        "       ░▓▓▓▓▓▓▓▒       \n" +
                        "    ░██████▓▓▓███▓▒    \n" +
                        "  █▓██████░▓▒▒▓██████  \n" +
                        " ▒██▓▓███  █░ ▒███▓██▓ \n" +
                        "  █▓▓██▓       ████▓▓░ \n" +
                        "     ░           ░░    \n"
                , AnsiColor.PURPLE, col, row).print();
    }

    public static void displayLeaf(int col, int row) {
        new CLIString(

                "            ░      \n" +
                        "         ░██▓          \n" +
                        "        ░█▒▒▒█         \n" +
                        " ░░     █▒██▒██     ░░ \n" +
                        " ▓████░░█▒█▓▒▒▓░▒▓███▒ \n" +
                        " ▓█▒▒▒█▓█▒█▓▒█▓█▒▒▒▒█▒ \n" +
                        " ██▒▒▒▒▒█▓█▓█▓█▒▒▒▒▒▓░ \n" +
                        "  ▓█▒▒▒▒▒█▓▓▓▒▒▒▒▒▒██  \n" +
                        "   ██▒▒▒▒▒█▓█▒▒▒▒▒█▒   \n" +
                        "    ░▓█▒▒▒█▓▒▒▒▒█▓     \n" +
                        "      ░▒███▓███▒       \n" +
                        "          ░█           \n" +
                        "          ▓░           \n"
                , AnsiColor.GREEN, col, row).print();
    }

    public static void display404(){
        new CLIString(
                        "┌───────────────────────────┐\n"+
                        "│                           │\n"+
                        "│ ██╗  ██╗ ██████╗ ██╗  ██╗ │\n"+
                        "│ ██║  ██║██╔═████╗██║  ██║ │\n"+
                        "│ ███████║██║██╔██║███████║ │\n"+
                        "│ ╚════██║████╔╝██║╚════██║ │\n"+
                        "│      ██║╚██████╔╝     ██║ │\n"+
                        "│      ╚═╝ ╚═════╝      ╚═╝ │\n"+
                        "│                           │\n"+
                        "└───────────────────────────┘\n"
                , AnsiColor.CYAN, 1, 20).centerPrint();
    }

    public static void displayLogin(){
        new CLIString(
                "┌─────────────────────────────────────────┐\n" +
                "│                                         │\n"+
                "│ ██╗      ██████╗  ██████╗ ██╗███╗   ██╗ │\n"+
                "│ ██║     ██╔═══██╗██╔════╝ ██║████╗  ██║ │\n"+
                "│ ██║     ██║   ██║██║  ███╗██║██╔██╗ ██║ │\n"+
                "│ ██║     ██║   ██║██║   ██║██║██║╚██╗██║ │\n"+
                "│ ███████╗╚██████╔╝╚██████╔╝██║██║ ╚████║ │\n"+
                "│ ╚══════╝ ╚═════╝  ╚═════╝ ╚═╝╚═╝  ╚═══╝ │\n"+
                "│                                         │\n"+
                "└─────────────────────────────────────────┘\n"
                , AnsiColor.GREEN, 1, 20).centerPrint();
    }

}