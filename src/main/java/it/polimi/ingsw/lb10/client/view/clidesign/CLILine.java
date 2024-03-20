package it.polimi.ingsw.lb10.client.view.clidesign;

import it.polimi.ingsw.lb10.client.view.clidesign.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.view.clidesign.ansi.AnsiSpecial;
import it.polimi.ingsw.lb10.client.view.clidesign.ansi.AnsiString;

public abstract class CLILine {

    public static void drawHorizontal(int col, int row, int finalRow, AnsiColor color){
        AnsiString horizontalLine = new AnsiString(AnsiSpecial.HORIZONTAL.getCode(), color);
        CLIPage.setPosition(col, row);

        if(finalRow < row) CLIPage.setPosition(col, finalRow);

        for(int i = 0; i < finalRow-row; i++) horizontalLine.print();
    }

    public static void drawHorizontal(int col, int row, int finalRow){
        drawHorizontal(col, row, finalRow, AnsiColor.DEFAULT);
    }

    public static void drawVertical(int col, int row, int finalCol, AnsiColor color){
        AnsiString verticalLine = new AnsiString(AnsiSpecial.VERTICAL.getCode(), color);
        CLIPage.setPosition(col, row);

        if(finalCol < col){
            CLIPage.setPosition(finalCol, row);

            for(int i = finalCol; i < col; i++) {
                verticalLine.print();
                CLIPage.setPosition(i, row);
            }
        }

        else{
            for(int i = col; i < finalCol; i++) {
                verticalLine.print();
                CLIPage.setPosition(i, row);
            }
        }
    }

    public static void drawVertical(int col, int row, int finalCol){
        drawHorizontal(col, row, finalCol, AnsiColor.DEFAULT);
    }

}
