package it.polimi.ingsw.lb10.network.requests.match;

import it.polimi.ingsw.lb10.server.visitors.requestDispatch.MatchRequestVisitor;

public class MoveBoardRequest extends MatchRequest {

    private int xOffset;
    private int yOffset;


    public MoveBoardRequest(int matchId, int xOffset, int yOffset) {
        super(matchId);
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }


    @Override
    public void accept(MatchRequestVisitor visitor) {
        visitor.visit(this);
    }

    public int getxOffset() {return xOffset;}
    public int getyOffset() {return yOffset;}
}
