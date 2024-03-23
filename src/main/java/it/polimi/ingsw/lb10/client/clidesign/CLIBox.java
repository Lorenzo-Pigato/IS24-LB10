package it.polimi.ingsw.lb10.client.clidesign;

import it.polimi.ingsw.lb10.client.clidesign.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.clidesign.ansi.AnsiFormat;
import it.polimi.ingsw.lb10.client.clidesign.ansi.AnsiSpecial;
import it.polimi.ingsw.lb10.client.clidesign.ansi.AnsiString;

public abstract class CLIBox {

    /**
     * Draw a box on terminal.
     * If text is longer than width-2, it will be cut and "..." will be added
     * @param col box's top-left vertex's "x" position
     * @param row box's top-left vertex's "y" position
     * @param width box's width - >1
     * @param height box's height - >1
     * @param text box's text
     * @param borderColor box's border color ANSI
     * @param textColor box's text color
     * @param borderFormat box's border format ANSI
     * @param textFormat box's text format
     */
    public static void draw(int col, int row, int width, int height, String text, AnsiColor borderColor, AnsiColor textColor, AnsiFormat borderFormat, AnsiFormat textFormat) {

        if (width < 2) width = 2;
        if (height < 2) height = 2;

        CLICommand.setPosition(col, row);
        AnsiString.print(AnsiSpecial.ULCORNER.getCode(), borderColor, borderFormat);

        for (int i = 1; i < width - 1; i++) AnsiString.print(AnsiSpecial.HORIZONTAL.getCode(), borderColor, borderFormat);
        AnsiString.print(AnsiSpecial.URCORNER.getCode(), borderColor, borderFormat);

        for (int i = row + 1; i < row + height - 1; i++) {
            CLICommand.setPosition(col, i);
            AnsiString.print(AnsiSpecial.VERTICAL.getCode(), borderColor, borderFormat);
            CLICommand.setPosition(col + width - 1, i);
            AnsiString.print(AnsiSpecial.VERTICAL.getCode(), borderColor, borderFormat);
        }

        CLICommand.setPosition(col, row + height - 1);
        AnsiString.print(AnsiSpecial.DLCORNER.getCode(), borderColor, borderFormat);
        for (int i = 1; i < width - 1; i++) AnsiString.print(AnsiSpecial.HORIZONTAL.getCode(), borderColor, borderFormat);
        AnsiString.print(AnsiSpecial.DRCORNER.getCode(), borderColor, borderFormat);

        if (height > 2) {
            if (text.length() > width - 2) text = text.substring(0, width - 5) + "...";

            CLICommand.setPosition(col + 1, row + (height) / 2);
            AnsiString.print(text, textColor, textFormat);
        }
    }

    public static void draw(int col, int row, String text, AnsiColor borderColor, AnsiColor textColor, AnsiFormat borderFormat, AnsiFormat textFormat) {
        CLIBox.draw(col, row, text.length() + 2, 3, text, borderColor, textColor, borderFormat, textFormat);
    }

    static void draw(int col, int row, int width, int height, String text) {
        CLIBox.draw(col, row, width, height, "", AnsiColor.DEFAULT, AnsiColor.DEFAULT, AnsiFormat.DEFAULT, AnsiFormat.DEFAULT);

    }
    public static void draw(int col, int row, int width, int height, AnsiColor borderColor, AnsiFormat borderFormat) {
        CLIBox.draw(col, row, width, height, "", borderColor, AnsiColor.DEFAULT, borderFormat, AnsiFormat.DEFAULT);

    }

    public static void draw(int col, int row, int width, int height, AnsiColor borderColor) {
        CLIBox.draw(col, row, width, height, "", borderColor, AnsiColor.DEFAULT, AnsiFormat.DEFAULT, AnsiFormat.DEFAULT);
    }

    public static void draw(int col, int row, String text, AnsiColor borderColor) {
        CLIBox.draw(col, row, text, borderColor, AnsiColor.DEFAULT, AnsiFormat.DEFAULT, AnsiFormat.DEFAULT);

    }

    public static void draw(int col, int row, String text, AnsiColor borderColor, AnsiColor textColor){
        CLIBox.draw(col, row, text, borderColor, textColor, AnsiFormat.DEFAULT, AnsiFormat.DEFAULT);

    }

}
