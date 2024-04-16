package it.polimi.ingsw.lb10.server.model;

public class Player {
    private int hashCode;
    private String username;

    public Player(int hashCode, String username) {
        this.hashCode = hashCode;
        this.username = username;
    }

    public int getHashCode() {
        return hashCode;
    }

    public String getUsername() { return username;}
}
