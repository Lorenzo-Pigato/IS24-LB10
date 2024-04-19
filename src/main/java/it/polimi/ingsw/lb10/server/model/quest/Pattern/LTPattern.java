package it.polimi.ingsw.lb10.server.model.quest.Pattern;

import it.polimi.ingsw.lb10.server.model.Matrix;
import it.polimi.ingsw.lb10.server.model.cards.Color;
import it.polimi.ingsw.lb10.server.model.quest.QuestPattern;

public class LTPattern extends QuestPattern {
    private Color bodyColor;
    private Color toeColor;

    public LTPattern(int id, int points) {
        super(id, points);
    }

    public boolean chooseAlgorithm(Matrix matrix, int row, int column) {
        return false;
    }

    // --------> GETTER <--------
    public Color getBodyColor() {
        return bodyColor;
    }
    public Color getToeColor() {
        return toeColor;
    }

    // --------> SETTER <--------
    public void setBodyColor(Color bodyColor) {
        this.bodyColor = bodyColor;
    }

    public void setToeColor(Color toeColor) {
        this.toeColor = toeColor;
    }
}
