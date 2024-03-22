package it.polimi.ingsw.lb10.client.clidesign;

import it.polimi.ingsw.lb10.client.clidesign.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.clidesign.ansi.AnsiSpecial;
import it.polimi.ingsw.lb10.client.clidesign.ansi.AnsiString;

public abstract class CLILine {

    public static void drawHorizontal(int col, int row, int finalRow, AnsiColor color){
        CLIPage.setPosition(col, row);

        if(finalRow < row) CLIPage.setPosition(col, finalRow);

        for(int i = 0; i < finalRow-row; i++) AnsiString.print(AnsiSpecial.HORIZONTAL.getCode(), color);
    }

    public static void drawHorizontal(int col, int row, int finalRow){
        drawHorizontal(col, row, finalRow, AnsiColor.DEFAULT);
    }

    public static void drawVertical(int col, int row, int finalCol, AnsiColor color){
        CLIPage.setPosition(col, row);

        if(finalCol < col){
            CLIPage.setPosition(finalCol, row);

            for(int i = finalCol; i < col; i++) {
                AnsiString.print(AnsiSpecial.VERTICAL.getCode(), color);
                CLIPage.setPosition(i, row);
            }
        }

        else{
            for(int i = col; i < finalCol; i++) {
                AnsiString.print(AnsiSpecial.VERTICAL.getCode(), color);
                CLIPage.setPosition(i, row);
            }
        }
    }

    public static void drawVertical(int col, int row, int finalCol){
        drawHorizontal(col, row, finalCol, AnsiColor.DEFAULT);
    }

}
