package it.polimi.ingsw.lb10.server.model.quest;

public class QuestPattern extends Quest {

    private boolean isLPattern;
    private boolean isTopLeft;

    public QuestPattern(int id, int points){
            setId(id);
            setPoints(points);
    }

    // --------> SETTER <--------

    public void setLPattern(boolean LPattern) {
        isLPattern = LPattern;
    }
    public void setTopLeft(boolean topLeft) {
        isTopLeft = topLeft;
    }

    // --------> GETTER <--------


    public boolean isLPattern() {
        return isLPattern;
    }
    public boolean isTopLeft() {
        return isTopLeft;
    }

}
/*    TRIED TO IMPLEMENT THE CODE WITH A STRATEGY BUT I M CHANGING WITH EXTENSION
 * This method calls the right algorithm!
 * The choice is decided by the method setPatternStrategy, based on the boolean itTopLeft ( the first card of the combo)

public boolean getAlgorithm(Matrix matrix, int row, int column){
    setPatternStrategy();
    return patternStrategy.chooseAlgorithm( matrix, row, column);
}

public void setPatternStrategy() {
    if (isLPattern)
        patternStrategy = new LPattern();
    else
        patternStrategy = new DiagonalPattern(isTopLeft);
}
*
*
*
        */
