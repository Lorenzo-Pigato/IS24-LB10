package it.polimi.ingsw.lb10.server.model;

/**
 * Use the value NULL when the corner doesn't exist
 * Use EMPTY when the corner exists but without resources!
 */
public enum Resource {
    NULL(),
    EMPTY(),
    MUSHROOM(),
    ANIMAL(),
    INSECT(),
    PLANT(),
    FEATHER(),
    POTION(),
    PERGAMENA(),
    PATTERN();
}
