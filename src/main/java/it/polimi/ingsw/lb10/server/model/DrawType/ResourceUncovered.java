package it.polimi.ingsw.lb10.server.model.DrawType;

import it.polimi.ingsw.lb10.server.model.MatchModel;
import it.polimi.ingsw.lb10.server.model.Player;
import it.polimi.ingsw.lb10.server.model.cards.PlaceableCard;

public class ResourceUncovered implements DrawStrategy {
    private final MatchModel model;
    private final Player player;
    private final boolean first;
    public ResourceUncovered(Player player,MatchModel model, boolean first){
        this.player=player;
        this.model=model;
        this.first=first;
    }
    @Override
    public void drawCard() {
        getPlayer().addCardOnHand(getResourceUncovered(isFirst()));
    }

    /**
     * @param first means if th player wants to draw the first card, if it's false he'll draw th second card
     * @return the first (second) uncovered card
     */
    public PlaceableCard getResourceUncovered(boolean first){
        PlaceableCard temp;
        int index = 0;

        if (!first)
            index = 1;

        temp=getModel().getResourceUncovered().get(index);

        getModel().getResourceUncovered().remove(index);
        getModel().addResourceUncovered(index);
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
