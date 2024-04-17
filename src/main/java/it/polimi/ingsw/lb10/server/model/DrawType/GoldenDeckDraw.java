package it.polimi.ingsw.lb10.server.model.DrawType;

import it.polimi.ingsw.lb10.server.model.MatchModel;
import it.polimi.ingsw.lb10.server.model.Player;


public class GoldenDeckDraw implements DrawStrategy {
    private final MatchModel model;
    private final Player player;
    public GoldenDeckDraw(Player player, MatchModel model){
        this.player=player;
        this.model=model;
    }
    @Override
    public void drawCard() {
        getPlayer().addCardOnHand(getModel().getGoldenCardFromDeck());
    }

    // --------> GETTER <--------

    public MatchModel getModel() {
        return model;
    }

    public Player getPlayer() {
        return player;
    }
}
