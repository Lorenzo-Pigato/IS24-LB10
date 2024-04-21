package it.polimi.ingsw.lb10.server.model.quest.Pattern;

import it.polimi.ingsw.lb10.server.model.cards.Color;
import it.polimi.ingsw.lb10.server.model.quest.Quest;

public abstract class LJPattern extends Quest {
    private final Color bodyColor;
    private final Color toeColor;


    public LJPattern(int id, int points, Color bodyColor, Color toeColor) {
        super(id, points);

        this.bodyColor=bodyColor;
        this.toeColor=toeColor;
    }



    // --------> GETTER <--------
    public Color getBodyColor() {
        return bodyColor;
    }
    public Color getToeColor() {
        return toeColor;
    }

}
