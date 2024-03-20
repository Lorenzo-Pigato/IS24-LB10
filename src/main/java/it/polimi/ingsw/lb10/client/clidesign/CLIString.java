package it.polimi.ingsw.lb10.client.clidesign;

import it.polimi.ingsw.lb10.client.clidesign.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.clidesign.ansi.AnsiString;
import it.polimi.ingsw.lb10.client.clidesign.ansi.AnsiFormat;

public class CLIString {
    private AnsiString string;
    private final int[] position = new int[2];

    public CLIString(String string, AnsiColor color, int col, int row){
        this.string = new AnsiString(string, color);
        this.position[0] = col;
        this.position[1] = row;
    }

    public void changeColor(AnsiColor color){
        string = new AnsiString(string.getString(), color);

        CLIPage.setPosition(this.position[0], this.position[1]);
        this.string.print();
    }

    public void changeFormat(AnsiFormat format){
        string = new AnsiString(string.getString(), format);

        CLIPage.setPosition(this.position[0], this.position[1]);
        this.string.print();
    }

    public void deleteString(){
        CLIPage.setPosition(this.position[0], this.position[1]);
        int len = this.string.getString().length();

        for(int i = 0; i < len; i++){
            if(this.string.getString().charAt(i) != '\n')
                System.out.print(" ");
            else
                System.out.println();
        }

    }

    public void print(){
        CLIPage.setPosition(position[0], position[1]);
        this.string.print();
    }

}
