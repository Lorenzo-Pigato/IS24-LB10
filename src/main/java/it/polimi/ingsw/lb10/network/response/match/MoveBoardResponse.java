package it.polimi.ingsw.lb10.network.response.match;

import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.model.Matrix;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.ResponseVisitor;

public class MoveBoardResponse extends Response {

    private final Matrix board;
    private final int xOffset;
    private final int yOffset;

    public MoveBoardResponse(Matrix board, int xOffset, int yOffset) {
        this.board = board;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    @Override
    public void accept(ResponseVisitor handler) {
        handler.visit(this);
    }

    public Matrix getBoard() {
        return board;
    }

    public int getxOffset() {
        return xOffset;
    }

    public int getyOffset() {
        return yOffset;
    }
}
