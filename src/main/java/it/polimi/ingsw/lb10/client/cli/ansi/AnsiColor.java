package it.polimi.ingsw.lb10.client.cli.ansi;

public enum AnsiColor {
    DEFAULT(""),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m"),
    YELLOW("\u001B[33m"),
    GREY("\u001B[90m");

    private final String code;

    AnsiColor(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}