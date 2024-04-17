package it.polimi.ingsw.lb10.server.model.quest;

import it.polimi.ingsw.lb10.server.model.quest.pattern.PatternStrategy;

public class QuestPattern extends Quest {

    private boolean isLPattern;
    PatternStrategy patternStrategy;

    public QuestPattern(int id, int points){
            setId(id);
            setPoints(points);
    }

    public boolean isLPattern() {
        return isLPattern;
    }

    public PatternStrategy getPatternStrategy() {
        return patternStrategy;
    }
    public void getAlgorithm(){
        patternStrategy.chooseAlgorithm();
    }

    public void setPatternStrategy(PatternStrategy patternStrategy) {
        this.patternStrategy = patternStrategy;
    }
}
