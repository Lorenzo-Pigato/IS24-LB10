package it.polimi.ingsw.lb10.client.cli.ansi;

/**
 * This class provides a way to print strings with ANSI colors and formats
 */
public abstract class AnsiString {

    public static void print(String string, AnsiColor color, AnsiFormat format){
        System.out.print(AnsiSpecial.RESET.getCode() +
                        color.getCode()+
                        format.getCode()+
                        string +
                        AnsiSpecial.RESET.getCode()
        );
    }

    public static void print(String string, AnsiColor color){
        print(string, color, AnsiFormat.DEFAULT);
    }

    public static void print(String string, AnsiFormat format){
        print(string, AnsiColor.DEFAULT, format);
    }
}


