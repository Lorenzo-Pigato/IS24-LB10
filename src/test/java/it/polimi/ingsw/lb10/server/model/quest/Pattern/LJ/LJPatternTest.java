package it.polimi.ingsw.lb10.server.model.quest.Pattern.LJ;

import it.polimi.ingsw.lb10.server.model.Matrix;
import it.polimi.ingsw.lb10.server.model.cards.Color;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;
import it.polimi.ingsw.lb10.server.model.cards.decks.ResourceDeck;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.Diagonal.TypeDiagonal;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LJPatternTest {
    Matrix matrix=new Matrix();
    private LJPattern questCard= new BottomLeft(220,2, Color.BLUE,Color.RED);
    private static ResourceDeck resourceDeck=new ResourceDeck();

    @BeforeAll
    static void setUp(){
        resourceDeck.fillDeck();
    }
    @Test
    void checkCorner() {
        matrix.setCard(resourceDeck.getCards().get(31));
        matrix.setCard(resourceDeck.getCards().get(32),41,43);
        matrix.setCard(resourceDeck.getCards().get(1),40,44);

        assertTrue(questCard.checkCorner(matrix,41,41, Position.TOPLEFT,true));
        assertTrue(questCard.checkCorner(matrix,41,43, Position.TOPLEFT,true));
        assertTrue(questCard.checkCorner(matrix,41,44, Position.TOPRIGHT,false));
    }

    @Test
    public void checkColor(){
        assertTrue(questCard.checkColor(resourceDeck.getCards().get(31).getCorners().getFirst(),true));
        assertTrue(questCard.checkColor(resourceDeck.getCards().get(1).getCorners().getFirst(),false));
    }
}