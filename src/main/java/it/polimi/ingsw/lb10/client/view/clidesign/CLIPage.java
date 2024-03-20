package it.polimi.ingsw.lb10.client.view.clidesign;

import it.polimi.ingsw.lb10.client.view.clidesign.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.view.clidesign.ansi.AnsiFormat;
import it.polimi.ingsw.lb10.client.view.clidesign.ansi.AnsiSpecial;
import it.polimi.ingsw.lb10.client.view.clidesign.ansi.AnsiString;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * This interface provides all methods to create simple graphics on terminal
 */
public class CLIPage {
    /**
     * Set cursor position to (1,1) - Home
     */
    static void home() {
        System.out.print("\033[H");
        System.out.flush();
    }

    /**
     * Clear screen using ANSI code
     */
    static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void clearLineAfterCursor(){
        System.out.print("\033[0K");
        System.out.flush();
    }

    static void clearLineUntilCursor(){
        System.out.print("\033[1K");
        System.out.flush();
    }

    static void clearLine(){
        System.out.print("\033[2K");
        System.out.flush();
    }

    /**
     * This method sets current terminal windows size
     * @param cols number of terminal columns
     * @param rows number of terminal rows
     */
    static void setScreenSize(int cols, int rows) {
        System.out.print("\033[8;"+rows+";"+cols+"t");
    }

    /**
     * Set terminal window's default state:
     * window size of 80x40 - default system size
     * clear window, cursor in (1,1)
     */
    static void initialize() {
        setScreenSize(80,24);
        home();
        clearScreen();
    }

    /**
     * Set cursor position inside window
     * @param col column position - starting at 1
     * @param row row position - starting at 1
     */
    static void setPosition(int col, int row) {
        if(row < 1) row = 1;
        if(col < 1) col = 1;
        System.out.print("\033[" + row + ";" + col + "H");
    }

    static void goToInputLine(){
        setPosition(0,23);
    }

    static void setInvisibleInput(){
        System.out.print("\u001B[8m");
        System.out.flush();
        System.out.print("\u001b[?25l");
        System.out.flush();
    }

    static void resetInvisibleInput(){
        System.out.print("\u001B[28m");
        System.out.flush();
        System.out.print("\u001b[?25h");
        System.out.flush();
    }
}
