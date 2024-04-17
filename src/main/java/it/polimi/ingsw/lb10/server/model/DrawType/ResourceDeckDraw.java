package it.polimi.ingsw.lb10.server.model.DrawType;

import it.polimi.ingsw.lb10.server.model.MatchModel;
import it.polimi.ingsw.lb10.server.model.Player;


public class ResourceDeckDraw implements DrawStrategy {
    private final MatchModel model;
    private final Player player;
    public ResourceDeckDraw(Player player, MatchModel model){
        this.player=player;
        this.model=model;
    }
    @Override
    public void drawCard() {
        getPlayer().addCardOnHand(getModel().getResourceCardFromDeck());
    }

    // --------> GETTER <--------

    public MatchModel getModel() {
        return model;
    }

    public Player getPlayer() {
        return player;
    }
}
