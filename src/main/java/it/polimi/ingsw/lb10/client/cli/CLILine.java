package it.polimi.ingsw.lb10.client.cli;

import it.polimi.ingsw.lb10.client.cli.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiSpecial;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiString;

/**
 * This class contains methods to draw horizontal and vertical lines on the terminal.
 */
public abstract class CLILine {

    public static void drawHorizontal(int col, int row, int finalCol, AnsiColor color) {
        CLICommand.setPosition(col, row);

        if (finalCol < col) CLICommand.setPosition(col, finalCol);

        for (int i = 0; i < finalCol - col; i++) AnsiString.print(AnsiSpecial.HORIZONTAL.getCode(), color);
    }

    public static void drawHorizontal(int col, int row, int finalCol) {
        drawHorizontal(col, row, finalCol, AnsiColor.DEFAULT);
    }

    public static void drawVertical(int col, int row, int finalRow, AnsiColor color) {
        CLICommand.setPosition(col, row);

        if (finalRow < row) {
            CLICommand.setPosition(finalRow, row);

            for (int i = finalRow; i < row; i++) {
                AnsiString.print(AnsiSpecial.VERTICAL.getCode(), color);
                CLICommand.setPosition(col, i);
            }
        } else {
            for (int i = row; i < finalRow; i++) {
                AnsiString.print(AnsiSpecial.VERTICAL.getCode(), color);
                CLICommand.setPosition(col, i);
            }
        }
    }

    public static void drawVertical(int col, int row, int finalRow) {
        drawVertical(col, row, finalRow, AnsiColor.DEFAULT);
    }

}
