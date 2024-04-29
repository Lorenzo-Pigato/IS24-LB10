package it.polimi.ingsw.lb10.network.requests.match;

import it.polimi.ingsw.lb10.server.visitors.requestDispatch.MatchRequestVisitor;

public class DrawGoldenFromTableRequest extends MatchRequest {
    private static final long serialVersionUID = 19L;
    private int index;

    public DrawGoldenFromTableRequest(int matchId, int index){
        super(matchId);
        this.index = index;}

    @Override
    public void accept(MatchRequestVisitor visitor) {
        visitor.visit(this);
    }

    public int getIndex(){return index;}
}
