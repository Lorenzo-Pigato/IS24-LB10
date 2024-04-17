package it.polimi.ingsw.lb10.server.model.DrawType;

import it.polimi.ingsw.lb10.server.model.MatchModel;
import it.polimi.ingsw.lb10.server.model.Player;
import it.polimi.ingsw.lb10.server.model.cards.Card;


public class GoldenUncovered implements DrawStrategy {
    private final MatchModel model;
    private final Player player;
    private final boolean first;
    public GoldenUncovered(Player player,MatchModel model,boolean first){
        this.player=player;
        this.model=model;
        this.first=first;
    }
    @Override
    public void drawCard() {
        getPlayer().addCardOnHand(getGoldenUncovered(isFirst()));
    }

    public Card getGoldenUncovered(boolean first){
        Card temp;
        int index = 0;

        if (!first)
            index = 1;

        temp=getModel().getGoldenUncovered().get(index);

        getModel().getGoldenUncovered().remove(index);
        getModel().addGoldenUncovered(index);
        return temp;
    }

    // --------> GETTER <--------

    public MatchModel getModel() {
        return model;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isFirst() {
        return first;
    }
}
