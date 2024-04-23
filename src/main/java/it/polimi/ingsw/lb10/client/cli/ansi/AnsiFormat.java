package it.polimi.ingsw.lb10.client.cli.ansi;

public enum AnsiFormat {
    DEFAULT(""),
    UNDERLINE("\u001B[4m"),
    BOLD("\u001B[1m"),
    CROSSED("\u001B[9m"),
    ITALIC("\u001B[3m"),
    LIGHT("\u001B[2m");

    private final String code;

    AnsiFormat(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
