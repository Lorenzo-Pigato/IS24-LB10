package it.polimi.ingsw.lb10.client.cli;

import it.polimi.ingsw.lb10.client.cli.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiFormat;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiSpecial;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiString;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class CLIBox {

    /**
     * Draw a box on terminal.
     * If text is longer than width-2, it will be cut and "..." will be added
     *
     * @param col         box's top-left vertex's "x" position
     * @param row         box's top-left vertex's "y" position
     * @param width       box's width - >1
     * @param height      box's height - >1
     * @param text        box's text
     * @param borderColor box's border color ANSI
     * @param textColor   box's text color
     * @param textFormat  box's text format
     */

    public static void draw(int col, int row, int width, int height, String text, AnsiColor borderColor, AnsiColor textColor, AnsiFormat textFormat) {

        if (width < 2) width = 2;
        if (height < 2) height = 2;

        CLICommand.setPosition(col, row);
        AnsiString.print(AnsiSpecial.ULCORNER.getCode(), borderColor);

        for (int i = 1; i < width - 1; i++) AnsiString.print(AnsiSpecial.HORIZONTAL.getCode(), borderColor);
        AnsiString.print(AnsiSpecial.URCORNER.getCode(), borderColor);

        for (int i = row + 1; i < row + height - 1; i++) {
            CLICommand.setPosition(col, i);
            AnsiString.print(AnsiSpecial.VERTICAL.getCode(), borderColor);
            CLICommand.setPosition(col + width - 1, i);
            AnsiString.print(AnsiSpecial.VERTICAL.getCode(), borderColor);
        }

        CLICommand.setPosition(col, row + height - 1);
        AnsiString.print(AnsiSpecial.DLCORNER.getCode(), borderColor);
        for (int i = 1; i < width - 1; i++) AnsiString.print(AnsiSpecial.HORIZONTAL.getCode(), borderColor);
        AnsiString.print(AnsiSpecial.DRCORNER.getCode(), borderColor);

        if (height >= calcStringHeight(text) + 2) {
            int cursorCol = calcMaxLength(text) > width + 2 ? col + 1 : col + (width - calcMaxLength(text)) / 2;
            int cursorRow = row + (height - calcStringHeight(text)) / 2;

            AtomicInteger index = new AtomicInteger(0);
            Arrays.stream(text.split("\n")).forEach(
                    line -> {
                        CLICommand.setPosition(cursorCol, cursorRow + index.getAndIncrement());
                        AnsiString.print(line, textColor, textFormat);
                    }
            );
        }
    }

    //Overloaded draw Methods
    public static void draw(int col, int row, String text, AnsiColor borderColor, AnsiColor textColor, AnsiFormat textFormat) {
        CLIBox.draw(col, row, calcMaxLength(text) + 2, calcStringHeight(text) + 2, text, borderColor, textColor, textFormat);
    }

    public static void draw(int col, int row, int width, int height, AnsiColor borderColor) {
        CLIBox.draw(col, row, width, height, "", borderColor, AnsiColor.DEFAULT, AnsiFormat.DEFAULT);
    }

    public static void draw(int col, int row, String text, AnsiColor borderColor) {
        CLIBox.draw(col, row, text, borderColor, AnsiColor.DEFAULT, AnsiFormat.DEFAULT);
    }
    // - END of overloaded methods -

    private static int calcStringHeight(String text) {
        return (int) Arrays.stream(text.split("\n")).count();
    }

    private static int calcMaxLength(String text) {
        return Arrays.stream(text.split("\n")).mapToInt(String::length).max().orElse(0);
    }

}
