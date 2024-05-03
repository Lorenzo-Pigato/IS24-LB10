package it.polimi.ingsw.lb10.network.response.match;

import it.polimi.ingsw.lb10.network.response.Response;
import it.polimi.ingsw.lb10.server.model.Matrix;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.CLIResponseHandler;
import it.polimi.ingsw.lb10.server.visitors.responseDespatch.ResponseVisitor;

public class MoveBoardResponse extends Response {

    private Matrix board;

    public MoveBoardResponse(Matrix board) {
        this.board = board;
    }

    @Override
    public void accept(ResponseVisitor handler) {
        handler.visit(this);
    }

    public Matrix getBoard() {return board;}
}
