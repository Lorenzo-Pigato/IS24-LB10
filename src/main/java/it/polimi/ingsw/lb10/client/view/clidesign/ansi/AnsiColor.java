package it.polimi.ingsw.lb10.client.view.clidesign.ansi;

public enum AnsiColor {
    DEFAULT(""),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m"),
    YELLOW("\u001B[33m"),
    YELLOWBG("\033[35;47m");

    private final String code;

    AnsiColor(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}