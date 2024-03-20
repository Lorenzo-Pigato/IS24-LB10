package it.polimi.ingsw.lb10.client.view.clidesign;

import it.polimi.ingsw.lb10.client.view.clidesign.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.view.clidesign.ansi.AnsiFormat;
import it.polimi.ingsw.lb10.client.view.clidesign.ansi.AnsiSpecial;
import it.polimi.ingsw.lb10.client.view.clidesign.ansi.AnsiString;

import java.util.Scanner;

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
    static void draw(int col, int row, int width, int height, String text, AnsiColor borderColor, AnsiColor textColor, AnsiFormat borderFormat, AnsiFormat textFormat) {

        AnsiString verticalLine = new AnsiString(AnsiSpecial.VERTICAL.getCode(), borderColor, borderFormat);
        AnsiString horizontalLine = new AnsiString(AnsiSpecial.HORIZONTAL.getCode(), borderColor, borderFormat);
        AnsiString downLeftCorner = new AnsiString(AnsiSpecial.DLCORNER.getCode(), borderColor, borderFormat);
        AnsiString downRightCorner = new AnsiString(AnsiSpecial.DRCORNER.getCode(), borderColor, borderFormat);
        AnsiString upLeftCorner = new AnsiString(AnsiSpecial.ULCORNER.getCode(), borderColor, borderFormat);
        AnsiString upRightCorner = new AnsiString(AnsiSpecial.URCORNER.getCode(), borderColor, borderFormat);

        if (width < 2) width = 2;
        if (height < 2) height = 2;

        CLIPage.setPosition(col, row);

        upLeftCorner.print();
        for (int i = 1; i < width - 1; i++) horizontalLine.print();
        upRightCorner.print();

        for (int i = row + 1; i < row + height - 1; i++) {
            CLIPage.setPosition(col, i);
            verticalLine.print();
            CLIPage.setPosition(col + width - 1, i);
            verticalLine.print();
        }

        CLIPage.setPosition(col, row + height - 1);
        downLeftCorner.print();
        for (int i = 1; i < width - 1; i++) horizontalLine.print();
        downRightCorner.print();

        if (height > 2) {
            if (text.length() > width - 2) text = text.substring(0, width - 5) + "...";

            CLIPage.setPosition(col + 1, row + (height) / 2);
            new AnsiString(text, textColor, textFormat).print();
        }
    }

    static void draw(int col, int row, String text, AnsiColor borderColor, AnsiColor textColor, AnsiFormat borderFormat, AnsiFormat textFormat) {
        CLIBox.draw(col, row, text.length() + 2, 3, text, borderColor, textColor, borderFormat, textFormat);
    }

    static void draw(int col, int row, int width, int height, String text) {
        CLIBox.draw(col, row, width, height, "", AnsiColor.DEFAULT, AnsiColor.DEFAULT, AnsiFormat.DEFAULT, AnsiFormat.DEFAULT);

    }
    static void draw(int col, int row, int width, int height, AnsiColor borderColor, AnsiFormat borderFormat) {
        CLIBox.draw(col, row, width, height, "", borderColor, AnsiColor.DEFAULT, borderFormat, AnsiFormat.DEFAULT);

    }

    static void draw(int col, int row, int width, int height, AnsiColor borderColor) {
        CLIBox.draw(col, row, width, height, "", borderColor, AnsiColor.DEFAULT, AnsiFormat.DEFAULT, AnsiFormat.DEFAULT);
    }

    static void draw(int col, int row, String text, AnsiColor borderColor) {
        CLIBox.draw(col, row, text, borderColor, AnsiColor.DEFAULT, AnsiFormat.DEFAULT, AnsiFormat.DEFAULT);

    }

    static void draw(int col, int row, String text, AnsiColor borderColor, AnsiColor textColor){
        CLIBox.draw(col, row, text, borderColor, textColor, AnsiFormat.DEFAULT, AnsiFormat.DEFAULT);

    }


}
