package it.polimi.ingsw.lb10.client.view.clidesign.ansi;

/**
 * This class provides a way to print strings with ANSI colors and formats
 */
public class AnsiString {
    private final String string;
    private AnsiColor color;
    private AnsiFormat format;

    private AnsiString(String string){
        this.string = string;
        this.color = AnsiColor.DEFAULT;
        this.format = AnsiFormat.DEFAULT;
    }

    public AnsiString(String string, AnsiColor color){
        this(string);
        this.color=color;
    }

    public AnsiString(String string, AnsiFormat format){
        this(string);
        this.format = format;
    }
    public AnsiString(String string, AnsiColor color, AnsiFormat format){
        this(string);
        this.color = color;
        this.format = format;
    }

    public void print(){
        System.out.print(AnsiSpecial.RESET.getCode() +
                        this.color.getCode()+
                        this.format.getCode()+
                        this.string +
                        AnsiSpecial.RESET.getCode()
        );
    }
}


