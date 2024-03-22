package it.polimi.ingsw.lb10.client.clidesign;

import it.polimi.ingsw.lb10.client.clidesign.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.clidesign.ansi.AnsiString;
import it.polimi.ingsw.lb10.client.clidesign.ansi.AnsiFormat;

import java.util.Arrays;

/**
 * This class provides a way to print strings with ANSI colors and formats
 * and to manage their position on the console.
 * It also provides methods to refactor them or delete them.
 * Strings are declared as objects, use AnsiString to print without creating an object
 */
public class CLIString {
    private String string;
    private AnsiColor color;
    private AnsiFormat format;
    private final int[] position = new int[2];

    public CLIString(String string, AnsiColor color, AnsiFormat format, int col, int row){
        this.string = string;
        this.color = color;
        this.format = format;

        this.position[0] = col;
        this.position[1] = row;
    }

    public CLIString(String string, AnsiColor color, int col, int row){
        this(string, color, AnsiFormat.DEFAULT, col, row);
    }

    public CLIString(String string, AnsiFormat format, int col, int row){
        this(string, AnsiColor.DEFAULT, format, col, row);
    }

    /**
     * This method changes the color of the string by creating a new AnsiString object
     * and printing it to the console on top of the old string, effectively changing its color
     * without having to delete it
     * @param color the color to change to
     */
    public void changeColor(AnsiColor color){
        this.color = color;
        this.print();
    }

    public void changeFormat(AnsiFormat format){
        this.format = format;
        this.print();
    }

    public void deleteString(){
        CLIPage.setPosition(this.position[0], this.position[1]);
        int row = position[1];
        Arrays.stream(string.split("\n")).forEach(
                line -> {
                    System.out.print(" ".repeat(line.length()));
                    CLIPage.setPosition(this.position[0], this.position[1]);
                    this.position[1]++;
                }
        );
        position[1] = row;
    }

    public void reposition(int col, int row){
        this.deleteString();
        this.position[0] = col;
        this.position[1] = row;
        this.print();
    }

    public void print(){
        CLIPage.setPosition(position[0], position[1]);
        int row = position[1];
        Arrays.stream(string.split("\n")).forEach(
                line -> {
                    CLIPage.setPosition(position[0], position[1]);
                    AnsiString.print(line, this.color, this.format);
                    position[1]++;
                }
        );
        position[1] = row;
    }

    public void centerPrint(){
        this.reposition((CLIPage.getDefaultWidth() - string.split("\n")[0].length()) / 2, position[1]);
    }

}
