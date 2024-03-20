package it.polimi.ingsw.lb10.client.view.clidesign.ansi;

public enum AnsiSpecial {
    RESET("\u001B[0m"),
    BLOCK("█"),
    HALFBLOCK("▄"),
    HORIZONTAL("─"),
    VERTICAL("│"),
    ULCORNER("┌"),
    URCORNER("┐"),
    DLCORNER("└"),
    DRCORNER("┘");

    private final String code;

    AnsiSpecial(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
