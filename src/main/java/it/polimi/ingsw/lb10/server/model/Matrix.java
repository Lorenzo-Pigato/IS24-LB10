package it.polimi.ingsw.lb10.server.model;

import it.polimi.ingsw.lb10.server.model.cards.PlaceableCard;
import it.polimi.ingsw.lb10.server.model.cards.StartingCard;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Matrix implements Serializable {
    @Serial
    private static final long serialVersionUID = 35L;

    private final ArrayList<ArrayList<Node>> matrix = new ArrayList<>();


    public Matrix() {
        startingMatrix();
    }

    /**
     * Initialization of the matrix
     * we work with the worst case, 83x83
     */
    public void startingMatrix() {
        for (int row = 0; row < 83; row++) {
            matrix.add(new ArrayList<>());
            for (int column = 0; column < 83; column++) {
                Node node = new Node();
                matrix.get(row).add(node);
            }
        }
    }

    /**
     * @return it's useful to switch between the different corners in algorithms
     */
    public Map<Position, int[]> parsingPositionCorners() {
        Map<Position, int[]> setIncrement = new HashMap<>();
        setIncrement.put(Position.TOPLEFT, new int[]{0, 0});
        setIncrement.put(Position.TOPRIGHT, new int[]{0, 1});
        setIncrement.put(Position.BOTTOMRIGHT, new int[]{1, 1});
        setIncrement.put(Position.BOTTOMLEFT, new int[]{1, 0});
        return setIncrement;
    }


    /**
     * @param card is the starting card to insert at the start of the game by each player
     */
    public void setCard(StartingCard card) {

        Map<Position, int[]> setIncrement = parsingPositionCorners();
        for (Corner corner : card.getStateCardCorners()) {
            int[] delta = setIncrement.get(corner.getPosition());
            getNode(41 + delta[0], 41 + delta[1]).addCorner(corner);
        }
    }

    /**
     * This method is used on the test for testing stuff, in the std game is useless and unused.
     */
    public void setCard(PlaceableCard card) {
        setCard(card, 41, 41);
    }

    /**
     * @param card to set inside the matrix, it's a Playable Card (Resource and Golden),
     *             i and j are the top-left corner of the card inside the matrix.
     */
    public void setCard(PlaceableCard card, int row, int column) {
        Map<Position, int[]> setIncrement = parsingPositionCorners();
        for (Corner corner : card.getStateCardCorners()) {
            int[] delta = setIncrement.get(corner.getPosition());
            getNode(row + delta[0], column + delta[1]).addCorner(corner);
        }
    }

    /**
     * @param row    row
     * @param column column
     *               if the card it's not validated, it's removed
     */
    public void deleteCard(int row, int column) {
        getNode(row, column).deleteLastCorner();
        getNode(row + 1, column).deleteLastCorner();
        getNode(row, column + 1).deleteLastCorner();
        getNode(row + 1, column + 1).deleteLastCorner();
    }


    /**
     * @param row and the column must be the top left corner of the card!
     *            this method sets the Corners of the cards to make them all usedForQuest,
     *            not to be reused in other patterns
     */
    public void setUsedForQuest(int row, int column) {
        Map<Position, int[]> setIncrement = parsingPositionCorners();

        for (Position position : getPossiblePosition()) {
            int[] delta = setIncrement.get(position);
            for (Corner corner : getNode(row + delta[0], column + delta[1]).getCorners())
                if (corner.getPosition().equals(position))
                    corner.setUsedForQuest(true);
        }
    }

    public Node getNode(int row, int column) {
        return matrix.get(row).get(column);
    }


    public ArrayList<ArrayList<Node>> getMatrix() {
        return matrix;
    }

    public Integer getCardRow(int cardId) {
        return matrix.stream().filter(row -> row.stream()
                        .filter(node -> !node.getCorners().isEmpty())
                        .anyMatch(node -> node.getCorners().stream().anyMatch(corner -> corner.getId() == cardId && corner.getPosition().equals(Position.TOPLEFT))))
                .map(matrix::indexOf).findFirst().orElse(null);
    }

    public Integer getCardColumn(int cardId) {

        return matrix.stream()
                .filter(row -> row.stream()
                        .filter(node -> !node.getCorners().isEmpty()) //filters empty nodes inside every row
                        .anyMatch(node -> node.getCorners().stream()
                                .anyMatch(corner -> corner.getId() == cardId && corner.getPosition().equals(Position.TOPLEFT)))) //filters nodes which do not contain corners of the given card
                .findFirst() //takes the one and only row which contains a node which contains TOPLEFT corner of the given card
                .map(row -> row.indexOf(row.stream()
                        .filter(node -> !node.getCorners().isEmpty() && node.getCorners().stream().anyMatch(corner -> corner.getId() == cardId && corner.getPosition().equals(Position.TOPLEFT))).findFirst().orElse(null))
                ).orElse(null);
    }

    private Position[] getPossiblePosition() {
        return new Position[]{Position.TOPLEFT, Position.TOPRIGHT, Position.BOTTOMRIGHT, Position.BOTTOMLEFT};
    }
}
