package it.polimi.ingsw.lb10.client.cli;

import it.polimi.ingsw.lb10.client.cli.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiString;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiFormat;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * This class provides a way to print strings with ANSI colors and formats
 * and to manage their position on the console.
 * It also provides methods to refactor them or delete them.
 * Strings are declared as objects, use AnsiString to print without creating an object
 */
public class CLIString {
    private final String string;
    private AnsiColor color;
    private AnsiFormat format;
    private final int[] position = new int[2];
    private boolean centered = false;
    private boolean visible = false;

    //-------------------------- Builders -----------------------------//
    public CLIString(String string, AnsiColor color, AnsiFormat format, int col, int row, int widthLimit){
        this.string = string.length() > widthLimit ? string.substring(0, widthLimit-3)+ "..." : string;
        this.color = color;
        this.format = format;

        this.position[0] = col;
        this.position[1] = row;

        this.centered = false;
        this.visible = false;
    }

    public CLIString(String string, AnsiColor color, int col, int row, int widthLimit){
        this(string, color, AnsiFormat.DEFAULT, col, row, widthLimit);
    }

    public CLIString(String string, AnsiFormat format, int col, int row, int widthLimit){
        this(string, AnsiColor.DEFAULT, format, col, row, widthLimit);
    }

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

    public CLIString(String string, int col, int row){
        this(string, AnsiColor.DEFAULT, AnsiFormat.DEFAULT, col, row);
    }

    //------------------------ Refactoring ---------------------------//

    /**
     * This method changes string's color by calling AnsiString's print() method
     * so that the new colored string is printed on top of the old string, effectively changing
     * its color without having to delete it
     * @param color new color to apply to the string
     */
    public void changeColor(AnsiColor color){
        this.color = color;
        if(this.visible) this.print() ;
    }

    /**
     * This method changes string's format by calling AnsiString's print() method
     * so that the new colored string is printed on top of the old string, effectively changing
     * its format without having to delete it
     * @param format new format to apply to the string
     */
    public void changeFormat(AnsiFormat format){
        this.format = format;
        if(this.visible) this.print();
    }

    /**
     * This method replaces a string with another one.
     * Cursor position has to be reset manually, if needed
     * @param oldString the string to be replaced
     * @param newString the string to replace the old one with
     */
    public static void replace(@NotNull CLIString oldString,@NotNull CLIString newString){
        oldString.deleteString();

        newString.reposition(oldString.getPosition()[0], oldString.getPosition()[1]);

        if (oldString.centered) newString.centerPrint();
        else newString.print();

    }

    public void reposition(int col, int row){
        if(this.visible){
            this.deleteString();
            this.position[0] = col;
            this.position[1] = row;
            this.print();
        }
        else {
            this.position[0] = col;
            this.position[1] = row;
        }
    }

    /**
     * This method is used to replace a string with " " chars in order to
     * effectively delete it on the CLI.
     * It sets the isVisible flag to false after
     */
    public void deleteString(){
        CLICommand.setPosition(position[0], position[1]);
        int row = position[1];
        Arrays.stream(string.split("\n")).forEach(
                line -> {
                    System.out.print(" ".repeat(line.length()));
                    CLICommand.setPosition(position[0], ++position[1]);
                }
        );
        position[1] = row;
        this.visible = false;

        CLICommand.restoreCursorPosition(); //Restoring cursor position to saved position before deletion
    }
    //------------------ String printing Methods ----------------------//
    public void print(){
        CLICommand.setPosition(position[0], position[1]);
        int row = position[1];
        Arrays.stream(string.split("\n")).forEach(
                line -> {
                    CLICommand.setPosition(position[0], position[1]);
                    AnsiString.print(line, this.color, this.format);
                    position[1]++;
                }
        );
        position[1] = row;
        this.visible = true;
    }

    public void centerPrint(){
        this.reposition((CLICommand.getDefaultWidth() - Arrays.stream(string.split("\n"))
                                                                .mapToInt(String::length)
                                                                .max().orElse(0)) / 2
                , position[1]);

        this.print();
        this.centered = true;
        this.visible = true;
    }

    //------------------------   Getters   ----------------------------//
    public int[] getPosition() {
        return  new int[] {position[0], position[1]};
    }

    public boolean isCentered() {
        return centered;
    }

    public boolean isVisible() {
        return visible;
    }
}
